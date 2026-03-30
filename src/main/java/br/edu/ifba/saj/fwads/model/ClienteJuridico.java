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

    @Override
    public void validaIdentificacao() {
        String cnpjLimpo = limparMascara(getCnpj());
        if (cnpjLimpo.length() != 14) {
            throw new IllegalArgumentException("O CNPJ deve conter exatamente 14 dígitos numéricos.");
        }
        setCnpj(cnpjLimpo);
        
    }

    private String limparMascara(String texto) {
        if (texto == null)
            return "";
        return texto.replaceAll("[^0-9]", "");
    }

    @Override
    public String getIdentificacao() {
        return getCnpj();
    }

}
