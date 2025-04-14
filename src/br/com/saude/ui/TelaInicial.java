package br.com.saude.ui;

import br.com.saude.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaInicial extends JFrame {

    private static final long serialVersionUID = 1L;

    public TelaInicial(Usuario usuario) {
        setTitle("Tela Inicial - Bem-vindo");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel boasVindas = new JLabel("Olá, " + usuario.getNome() + " (" + usuario.getTipo() + ")");
        boasVindas.setHorizontalAlignment(SwingConstants.CENTER);
        boasVindas.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(0, 1, 10, 10));

        if (usuario.getTipo().equalsIgnoreCase("medico")) {
            JButton btnPacientes = new JButton("Ver pacientes");
            JButton btnGraficos = new JButton("Gráficos por paciente");
            JButton btnExportar = new JButton("Exportar dados");

            btnPacientes.addActionListener(e -> JOptionPane.showMessageDialog(this, "Abrindo tela de pacientes..."));
            btnGraficos.addActionListener(e -> JOptionPane.showMessageDialog(this, "Abrindo gráficos dos pacientes..."));
            btnExportar.addActionListener(e -> JOptionPane.showMessageDialog(this, "Exportando dados para Excel..."));

            painelBotoes.add(btnPacientes);
            painelBotoes.add(btnGraficos);
            painelBotoes.add(btnExportar);

        } else if (usuario.getTipo().equalsIgnoreCase("paciente")) {
            JButton btnInfo = new JButton("Minhas informações");
            JButton btnGrafico = new JButton("Gráfico da pressão");

            btnInfo.addActionListener(e -> JOptionPane.showMessageDialog(this, "Exibindo suas informações..."));
            btnGrafico.addActionListener(e -> JOptionPane.showMessageDialog(this, "Exibindo seu gráfico de pressão..."));

            painelBotoes.add(btnInfo);
            painelBotoes.add(btnGrafico);
        }

        add(boasVindas, BorderLayout.NORTH);
        add(painelBotoes, BorderLayout.CENTER);

        setVisible(true);
    }
}
