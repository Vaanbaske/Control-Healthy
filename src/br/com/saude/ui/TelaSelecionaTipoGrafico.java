package br.com.saude.ui;

import br.com.saude.model.Paciente;
import br.com.saude.old.TelaGraficoMenu;

import javax.swing.*;
import java.awt.*;

public class TelaSelecionaTipoGrafico extends JFrame {
    private Paciente paciente;

    public TelaSelecionaTipoGrafico(Paciente paciente) {
        this.paciente = paciente;

        setTitle("Escolher Tipo de Gráfico");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        JLabel titulo = new JLabel("Escolha o Tipo de Gráfico", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo);

        JButton btnDiario = new JButton("Gráfico Diário");
        JButton btnSemanal = new JButton("Gráfico Semanal");
        JButton btnMensal = new JButton("Gráfico Mensal");
        JButton btnVoltar = new JButton("← Voltar");

        btnDiario.addActionListener(e -> {
            dispose();
            new TelaGraficoDiario(paciente); //  ainda vamos criar - normal dar erro
        });

        btnSemanal.addActionListener(e -> {
            dispose();
            new TelaGraficoSemanal(paciente); // ainda vamos criar - normal dar erro
        });

        btnMensal.addActionListener(e -> {
            dispose();
            new TelaGraficoMensal(paciente); // ainda vamos criar - normal dar erro
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaGraficoMenu(paciente);
        });

        add(btnDiario);
        add(btnSemanal);
        add(btnMensal);
        add(btnVoltar);

        setVisible(true);
    }
}
