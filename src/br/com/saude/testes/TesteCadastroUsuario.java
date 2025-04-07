package br.com.saude.testes;

import br.com.saude.dao.UsuarioDAO;
import br.com.saude.model.Usuario;

public class TesteCadastroUsuario {
    public static void main(String[] args) {
        // Criando um novo usuário
        Usuario novoUsuario = new Usuario(0, "João da Silva", "joao@email.com", "123456", "paciente");

        // Inserindo no banco
        UsuarioDAO dao = new UsuarioDAO();
        dao.inserirUsuario(novoUsuario);

        System.out.println("Usuário inserido com sucesso!");
    }
}
