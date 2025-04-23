package br.com.saude.ui;

import br.com.saude.dao.PressaoDAO;
import br.com.saude.dao.PressaoDAO.Registro;
import br.com.saude.model.Usuario;
import br.com.saude.util.Conexao;
import br.com.saude.util.ExportacaoExcelUtil;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public class TelaInicialPaciente extends JFrame {
    private Usuario paciente;
    private JPanel painelInfo, painelPressao;
    private JTextField tfNome, tfEndereco, tfSistolica, tfDiastolica;
    private JFormattedTextField tfTelefone;
    private JDateChooser campoData;

    public TelaInicialPaciente(Usuario paciente) {
        this.paciente = paciente;
        setTitle("Tela Inicial - Bem-vindo");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        carregarDadosPaciente();

        JLabel titulo = new JLabel("Olá, " + paciente.getNome() + " (paciente)", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        add(centro, BorderLayout.CENTER);

        JButton btnToggleInfo = new JButton("Minhas Informações");
        JButton btnTogglePressao = new JButton("Registrar Pressão");

        painelInfo = criarPainelDados();
        painelInfo.setVisible(false);

        painelPressao = criarPainelRegistroPressao();
        painelPressao.setVisible(false);

        btnToggleInfo.addActionListener(e -> painelInfo.setVisible(!painelInfo.isVisible()));
        btnTogglePressao.addActionListener(e -> painelPressao.setVisible(!painelPressao.isVisible()));

        centro.add(btnToggleInfo);
        centro.add(painelInfo);
        centro.add(Box.createVerticalStrut(10));
        centro.add(btnTogglePressao);
        centro.add(painelPressao);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnGrafico = new JButton("Gerar Gráfico");
        JButton btnExportar = new JButton("Exportar para Excel");
        JButton btnAlterarSenha = new JButton("Alterar Senha");
        JButton btnDeslogar = new JButton("Deslogar");

        btnGrafico.addActionListener(e -> new TelaSelecionaTipoGrafico(paciente));
        btnExportar.addActionListener(e -> {
            List<Registro> registros = new PressaoDAO().listarRegistros(paciente.getId());
            new ExportacaoExcelUtil().exportarRegistros(paciente.getNome(), registros);
            JOptionPane.showMessageDialog(this, "Exportação concluída!");
        });
        btnAlterarSenha.addActionListener(e -> new TelaAlterarSenha(paciente));
        btnDeslogar.addActionListener(e -> {
            dispose();
            new TelaEscolhaLogin();
        });

        rodape.add(btnGrafico);
        rodape.add(btnExportar);
        rodape.add(btnAlterarSenha);
        rodape.add(btnDeslogar);
        add(rodape, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void carregarDadosPaciente() {
        String sql = "SELECT endereco, telefone FROM usuarios WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, paciente.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                paciente.setEndereco(rs.getString("endereco"));
                paciente.setTelefone(rs.getString("telefone"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JPanel criarPainelDados() {
        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));
        painel.setBorder(BorderFactory.createTitledBorder("Informações Pessoais"));

        tfNome = new JTextField(paciente.getNome());
        tfEndereco = new JTextField(paciente.getEndereco() != null ? paciente.getEndereco() : "");

        try {
            MaskFormatter formatter = new MaskFormatter("(##)#####-####");
            formatter.setPlaceholderCharacter('_');
            tfTelefone = new JFormattedTextField(formatter);
            tfTelefone.setText(paciente.getTelefone() != null ? paciente.getTelefone() : "");
        } catch (Exception e) {
            tfTelefone = new JFormattedTextField();
        }

        JButton btnSalvarInfo = new JButton("Salvar Informações");
        btnSalvarInfo.addActionListener(this::salvarInformacoes);

        painel.add(new JLabel("Nome:")); painel.add(tfNome);
        painel.add(new JLabel("Endereço:")); painel.add(tfEndereco);
        painel.add(new JLabel("Telefone:")); painel.add(tfTelefone);
        painel.add(new JLabel("")); painel.add(btnSalvarInfo);

        return painel;
    }

    private JPanel criarPainelRegistroPressao() {
        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));
        painel.setBorder(BorderFactory.createTitledBorder("Registrar Pressão"));

        tfSistolica = new JTextField();
        tfDiastolica = new JTextField();
        campoData = new JDateChooser();

        tfSistolica.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
                if (str != null && str.matches("\\d+") && (getLength() + str.length() <= 3)) {
                    super.insertString(offset, str, attr);
                }
            }
        });

        tfDiastolica.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
                if (str != null && str.matches("\\d+") && (getLength() + str.length() <= 3)) {
                    super.insertString(offset, str, attr);
                }
            }
        });

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(this::registrarPressao);

        painel.add(new JLabel("Data:")); painel.add(campoData);
        painel.add(new JLabel("Sistólica:")); painel.add(tfSistolica);
        painel.add(new JLabel("Diastólica:")); painel.add(tfDiastolica);
        painel.add(new JLabel("")); painel.add(btnSalvar);

        return painel;
    }

    private void registrarPressao(ActionEvent e) {
        Date data = campoData.getDate();
        String sistolica = tfSistolica.getText().trim();
        String diastolica = tfDiastolica.getText().trim();

        if (data == null || sistolica.isEmpty() || diastolica.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "INSERT INTO pressao (id_paciente, sistolica, diastolica, data) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, paciente.getId());
            stmt.setInt(2, Integer.parseInt(sistolica));
            stmt.setInt(3, Integer.parseInt(diastolica));
            stmt.setDate(4, new java.sql.Date(data.getTime()));
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registro de pressão salvo!");
            tfSistolica.setText("");
            tfDiastolica.setText("");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao registrar pressão: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarInformacoes(ActionEvent e) {
        String novoNome = tfNome.getText().trim();
        String novoEndereco = tfEndereco.getText().trim();
        String novoTelefone = tfTelefone.getText().trim();

        if (novoNome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome é obrigatório.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "UPDATE usuarios SET nome = ?, endereco = ?, telefone = ? WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setString(2, novoEndereco);
            stmt.setString(3, novoTelefone);
            stmt.setInt(4, paciente.getId());

            stmt.executeUpdate();

            paciente.setNome(novoNome);
            paciente.setEndereco(novoEndereco);
            paciente.setTelefone(novoTelefone);
            JOptionPane.showMessageDialog(this, "Informações atualizadas com sucesso!");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Usuario u = new Usuario(2, "Paciente Teste", "email@teste.com", "123", "paciente");
        SwingUtilities.invokeLater(() -> new TelaInicialPaciente(u));
    }
}
