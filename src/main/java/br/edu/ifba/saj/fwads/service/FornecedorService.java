package br.edu.ifba.saj.fwads.service;

import br.edu.ifba.saj.fwads.model.Fornecedor;

import java.util.UUID;

public class FornecedorService extends Service<Fornecedor, UUID> {
    public FornecedorService() {
        super(Fornecedor.class, UUID.class);
    }

    public void validar(Fornecedor fornecedor) throws IllegalArgumentException {
        if (fornecedor.getNomeFantasia() == null || fornecedor.getNomeFantasia().trim().isEmpty()) {
            throw new IllegalArgumentException("O Nome Fantasia é obrigatório.");
        }
        if (fornecedor.getIe() == null || fornecedor.getIe().trim().isEmpty()) {
            throw new IllegalArgumentException("A Inscrição Estadual (IE) é obrigatória.");
        }

        String cnpjLimpo = limparMascara(fornecedor.getCnpj());
        if (cnpjLimpo.length() != 14) {
            throw new IllegalArgumentException("O CNPJ deve conter exatamente 14 dígitos numéricos.");
        }
        fornecedor.setCnpj(cnpjLimpo);

        String cepLimpo = limparMascara(fornecedor.getEndereco().getCep());
        if (cepLimpo.length() != 8) {
            throw new IllegalArgumentException("O CEP deve conter exatamente 8 dígitos numéricos.");
        }
        fornecedor.getEndereco().setCep(cepLimpo);

        if (fornecedor.getEndereco().getLogradouro() == null
                || fornecedor.getEndereco().getLogradouro().trim().isEmpty()) {
            throw new IllegalArgumentException("O Logradouro do endereço é obrigatório.");
        }
    }

    private String limparMascara(String texto) {
        if (texto == null)
            return "";
        return texto.replaceAll("[^0-9]", "");
    }


}