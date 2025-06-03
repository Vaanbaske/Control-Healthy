package br.com.saude.dao;

import br.com.saude.model.Paciente;
import br.com.saude.model.Usuario;
import br.com.saude.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    // Insere um novo paciente, atribuindo-o a um médico 
    public void inserirPaciente(Paciente paciente) {
        String sql = "INSERT INTO pacientes (nome, idade, endereco, telefone, id_medico) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNome());
            stmt.setInt(2, paciente.getIdade());
            stmt.setString(3, paciente.getEndereco());
            stmt.setString(4, paciente.getTelefone());
            if (paciente.getMedico() != null) {
                stmt.setInt(5, paciente.getMedico().getId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retorna todos os pacientes, com o nome do médico responsável
    public List<Paciente> listarTodos() {
        List<Paciente> lista = new ArrayList<>();
        String sql =
            "SELECT p.id, p.nome, p.idade, p.endereco, p.telefone, " +
            "       u.id AS id_medico, u.nome AS nome_medico " +
            "  FROM pacientes p " +
            "  LEFT JOIN usuarios u ON p.id_medico = u.id";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Paciente p = new Paciente(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("endereco"),
                    rs.getString("telefone")
                );

                int idMed = rs.getInt("id_medico");
                if (!rs.wasNull()) {
                    Usuario med = new Usuario();
                    med.setId(idMed);
                    med.setNome(rs.getString("nome_medico"));
                    p.setMedico(med);
                }

                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Retorna somente os pacientes atribuídos ao médico de id informado. 
    public List<Paciente> listarPorMedico(int idMedico) {
        List<Paciente> lista = new ArrayList<>();
        String sql =
            "SELECT id, nome, idade, endereco, telefone " +
            "  FROM pacientes " +
            " WHERE id_medico = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedico);
            try (ResultSet rs = stmt.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Exclui um paciente pelo id.
    public void excluirPaciente(int id) {
        String sql = "DELETE FROM pacientes WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // NOVO: Lista pacientes que ainda NÃO têm médico atribuído
    public List<Paciente> listarSemMedico() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM pacientes WHERE id_medico IS NULL";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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

    // NOVO: Atribui um médico a um paciente
    public boolean atribuirMedico(int idPaciente, int idMedico) {
        String sql = "UPDATE pacientes SET id_medico = ? WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedico);
            stmt.setInt(2, idPaciente);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
