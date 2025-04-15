package br.com.saude.dao;

import br.com.saude.model.Usuario;
import br.com.saude.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    // Buscar usuário por e-mail
    public Usuario buscarPorEmail(String email) {
        Usuario usuario = null;

        String sql = "SELECT * FROM usuarios WHERE email = ?"; // corrigido

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

    // Inserir usuário com verificação de duplicidade
    public boolean inserirUsuario(Usuario usuario) {
        // Verifica se já existe um usuário com esse e-mail
        Usuario existente = buscarPorEmail(usuario.getEmail());

        if (existente != null) {
            System.out.println("Atenção: Usuário já existe com esse e-mail.");
            return false;
        }

        String sql = "INSERT INTO usuarios (nome, email, senha, tipo) VALUES (?, ?, ?, ?)"; // corrigido

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getTipo());

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
