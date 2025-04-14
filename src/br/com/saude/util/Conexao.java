package br.com.saude.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/saude_bem_estar";
    private static final String USUARIO = "root";
    private static final String SENHA = ""; // Adicione sua senha aqui se necessário

    //  Método para `getConexao`
    public static Connection getConexao() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados!", e);
        }
    }

    public static void main(String[] args) {
        // Testando a conexão
        Connection conexao = getConexao(); // Aqui também alteramos para `getConexao`
        if (conexao != null) {
            System.out.println("Conectado com sucesso!");
        }
    }
}
