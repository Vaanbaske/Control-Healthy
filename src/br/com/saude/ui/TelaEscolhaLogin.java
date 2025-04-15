package br.com.saude.ui;

import javax.swing.*;
import java.awt.*;

public class TelaEscolhaLogin extends JFrame {

    public TelaEscolhaLogin() {
        setTitle("Bem-vindo");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel(
            "Sistema de Acompanhamento da Pressão Arterial",
            SwingConstants.CENTER
        );
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 10, 10));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton botaoMedico = new JButton("Login Médico");
        botaoMedico.setBackground(Color.decode("#ADD8E6"));
        botaoMedico.addActionListener(e -> {
            setVisible(false);
            new TelaLogin(this);
        });

        JButton botaoPaciente = new JButton("Login Paciente");
        botaoPaciente.setBackground(Color.decode("#FFCCCC"));
        botaoPaciente.addActionListener(e -> {
            setVisible(false);
            new TelaLogin(this);
        });

        JButton botaoCadastro = new JButton("Cadastro");
        botaoCadastro.setBackground(Color.decode("#D3FAD3"));
        botaoCadastro.addActionListener(e -> {
            setVisible(false);
            new TelaCadastroUsuario(this);
        });

        painelBotoes.add(botaoMedico);
        painelBotoes.add(botaoPaciente);
        painelBotoes.add(botaoCadastro);

        add(painelBotoes, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaEscolhaLogin();
    }
}
