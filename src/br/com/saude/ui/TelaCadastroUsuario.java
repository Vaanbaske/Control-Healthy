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
        String nome = campoNome.getText().trim();
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword()).trim();
        String tipo = comboTipo.getSelectedItem().toString();

        // Verifica se o e-mail já está cadastrado
        UsuarioDAO dao = new UsuarioDAO();
        Usuario existente = dao.buscarPorEmail(email);

        if (existente != null) {
            JOptionPane.showMessageDialog(this, "Este usuário já existe!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Se não existir, cadastra
        Usuario novoUsuario = new Usuario(0, nome, email, senha, tipo);
        dao.inserirUsuario(novoUsuario);

        JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        campoNome.setText("");
        campoEmail.setText("");
        campoSenha.setText("");
    }

    public static void main(String[] args) {
        new TelaCadastroUsuario();
    }
}
