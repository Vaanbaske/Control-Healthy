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

public class TelaGraficoDiario extends JFrame {
    private Paciente paciente;

    public TelaGraficoDiario(Paciente paciente) {
        this.paciente = paciente;

        setTitle("Gráfico Diário de Pressão - " + paciente.getNome());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Gráfico Diário de " + paciente.getNome(), SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Carregar registros do banco
        List<Registro> registros = new PressaoDAO().listarRegistros(paciente.getId());

        // Preparar dataset para o gráfico
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

        for (Registro r : registros) {
            String data = r.getData().format(formatter);
            dataset.addValue(r.getSistolica(), "Sistólica", data);
            dataset.addValue(r.getDiastolica(), "Diastólica", data);
        }

        // Criar gráfico
        JFreeChart chart = ChartFactory.createLineChart(
            "Pressão Arterial por Dia",
            "Data",
            "Pressão (mmHg)",
            dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);

        // Botão de voltar
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
