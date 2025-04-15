package br.com.saude.ui;

import javax.swing.*;
import java.awt.*;

import br.com.saude.dao.UsuarioDAO;
import br.com.saude.model.Usuario;

public class TelaLogin extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoEntrar, botaoCadastrar, botaoVoltar;
    private JFrame janelaAnterior;

    public TelaLogin(JFrame anterior) {
        this.janelaAnterior = anterior;

        setTitle("Login");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2));

        campoEmail   = new JTextField();
        campoSenha   = new JPasswordField();
        botaoEntrar  = new JButton("Entrar");
        botaoCadastrar = new JButton("Cadastrar");
        botaoVoltar    = new JButton("← Voltar");

        add(new JLabel("Email:")); add(campoEmail);
        add(new JLabel("Senha:")); add(campoSenha);
        add(botaoEntrar); add(botaoCadastrar);
        add(new JLabel(""));    add(botaoVoltar);

        botaoEntrar.addActionListener(e -> autenticarUsuario());
        botaoCadastrar.addActionListener(e -> {
            this.dispose();
            new TelaCadastroUsuario(janelaAnterior);
        });
        botaoVoltar.addActionListener(e -> {
            this.dispose();
            janelaAnterior.setVisible(true);
        });

        setVisible(true);
    }

    private void autenticarUsuario() {
        String email = campoEmail.getText();
        String senha = new String(campoSenha.getPassword());

        Usuario usuario = new UsuarioDAO().buscarPorEmail(email);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "E-mail não cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (!usuario.getSenha().equals(senha)) {
            JOptionPane.showMessageDialog(this, "Senha incorreta.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
            this.dispose();
            new TelaInicial(usuario);
        }
    }
}
