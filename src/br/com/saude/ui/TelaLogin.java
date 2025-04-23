package br.com.saude.ui;

import javax.swing.*;
import java.awt.*;
import br.com.saude.dao.UsuarioDAO;
import br.com.saude.model.Usuario;

public class TelaLogin extends JFrame {
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;

    public TelaLogin() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        // Criando os componentes
        campoEmail = new JTextField();
        campoSenha = new JPasswordField();
        botaoEntrar = new JButton("Entrar");

        // Adicionando componentes na tela
        add(new JLabel("Email:"));
        add(campoEmail);
        add(new JLabel("Senha:"));
        add(campoSenha);
        add(new JLabel("")); // Espaço vazio para alinhamento
        add(botaoEntrar);

        // Definindo ação do botão
        botaoEntrar.addActionListener(e -> autenticarUsuario());

        setVisible(true);
    }

    private void autenticarUsuario() {
        String email = campoEmail.getText();
        String senha = new String(campoSenha.getPassword());

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.buscarPorEmail(email);

        if (usuario != null && usuario.getSenha().equals(senha)) {
            JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
            dispose(); // Fecha a tela de login
            new TelaCadastroPaciente(); // Abre a próxima tela (vamos mudar depois!)
        } else {
            JOptionPane.showMessageDialog(this, "Email ou senha incorretos.");
        }
    }

    public static void main(String[] args) {
        new TelaLogin();
    }
}
