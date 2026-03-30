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

    @Override
    public void validaIdentificacao() {
        String cpfLimpo = limparMascara(getCpf());
        if (cpfLimpo.length() != 11) {
            throw new IllegalArgumentException("O CPF deve conter exatamente 11 dígitos numéricos.");
        }
        setCpf(cpfLimpo);
        
    }

    private String limparMascara(String texto) {
        if (texto == null)
            return "";
        return texto.replaceAll("[^0-9]", "");
    }

    @Override
    public String getIdentificacao() {
        return getCpf();
    }

    

   
}