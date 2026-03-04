package br.edu.ifba.saj.fwads.model;

public class ClienteFisico extends Cliente {
    private String cpf;

    public ClienteFisico() {
        super();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}