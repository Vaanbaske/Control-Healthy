package br.com.saude.ui;

import br.com.saude.model.Paciente;
import br.com.saude.model.Usuario;
import br.com.saude.dao.PressaoDAO;
import br.com.saude.util.ExportacaoExcelUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaInicialPaciente extends JFrame {
    private Paciente paciente;

    public TelaInicialPaciente(Paciente paciente) {
        this.paciente = paciente;

        setTitle("Painel do Paciente: " + paciente.getNome());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Bem-vindo, " + paciente.getNome(), SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        // Painel com dados do paciente
        JPanel painelDados = new JPanel(new GridLayout(5, 1));
        painelDados.setBorder(BorderFactory.createTitledBorder("Seus Dados"));

        painelDados.add(new JLabel("Nome: " + paciente.getNome()));
        painelDados.add(new JLabel("Idade: " + paciente.getIdade()));
        painelDados.add(new JLabel("Endereço: " + paciente.getEndereco()));
        painelDados.add(new JLabel("Telefone: " + paciente.getTelefone()));

        Usuario medico = paciente.getMedico();
        String nomeMedico = (medico != null) ? medico.getNome() : "Não atribuído";
        painelDados.add(new JLabel("Médico Responsável: " + nomeMedico));

        add(painelDados, BorderLayout.CENTER);

        // Painel com botões
        JPanel painelBotoes = new JPanel();

        JButton btnGrafico = new JButton("Gerar Gráfico");
        btnGrafico.addActionListener(e -> {
            dispose();
            new TelaSelecionaTipoGrafico(paciente);
        });

        JButton btnExportar = new JButton("Exportar para Excel");
        btnExportar.addActionListener(e -> {
            List<PressaoDAO.Registro> registros = new PressaoDAO().listarRegistros(paciente.getId());
            new ExportacaoExcelUtil().exportarRegistros(paciente.getNome(), registros);
            JOptionPane.showMessageDialog(this, "Exportação concluída!");
        });

        JButton btnDeslogar = new JButton("← Deslogar");
        btnDeslogar.addActionListener(e -> {
            dispose();
            new TelaEscolhaLogin();
        });

        painelBotoes.add(btnGrafico);
        painelBotoes.add(btnExportar);
        painelBotoes.add(btnDeslogar);

        add(painelBotoes, BorderLayout.SOUTH);

        setVisible(true);
    }
}
