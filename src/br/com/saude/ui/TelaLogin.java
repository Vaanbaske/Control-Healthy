package br.com.saude.ui;

import javax.swing.*;
import java.awt.*;

import br.com.saude.dao.UsuarioDAO;
import br.com.saude.model.Usuario;
import br.com.saude.old.TelaInicial;

public class TelaLogin extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;
    private JButton botaoCadastrar;
    private JButton botaoVoltar;
    private JFrame janelaAnterior;

    public TelaLogin(JFrame anterior) {
        this.janelaAnterior = anterior;

        setTitle("Login");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 5, 5));

        campoEmail     = new JTextField();
        campoSenha     = new JPasswordField();
        botaoEntrar    = new JButton("Entrar");
        botaoCadastrar = new JButton("Cadastrar");
        botaoVoltar    = new JButton("← Voltar");

        add(new JLabel("Email:"));
        add(campoEmail);
        add(new JLabel("Senha:"));
        add(campoSenha);
        add(botaoEntrar);
        add(botaoCadastrar);
        add(new JLabel());  // célula vazia
        add(botaoVoltar);

        // Ações dos botões
        botaoEntrar.addActionListener(e -> autenticarUsuario());
        botaoCadastrar.addActionListener(e -> {
            dispose();
            new TelaCadastroUsuario(janelaAnterior);
        });
        botaoVoltar.addActionListener(e -> {
            dispose();
            janelaAnterior.setVisible(true);
        });

        setVisible(true);
    }

    private void autenticarUsuario() {
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword()).trim();

        Usuario usuario = new UsuarioDAO().buscarPorEmail(email);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this,
                "E-mail não cadastrado.", "Erro",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!usuario.getSenha().equals(senha)) {
            JOptionPane.showMessageDialog(this,
                "Senha incorreta.", "Erro",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Login bem-sucedido
        JOptionPane.showMessageDialog(this,
            "Login bem‑sucedido!", "Sucesso",
            JOptionPane.INFORMATION_MESSAGE);
        dispose();

        // Abre a tela correta conforme o tipo de usuário
        if (usuario.getTipo().equalsIgnoreCase("medico")) {
            new TelaInicialMedico(usuario);
        } else {
            new TelaInicial(usuario);
        }
    }
}
