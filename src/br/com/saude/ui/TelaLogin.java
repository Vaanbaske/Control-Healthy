package br.com.saude.ui;

import javax.swing.*;
import java.awt.*;
import br.com.saude.dao.UsuarioDAO;
import br.com.saude.model.Usuario;

public class TelaLogin extends JFrame {
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;
    private JButton botaoCadastrar;

    public TelaLogin() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        // Criando os componentes
        campoEmail = new JTextField();
        campoSenha = new JPasswordField();
        botaoEntrar = new JButton("Entrar");
        botaoCadastrar = new JButton("Cadastrar");
     // Adicionando componentes na tela
        add(new JLabel("Email:"));
        add(campoEmail);
        add(new JLabel("Senha:"));
        add(campoSenha);
        add(botaoEntrar);
        add(botaoCadastrar);

        // Definindo ação do botão
        botaoCadastrar.addActionListener(e -> {
            dispose(); // Fecha a tela de login
            new TelaCadastroUsuario(); // Abre a tela de cadastro
        });
        setVisible(true);
    }

    private void autenticarUsuario() {
        String email = campoEmail.getText();
        String senha = new String(campoSenha.getPassword());

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.buscarPorEmail(email);

        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Este e-mail não está cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (!usuario.getSenha().equals(senha)) {
            JOptionPane.showMessageDialog(this, "Senha incorreta.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
        	JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
        	dispose(); // Fecha a tela de login
        	new TelaInicial(usuario); // chama a nova tela, passando o usuário logado

        }
    }

    public static void main(String[] args) {
        new TelaLogin();
    }
}
