package br.com.saude;
import br.com.saude.ui.TelaSelecionaTipoGrafico;


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

public class TelaGraficoDiario extends JFrame {

    private Paciente paciente;

    public TelaGraficoDiario(Paciente paciente) {
        this.paciente = paciente;

        setTitle("Gráfico Diário de Pressão - " + paciente.getNome());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Pressão Arterial (Diário)", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Dados e gráfico
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Registro> registros = new PressaoDAO().listarRegistros(paciente.getId());
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM");

        for (Registro r : registros) {
            String dataFormatada = r.getData().format(fmt);
            dataset.addValue(r.getSistolica(), "Sistólica", dataFormatada);
            dataset.addValue(r.getDiastolica(), "Diastólica", dataFormatada);
        }

        JFreeChart chart = ChartFactory.createLineChart(
            "Evolução da Pressão Arterial - Diário",
            "Data",
            "Pressão (mmHg)",
            dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);

        // Botão voltar
        JButton btnVoltar = new JButton("← Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaSelecionaTipoGrafico(paciente);
        });

        JPanel painelSul = new JPanel();
        painelSul.add(btnVoltar);
        add(painelSul, BorderLayout.SOUTH);

        setVisible(true);
    }
}