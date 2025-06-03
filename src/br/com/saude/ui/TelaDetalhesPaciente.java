package br.com.saude.ui;

import br.com.saude.dao.PressaoDAO;
import br.com.saude.dao.PressaoDAO.Registro;
import br.com.saude.model.Paciente;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaDetalhesPaciente extends JFrame {
    private Paciente paciente;

    public TelaDetalhesPaciente(Paciente paciente) {
        this.paciente = paciente;

        setTitle("Detalhes de " + paciente.getNome());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Paciente: " + paciente.getNome(), SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Gráfico
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Registro> registros = new PressaoDAO().listarRegistros(paciente.getId());
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Registro r : registros) {
            String dataFormatada = r.getData().format(fmt);
            dataset.addValue(r.getSistolica(), "Sistólica", dataFormatada);
            dataset.addValue(r.getDiastolica(), "Diastólica", dataFormatada);
        }

        JFreeChart chart = ChartFactory.createLineChart(
            "Histórico de Pressão Arterial",
            "Data",
            "Pressão (mmHg)",
            dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);

        // Botões
        JButton btnVoltar = new JButton("← Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaInicialMedico(new br.com.saude.model.Usuario()); // ou redirecionar para a tela anterior do login
        });

        JButton btnExportar = new JButton("Exportar para Excel");
        btnExportar.addActionListener(e -> {
            new br.com.saude.util.ExportacaoExcelUtil().exportarRegistros(paciente.getNome(), registros);
            JOptionPane.showMessageDialog(this, "Exportado com sucesso!");
        });

        JPanel painelSul = new JPanel();
        painelSul.add(btnExportar);
        painelSul.add(btnVoltar);
        add(painelSul, BorderLayout.SOUTH);

        setVisible(true);
    }
}
