package br.com.saude.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import br.com.saude.util.Conexao;

public class PressaoDAO {

    public double calcularMediaSistolica(int idPaciente) {
        String sql = "SELECT AVG(sistolica) AS media_sis FROM pressao WHERE id_paciente = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPaciente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rs.getDouble("media_sis");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public double calcularMediaDiastolica(int idPaciente) {
        String sql = "SELECT AVG(diastolica) AS media_dia FROM pressao WHERE id_paciente = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPaciente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rs.getDouble("media_dia");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ✅ NOVO MÉTODO PARA O GRÁFICO
    public List<Registro> listarRegistros(int idPaciente) {
        List<Registro> registros = new ArrayList<>();
        String sql = "SELECT data, sistolica, diastolica FROM pressao WHERE id_paciente = ? ORDER BY data";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPaciente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Registro r = new Registro(
                    rs.getDate("data").toLocalDate(),
                    rs.getInt("sistolica"),
                    rs.getInt("diastolica")
                );
                registros.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return registros;
    }

    // ✅ CLASSE INTERNA USADA PELO GRÁFICO
    public static class Registro {
        private LocalDate data;
        private int sistolica;
        private int diastolica;

        public Registro(LocalDate data, int sistolica, int diastolica) {
            this.data = data;
            this.sistolica = sistolica;
            this.diastolica = diastolica;
        }

        public LocalDate getData() { return data; }
        public int getSistolica() { return sistolica; }
        public int getDiastolica() { return diastolica; }
    }
}
