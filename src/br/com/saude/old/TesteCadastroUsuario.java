package br.com.saude.old;

import br.com.saude.dao.UsuarioDAO;
import br.com.saude.model.Usuario;

public class TesteCadastroUsuario {
    public static void main(String[] args) {
        // Criando um novo usuário
        Usuario novoUsuario = new Usuario(0, "João da Silva", "joao@email.com", "123456", "paciente");

        // Tentando inserir no banco
        UsuarioDAO dao = new UsuarioDAO();
        boolean sucesso = dao.inserirUsuario(novoUsuario);

        // Mostrando o resultado
        if (sucesso) {
            System.out.println("✅ Usuário inserido com sucesso!");
        } else {
            System.out.println("❌ Este usuário já existe.");
        }
    }
}
