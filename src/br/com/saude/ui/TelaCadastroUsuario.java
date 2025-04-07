package br.com.saude.ui;

import javax.swing.*;
import br.com.saude.dao.UsuarioDAO;
import br.com.saude.model.Usuario;
import java.awt.*;

public class TelaCadastroUsuario extends JFrame {
    private JTextField campoNome, campoEmail;
    private JPasswordField campoSenha;
    private JComboBox<String> comboTipo;
    private JButton botaoCadastrar;

    public TelaCadastroUsuario() {
        setTitle("Cadastro de Usuário");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        campoNome = new JTextField();
        campoEmail = new JTextField();
        campoSenha = new JPasswordField();
        comboTipo = new JComboBox<>(new String[]{"paciente", "medico"});
        botaoCadastrar = new JButton("Cadastrar");

        add(new JLabel("Nome:")); add(campoNome);
        add(new JLabel("Email:")); add(campoEmail);
        add(new JLabel("Senha:")); add(campoSenha);
        add(new JLabel("Tipo:")); add(comboTipo);
        add(new JLabel("")); add(botaoCadastrar);

        botaoCadastrar.addActionListener(e -> cadastrarUsuario());

        setVisible(true);
    }

    private void cadastrarUsuario() {
        String nome = campoNome.getText();
        String email = campoEmail.getText();
        String senha = new String(campoSenha.getPassword());
        String tipo = comboTipo.getSelectedItem().toString();

        Usuario usuario = new Usuario(0, nome, email, senha, tipo);
        new UsuarioDAO().inserirUsuario(usuario);

        JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");

        campoNome.setText("");
        campoEmail.setText("");
        campoSenha.setText("");
    }

    public static void main(String[] args) {
        new TelaCadastroUsuario();
    }
}
