package br.com.saude.ui;

import br.com.saude.dao.PacienteDAO;
import br.com.saude.dao.PressaoDAO;
import br.com.saude.dao.PressaoDAO.Registro;
import br.com.saude.model.Paciente;
import br.com.saude.util.ExportacaoExcelUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListaPacientes extends JFrame {
    private JTable tabela;
    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private final PressaoDAO pressaoDAO = new PressaoDAO();

    public TelaListaPacientes(JFrame telaAnterior) {
        setTitle("Lista de Pacientes");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Lista de Pacientes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Tabela de pacientes
        tabela = new JTable();
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);
        preencherTabela();

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());

        JButton btnExportar = new JButton("Exportar Selecionado");
        JButton btnVoltar = new JButton("Voltar");

        btnExportar.addActionListener(e -> exportarSelecionado());
        btnVoltar.addActionListener(e -> {
            dispose();
            telaAnterior.setVisible(true);
        });

        painelBotoes.add(btnExportar);
        painelBotoes.add(btnVoltar);
        add(painelBotoes, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void preencherTabela() {
        String[] colunas = {"ID", "Nome", "Endereço", "Telefone"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        List<Paciente> pacientes = pacienteDAO.listarTodos();
        for (Paciente p : pacientes) {
            model.addRow(new Object[]{
                p.getId(),
                p.getNome(),
                p.getEndereco(),
                p.getTelefone()
            });
        }
        tabela.setModel(model);
    }

    private void exportarSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tabela.getValueAt(linha, 0);
        String nome = (String) tabela.getValueAt(linha, 1);
        List<Registro> registros = pressaoDAO.listarRegistros(id);

        if (registros.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Este paciente não tem registros de pressão!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        new ExportacaoExcelUtil().exportarRegistros(nome, registros);
        JOptionPane.showMessageDialog(this, "Exportado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}
