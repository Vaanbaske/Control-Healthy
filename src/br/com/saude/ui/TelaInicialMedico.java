package br.com.saude.ui;

import br.com.saude.dao.PacienteDAO;
import br.com.saude.dao.PressaoDAO;
import br.com.saude.model.Paciente;
import br.com.saude.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TelaInicialMedico extends JFrame {
    private Usuario medicoLogado;
    private JTable tabelaTodos, tabelaMeusPacientes;

    public TelaInicialMedico(Usuario medicoLogado) {
        this.medicoLogado = medicoLogado;

        setTitle("Painel do Médico: " + medicoLogado.getNome());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Painel do Médico: " + medicoLogado.getNome(), SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel(new GridLayout(2, 1));

        // Tabela 1: Todos os pacientes + médico responsável
        tabelaTodos = new JTable();
        JScrollPane scrollTodos = new JScrollPane(tabelaTodos);
        scrollTodos.setBorder(BorderFactory.createTitledBorder("Todos os Pacientes (com médico atribuído)"));
        preencherTabelaTodos();

        // Tabela 2: Pacientes do médico + média de pressão
        tabelaMeusPacientes = new JTable();
        JScrollPane scrollMeus = new JScrollPane(tabelaMeusPacientes);
        scrollMeus.setBorder(BorderFactory.createTitledBorder("Seus Pacientes (clique duplo para visualizar)"));
        preencherTabelaMeus();

        // Ao clicar duas vezes, abre nova tela com gráfico e exportação
        tabelaMeusPacientes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tabelaMeusPacientes.getSelectedRow() != -1) {
                    int linha = tabelaMeusPacientes.getSelectedRow();
                    int idPaciente = (int) tabelaMeusPacientes.getValueAt(linha, 0);
                    String nomePaciente = (String) tabelaMeusPacientes.getValueAt(linha, 1);
                    Paciente paciente = new Paciente();
                    paciente.setId(idPaciente);
                    paciente.setNome(nomePaciente);
                    dispose(); // fecha a tela atual
                    new TelaDetalhesPaciente(paciente); // nova tela com gráfico + exportar
                }
            }
        });

        painelCentral.add(scrollTodos);
        painelCentral.add(scrollMeus);

        add(painelCentral, BorderLayout.CENTER);

        setVisible(true);
    }

    private void preencherTabelaTodos() {
        String[] colunas = {"Nome do Paciente", "Médico Responsável"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        List<Paciente> lista = new PacienteDAO().listarTodos();

        for (Paciente p : lista) {
            String nomeMedico = (p.getMedico() != null) ? p.getMedico().getNome() : "Não atribuído";
            model.addRow(new Object[]{p.getNome(), nomeMedico});
        }

        tabelaTodos.setModel(model);
    }

    private void preencherTabelaMeus() {
        String[] colunas = {"ID", "Nome", "Endereço", "Média Sistólica", "Média Diastólica"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        List<Paciente> lista = new PacienteDAO().listarPorMedico(medicoLogado.getId());
        PressaoDAO pressaoDAO = new PressaoDAO();

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
}
