package br.edu.ifba.saj.fwads.model;

public abstract class Cliente {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private Endereco endereco;

    public Cliente() {
        this.endereco = new Endereco();
    }

    // --- GETTERS E SETTERS ---
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return nome;
    }
}