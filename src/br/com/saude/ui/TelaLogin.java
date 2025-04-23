package br.com.saude.ui;

import javax.swing.*;
import java.awt.*;
import br.com.saude.dao.UsuarioDAO;
import br.com.saude.model.Usuario;
<<<<<<< HEAD
=======

>>>>>>> 733c997 (Telas finais adicionadas)

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

<<<<<<< HEAD
        if (usuario != null && usuario.getSenha().equals(senha)) {
            JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
            dispose(); // Fecha a tela de login
            new TelaCadastroPaciente(); // Abre a próxima tela (vamos mudar depois!)
        } else {
            JOptionPane.showMessageDialog(this, "Email ou senha incorretos.");
=======
        // Login bem-sucedido
        JOptionPane.showMessageDialog(this,
            "Login bem‑sucedido!", "Sucesso",
            JOptionPane.INFORMATION_MESSAGE);
        dispose();

        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Este e-mail não está cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (!usuario.getSenha().equals(senha)) {
            JOptionPane.showMessageDialog(this, "Senha incorreta.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
            dispose(); // Fecha a tela de login

            switch (usuario.getTipo()) {
                case "admin":
                    new TelaInicialAdmin(usuario); // ← Admin
                    break;
                case "medico":
                    new TelaInicialMedico(usuario); // ← Médico
                    break;
                case "paciente":
                    new TelaInicialPaciente(usuario); // ← Paciente
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Tipo de usuário desconhecido!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
>>>>>>> 733c997 (Telas finais adicionadas)
        }
    }

    public static void main(String[] args) {
        new TelaLogin();
    }
}

    
