package br.com.saude.ui;

import javax.swing.*;
import java.awt.*;
import br.com.saude.dao.UsuarioDAO;
import br.com.saude.model.Usuario;

public class TelaLogin extends JFrame {
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;
    private JButton botaoVoltar;

    public TelaLogin() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centralizar na tela
        setLayout(new GridLayout(4, 2, 10, 10)); 
        campoEmail = new JTextField();
        campoSenha = new JPasswordField();
        botaoEntrar = new JButton("Entrar");
        botaoVoltar = new JButton("← Voltar");

        add(new JLabel("Email:"));
        add(campoEmail);
        add(new JLabel("Senha:"));
        add(campoSenha);
        add(botaoEntrar);
        add(botaoVoltar);

        botaoEntrar.addActionListener(e -> autenticarUsuario());
        botaoVoltar.addActionListener(e -> {
            dispose();
            new TelaEscolhaLogin();
        });

        setVisible(true);
    }

    private void autenticarUsuario() {
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword()).trim();

        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha email e senha!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.buscarPorEmail(email);

        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "E-mail não cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!usuario.getSenha().equals(senha)) {
            JOptionPane.showMessageDialog(this, "Senha incorreta.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Se chegou aqui, login foi validado
        JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");

        dispose(); // Fecha a tela de login

        switch (usuario.getTipo()) {
            case "admin":
                new TelaInicialAdmin(usuario);
                break;
            case "medico":
                new TelaInicialMedico(usuario);
                break;
            case "paciente":
                new TelaInicialPaciente(usuario);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Tipo de usuário desconhecido!", "Erro", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    public static void main(String[] args) {
        new TelaLogin();
    }
}
