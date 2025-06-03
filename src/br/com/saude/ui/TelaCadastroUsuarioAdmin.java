package br.com.saude.ui;

import br.com.saude.dao.UsuarioDAO;
import br.com.saude.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroUsuarioAdmin extends JFrame {
    private static final long serialVersionUID = 1L;
    private final TelaInicialAdmin telaAnterior;

    public TelaCadastroUsuarioAdmin(TelaInicialAdmin telaAnterior) {
        this.telaAnterior = telaAnterior;

        setTitle("Cadastro de Usuário");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        JTextField tfNome      = new JTextField();
        JTextField tfEmail     = new JTextField();
        JPasswordField pfSenha = new JPasswordField();
        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"paciente", "medico"});

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnVoltar    = new JButton("Voltar");

        add(new JLabel("Nome:"));   add(tfNome);
        add(new JLabel("Email:"));  add(tfEmail);
        add(new JLabel("Senha:"));  add(pfSenha);
        add(new JLabel("Tipo:"));   add(cbTipo);
        add(btnCadastrar);          add(btnVoltar);

        btnCadastrar.addActionListener(e -> {
            String nome  = tfNome.getText().trim();
            String email = tfEmail.getText().trim();
            String senha = new String(pfSenha.getPassword()).trim();
            String tipo  = cbTipo.getSelectedItem().toString();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Usuario novo = new Usuario(0, nome, email, senha, tipo);
            boolean sucesso = new UsuarioDAO().inserirUsuario(novo);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
                tfNome.setText("");
                tfEmail.setText("");
                pfSenha.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Usuário já existe!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVoltar.addActionListener(e -> {
            dispose();
            // usa o getter para recuperar o admin
            new TelaInicialAdmin(telaAnterior.getAdmin());
        });

        setVisible(true);
    }
}
