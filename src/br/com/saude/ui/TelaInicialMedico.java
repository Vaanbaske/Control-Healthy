package br.com.saude.ui;

import br.com.saude.dao.PacienteDAO;
import br.com.saude.dao.PressaoDAO;
import br.com.saude.dao.PressaoDAO.Registro;
import br.com.saude.model.Paciente;
import br.com.saude.model.Usuario;
import br.com.saude.util.ExportacaoExcelUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TelaInicialMedico extends JFrame {
    private final Usuario medicoLogado;
    private final JTable tabelaTodos;
    private final JTable tabelaMeusPacientes;
    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private final PressaoDAO pressaoDAO = new PressaoDAO();

    public TelaInicialMedico(Usuario medicoLogado) {
        this.medicoLogado = medicoLogado;

        // ── Configuração da janela ───────────────────────────────────────────
        setTitle("Painel do Médico: " + medicoLogado.getNome());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ── Título ───────────────────────────────────────────────────────────
        JLabel titulo = new JLabel("Painel do Médico: " + medicoLogado.getNome(), SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        // ── Painel central com 2 tabelas ──────────────────────────────────────
        JPanel painelCentral = new JPanel(new GridLayout(2, 1, 0, 10));

        // 1) "Todos os Pacientes"
        tabelaTodos = new JTable();
        JScrollPane scrollTodos = new JScrollPane(tabelaTodos);
        scrollTodos.setBorder(BorderFactory.createTitledBorder("Todos os Pacientes (com médico atribuído)"));
        preencherTabelaTodos();

        // 2) "Seus Pacientes"
        tabelaMeusPacientes = new JTable();
        JScrollPane scrollMeus = new JScrollPane(tabelaMeusPacientes);
        scrollMeus.setBorder(BorderFactory.createTitledBorder("Seus Pacientes (clique duplo para visualizar)"));
        preencherTabelaMeus();

        // evento de duplo‑clique já existente
        tabelaMeusPacientes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tabelaMeusPacientes.getSelectedRow() != -1) {
                    gerarGraficoParaPacienteSelecionado();
                }
            }
        });

        painelCentral.add(scrollTodos);
        painelCentral.add(scrollMeus);
        add(painelCentral, BorderLayout.CENTER);

        // ── Painel de botões na parte inferior ────────────────────────────────
        JPanel painelSul = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Botão "Gerar Gráfico"
        JButton btnGerarGrafico = new JButton("Gerar Gráfico");
        btnGerarGrafico.addActionListener(e -> gerarGraficoParaPacienteSelecionado());

        // Botão "Exportar para Excel"
        JButton btnExportar = new JButton("Exportar para Excel");
        btnExportar.addActionListener(e -> exportarDadosSelecionado());

        // Botão "Deslogar"
        JButton btnDeslogar = new JButton("Deslogar");
        btnDeslogar.addActionListener(e -> {
            dispose();                        // fecha esta janela
            new TelaEscolhaLogin();          // volta para a tela inicial de login
        });

        // adiciona ao painel
        painelSul.add(btnGerarGrafico);
        painelSul.add(btnExportar);
        painelSul.add(btnDeslogar);

        add(painelSul, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ── Preenche a tabela "Todos os Pacientes" ─────────────────────────────
    private void preencherTabelaTodos() {
        String[] colunas = {"Nome do Paciente", "Médico Responsável"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        List<Paciente> lista = pacienteDAO.listarTodos();
        for (Paciente p : lista) {
            String nomeMedico = (p.getMedico() != null) ? p.getMedico().getNome() : "Não atribuído";
            model.addRow(new Object[]{p.getNome(), nomeMedico});
        }
        tabelaTodos.setModel(model);
    }

    // ── Preenche a tabela "Seus Pacientes" ────────────────────────────────
    private void preencherTabelaMeus() {
        String[] colunas = {"ID", "Nome", "Endereço", "Média Sistólica", "Média Diastólica"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        List<Paciente> lista = pacienteDAO.listarPorMedico(medicoLogado.getId());
        for (Paciente p : lista) {
            double mediaSis = pressaoDAO.calcularMediaSistolica(p.getId());
            double mediaDia = pressaoDAO.calcularMediaDiastolica(p.getId());
            model.addRow(new Object[]{
                p.getId(),
                p.getNome(),
                p.getEndereco(),
                String.format("%.1f", mediaSis),
                String.format("%.1f", mediaDia)
            });
        }
        tabelaMeusPacientes.setModel(model);
    }

    // ── Abre a tela de seleção de tipo de gráfico para o paciente selecionado ──
    private void gerarGraficoParaPacienteSelecionado() {
        int linha = tabelaMeusPacientes.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tabelaMeusPacientes.getValueAt(linha, 0);
        String nome = (String) tabelaMeusPacientes.getValueAt(linha, 1);
        Paciente p = new Paciente();
        p.setId(id);
        p.setNome(nome);
        new TelaSelecionaTipoGrafico(p);
    }

    // ── Exporta para Excel os registros do paciente selecionado ─────────────
    private void exportarDadosSelecionado() {
        int linha = tabelaMeusPacientes.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tabelaMeusPacientes.getValueAt(linha, 0);
        String nome = (String) tabelaMeusPacientes.getValueAt(linha, 1);
        List<Registro> registros = pressaoDAO.listarRegistros(id);
        new ExportacaoExcelUtil().exportarRegistros(nome, registros);
        JOptionPane.showMessageDialog(this, "Dados exportados para Excel com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}
