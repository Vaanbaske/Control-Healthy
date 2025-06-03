package br.com.saude.ui;

import br.com.saude.model.Usuario;
import br.com.saude.util.Conexao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TelaAlterarSenha extends JFrame {
    private Usuario usuario;
    private JPasswordField pfSenhaAtual, pfNovaSenha, pfConfirmar;

    public TelaAlterarSenha(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Alterar Senha");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        pfSenhaAtual = new JPasswordField();
        pfNovaSenha = new JPasswordField();
        pfConfirmar = new JPasswordField();

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(this::alterarSenha);

        add(new JLabel("Senha atual:")); add(pfSenhaAtual);
        add(new JLabel("Nova senha:")); add(pfNovaSenha);
        add(new JLabel("Confirmar senha:")); add(pfConfirmar);
        add(new JLabel("")); add(btnSalvar);

        setVisible(true);
    }

    private void alterarSenha(ActionEvent e) {
        String atual = new String(pfSenhaAtual.getPassword()).trim();
        String nova = new String(pfNovaSenha.getPassword()).trim();
        String confirmar = new String(pfConfirmar.getPassword()).trim();

        if (!atual.equals(usuario.getSenha())) {
            JOptionPane.showMessageDialog(this, "Senha atual incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!nova.equals(confirmar)) {
            JOptionPane.showMessageDialog(this, "Nova senha e confirmação não coincidem!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "UPDATE usuarios SET senha = ? WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nova);
            stmt.setInt(2, usuario.getId());
            stmt.executeUpdate();

            usuario.setSenha(nova); // atualiza localmente também
            JOptionPane.showMessageDialog(this, "Senha alterada com sucesso!");
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao alterar senha: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
