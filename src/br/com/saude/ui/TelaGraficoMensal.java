package br.com.saude.ui;

import br.com.saude.dao.PressaoDAO;
import br.com.saude.dao.PressaoDAO.Registro;
import br.com.saude.model.Paciente;
import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class TelaGraficoMensal extends JFrame {
    private Paciente paciente;

    public TelaGraficoMensal(Paciente paciente) {
        this.paciente = paciente;

        setTitle("Gráfico Mensal - " + paciente.getNome());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Pressão Arterial (Média Mensal)", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Registro> registros = new PressaoDAO().listarRegistros(paciente.getId());

        Map<String, List<Registro>> agrupadoPorMes = new TreeMap<>();
        DateTimeFormatter mesAnoFormat = DateTimeFormatter.ofPattern("MM/yyyy");

        for (Registro r : registros) {
            String mesAno = r.getData().format(mesAnoFormat);
            agrupadoPorMes.computeIfAbsent(mesAno, k -> new ArrayList<>()).add(r);
        }

        for (String mes : agrupadoPorMes.keySet()) {
            List<Registro> lista = agrupadoPorMes.get(mes);
            double sist = lista.stream().mapToInt(Registro::getSistolica).average().orElse(0);
            double dias = lista.stream().mapToInt(Registro::getDiastolica).average().orElse(0);
            dataset.addValue(sist, "Sistólica", mes);
            dataset.addValue(dias, "Diastólica", mes);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Evolução da Pressão - Mensal",
                "Mês",
                "Pressão (mmHg)",
                dataset
        );

        add(new ChartPanel(chart), BorderLayout.CENTER);

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
