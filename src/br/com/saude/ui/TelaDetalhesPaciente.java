package br.com.saude.ui;

import br.com.saude.dao.PressaoDAO;
import br.com.saude.dao.PressaoDAO.Registro;
import br.com.saude.model.Paciente;
import br.com.saude.util.ExportacaoExcelUtil;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

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

        // Painel do gráfico
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Registro> registros = new PressaoDAO().listarRegistros(paciente.getId());
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/yyyy");

        for (Registro r : registros) {
            String mes = r.getData().format(fmt);
            dataset.addValue(r.getSistolica(), "Sistólica", mes);
            dataset.addValue(r.getDiastolica(), "Diastólica", mes);
        }

        JFreeChart chart = ChartFactory.createLineChart(
            "Evolução da Pressão (mês a mês)",
            "Mês/Ano",
            "Pressão (mmHg)",
            dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);

        // Botão exportar
        JButton btnExportar = new JButton("Exportar para Excel");
        btnExportar.addActionListener(e -> {
            new ExportacaoExcelUtil().exportarRegistros(paciente.getNome(), registros);
            JOptionPane.showMessageDialog(this, "Exportação concluída!");
        });

        JPanel sul = new JPanel();
        sul.add(btnExportar);
        add(sul, BorderLayout.SOUTH);

        setVisible(true);
    }
}
