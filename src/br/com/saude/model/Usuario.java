package br.com.saude.model;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String tipo;
    private String endereco;
    private String telefone;

<<<<<<< HEAD
    // ✅ Construtor com parâmetros (este estava faltando!)
=======
>>>>>>> 733c997 (Telas finais adicionadas)
    public Usuario(int id, String nome, String email, String senha, String tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

<<<<<<< HEAD
    // ✅ Construtor vazio (opcional, mas útil)
    public Usuario() {}

    // ✅ Getters e Setters
=======
    public Usuario() {}

>>>>>>> 733c997 (Telas finais adicionadas)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
<<<<<<< HEAD
=======

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
>>>>>>> 733c997 (Telas finais adicionadas)
}
