package br.com.saude.ui;

import br.com.saude.model.Paciente;
import br.com.saude.dao.PressaoDAO;
import br.com.saude.dao.PressaoDAO.Registro;
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

        JLabel titulo = new JLabel("Pressão Arterial (Diário)", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // montar dataset diário
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Registro> registros = new PressaoDAO().listarRegistros(paciente.getId());
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Registro r : registros) {
            String dia = r.getData().format(fmt);
            dataset.addValue(r.getSistolica(), "Sistólica", dia);
            dataset.addValue(r.getDiastolica(), "Diastólica", dia);
        }

        JFreeChart chart = ChartFactory.createLineChart(
            "Evolução da Pressão (dia a dia)",
            "Dia",
            "Pressão (mmHg)",
            dataset
        );
        add(new ChartPanel(chart), BorderLayout.CENTER);

        // painel de botões
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVoltar = new JButton("← Selecionar Tipo");
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaSelecionaTipoGrafico(paciente);
        });
        botoes.add(btnVoltar);
        add(botoes, BorderLayout.SOUTH);

        setVisible(true);
    }
}
