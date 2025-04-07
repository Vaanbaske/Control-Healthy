package br.com.saude.dao;

import br.com.saude.model.Usuario;
import br.com.saude.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    // Método para buscar um usuário pelo e-mail
    public Usuario buscarPorEmail(String email) {
        Usuario usuario = null;

        String sql = "SELECT * FROM usuario WHERE email = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha"),
                    rs.getString("tipo")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }

    // Método para inserir um novo usuário no banco
    public void inserirUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email, senha, tipo) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getTipo());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
