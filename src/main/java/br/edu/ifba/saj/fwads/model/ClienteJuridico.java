package br.edu.ifba.saj.fwads.model;

public class ClienteJuridico extends Cliente {
    private String cnpj;
    private String inscricaoEstadual; // Costuma ter IE

    public ClienteJuridico() {
        super();
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }
}
