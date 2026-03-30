package br.edu.ifba.saj.fwads.service;

import br.edu.ifba.saj.fwads.dao.ClienteDAO;
import br.edu.ifba.saj.fwads.model.Cliente;


public class ClienteService extends Service<Cliente, String> {

    public ClienteService() {
        super(Cliente.class, String.class, new ClienteDAO());
    }

    public void validar(Cliente cliente) throws IllegalArgumentException {
        verificarDocumentoUnico(cliente);
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O Nome do cliente é obrigatório.");
        }

        // cliente.getEndereco().setCep(cepLimpo);
        cliente.validaIdentificacao();

    }

    private void verificarDocumentoUnico(Cliente clienteVerificado) throws IllegalArgumentException {
        Cliente clienteJaSalvo = buscarPorId(clienteVerificado.getIdentificacao());
        if (clienteJaSalvo != null) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com essa identificação");
        }
    }

}