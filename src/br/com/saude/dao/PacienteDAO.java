package br.com.saude.dao;

import br.com.saude.model.Paciente;
import br.com.saude.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    // Método para inserir um paciente no banco de dados
    public void inserirPaciente(Paciente paciente) {
        String sql = "INSERT INTO paciente (nome, idade, endereco, telefone) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNome());
            stmt.setInt(2, paciente.getIdade());
            stmt.setString(3, paciente.getEndereco());
            stmt.setString(4, paciente.getTelefone());

            stmt.executeUpdate();
            System.out.println("Paciente inserido com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para listar todos os pacientes
    public List<Paciente> listarPacientes() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM paciente";

        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Paciente p = new Paciente(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("endereco"),
                    rs.getString("telefone")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Método para excluir um paciente
    public void excluirPaciente(int id) {
        String sql = "DELETE FROM paciente WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Paciente excluído com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
