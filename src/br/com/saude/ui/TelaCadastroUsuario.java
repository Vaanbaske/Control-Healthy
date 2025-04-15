package br.com.saude.ui;

import javax.swing.*;
import java.awt.*;
import br.com.saude.dao.UsuarioDAO;
import br.com.saude.model.Usuario;

public class TelaCadastroUsuario extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField campoNome, campoEmail;
    private JPasswordField campoSenha;
    private JComboBox<String> comboTipo;
    private JButton botaoCadastrar, botaoVoltar;
    private JFrame janelaAnterior;  // guarda a janela que chamou

    // ← Construtor novo que recebe a janela anterior
    public TelaCadastroUsuario(JFrame anterior) {
        this.janelaAnterior = anterior;

        setTitle("Cadastro de Usuário");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));
        setLocationRelativeTo(null);

        campoNome   = new JTextField();
        campoEmail  = new JTextField();
        campoSenha  = new JPasswordField();
        comboTipo   = new JComboBox<>(new String[]{"paciente", "medico"});
        botaoCadastrar = new JButton("Cadastrar");
        botaoVoltar    = new JButton("← Voltar");

        add(new JLabel("Nome:"));  add(campoNome);
        add(new JLabel("Email:")); add(campoEmail);
        add(new JLabel("Senha:")); add(campoSenha);
        add(new JLabel("Tipo:"));  add(comboTipo);
        add(botaoCadastrar);       add(botaoVoltar);

        botaoCadastrar.addActionListener(e -> cadastrarUsuario());
        botaoVoltar.addActionListener(e -> {
            this.dispose();
            janelaAnterior.setVisible(true);
        });

        setVisible(true);
    }

    private void cadastrarUsuario() {
        String nome  = campoNome.getText().trim();
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword()).trim();
        String tipo  = comboTipo.getSelectedItem().toString();

        UsuarioDAO dao = new UsuarioDAO();
        if (dao.buscarPorEmail(email) != null) {
            JOptionPane.showMessageDialog(this, "Este usuário já existe!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dao.inserirUsuario(new Usuario(0, nome, email, senha, tipo));
        JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        campoNome.setText(""); campoEmail.setText(""); campoSenha.setText("");
    }
}
