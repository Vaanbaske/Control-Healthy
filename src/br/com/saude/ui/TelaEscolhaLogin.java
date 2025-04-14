package br.com.saude.ui;

import javax.swing.*;
import java.awt.*;

public class TelaEscolhaLogin extends JFrame {

    public TelaEscolhaLogin() {
        setTitle("Sistema de Saúde - Bem-vindo");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Sistema de Acompanhamento da Pressão ...", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 10, 10)); // Agora tem 3 linhas!
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton botaoMedico = new JButton("Login Médico");
        botaoMedico.setBackground(Color.decode("#ADD8E6")); // Azul claro
        botaoMedico.addActionListener(e -> new TelaLogin());

        JButton botaoPaciente = new JButton("Login Paciente");
        botaoPaciente.setBackground(Color.decode("#FFCCCC")); // Rosa claro
        botaoPaciente.addActionListener(e -> new TelaLogin());

        JButton botaoCadastro = new JButton("Cadastro");
        botaoCadastro.setBackground(Color.decode("#D3FAD3")); // Verde claro
        botaoCadastro.addActionListener(e -> new TelaCadastroUsuario());

        painelBotoes.add(botaoMedico);
        painelBotoes.add(botaoPaciente);
        painelBotoes.add(botaoCadastro); // 👈 Aqui adiciona o botão de cadastro

        add(painelBotoes, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaEscolhaLogin();
    }
}
