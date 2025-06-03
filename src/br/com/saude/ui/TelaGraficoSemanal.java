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
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.List;

public class TelaGraficoSemanal extends JFrame {

    private Paciente paciente;

    public TelaGraficoSemanal(Paciente paciente) {
        this.paciente = paciente;

        setTitle("Gráfico Semanal de Pressão - " + paciente.getNome());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Pressão Arterial (Semanal)", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Dados e agrupamento por semana
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Registro> registros = new PressaoDAO().listarRegistros(paciente.getId());
        Map<String, List<Registro>> agrupado = new TreeMap<>();
        WeekFields padrao = WeekFields.of(Locale.getDefault());

        for (Registro r : registros) {
            int semana = r.getData().get(padrao.weekOfWeekBasedYear());
            int ano = r.getData().getYear();
            String chave = "Semana " + semana + "/" + ano;
            agrupado.computeIfAbsent(chave, k -> new ArrayList<>()).add(r);
        }

        for (String semana : agrupado.keySet()) {
            List<Registro> lista = agrupado.get(semana);
            double sist = lista.stream().mapToInt(Registro::getSistolica).average().orElse(0);
            double dias = lista.stream().mapToInt(Registro::getDiastolica).average().orElse(0);
            dataset.addValue(sist, "Sistólica", semana);
            dataset.addValue(dias, "Diastólica", semana);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Evolução da Pressão Semanal",
                "Semana",
                "Pressão (mmHg)",
                dataset
        );

        add(new ChartPanel(chart), BorderLayout.CENTER);

        JButton btnVoltar = new JButton("← Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaSelecionaTipoGrafico(paciente);
        });

        JPanel painel = new JPanel();
        painel.add(btnVoltar);
        add(painel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
