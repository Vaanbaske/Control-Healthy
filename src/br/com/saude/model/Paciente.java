package br.com.saude.model;

import br.com.saude.model.Usuario;

public class Paciente {
    private int id;
    private String nome;
    private int idade;
    private String endereco;
    private String telefone;
    private Usuario medico;  // campo para armazenar o médico

    // Construtor completo (sem o médico)
    public Paciente(int id, String nome, int idade, String endereco, String telefone) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    // Construtor vazio por medida preventiva
    public Paciente() {}

    // Getters e setters de todos os campos:
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    // Estes são os que faltavam:
    public Usuario getMedico() { return medico; }
    public void setMedico(Usuario medico) { this.medico = medico; }
}
