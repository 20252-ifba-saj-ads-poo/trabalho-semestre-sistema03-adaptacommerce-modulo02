package br.edu.ifba.saj.fwads.service;

import br.edu.ifba.saj.fwads.dao.GenericDAO;
import br.edu.ifba.saj.fwads.dao.GenericDAOImpl;
import br.edu.ifba.saj.fwads.model.Cliente;
import br.edu.ifba.saj.fwads.model.ClienteFisico;
import br.edu.ifba.saj.fwads.model.ClienteJuridico;

import java.util.List;
import java.util.UUID;

public class ClienteService {

    private final GenericDAO<Cliente, UUID> dao;

    public ClienteService() {
        this.dao = new GenericDAOImpl<>(Cliente.class, UUID.class);
    }

    public void salvar(Cliente cliente) throws IllegalArgumentException {
        validarCliente(cliente);
        verificarDocumentoUnico(cliente);
        dao.salvar(cliente);
    }

    public void atualizar(Cliente cliente) throws IllegalArgumentException {
        validarCliente(cliente);
        verificarDocumentoUnico(cliente);
        dao.atualizar(cliente);
    }

    private void validarCliente(Cliente cliente) throws IllegalArgumentException {
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O Nome do cliente é obrigatório.");
        }

        String cepLimpo = limparMascara(cliente.getEndereco().getCep());
        if (cepLimpo.length() != 8) {
            throw new IllegalArgumentException("O CEP deve conter exatamente 8 dígitos numéricos.");
        }
        cliente.getEndereco().setCep(cepLimpo);

        if (cliente instanceof ClienteFisico pf) {
            String cpfLimpo = limparMascara(pf.getCpf());
            if (cpfLimpo.length() != 11) {
                throw new IllegalArgumentException("O CPF deve conter exatamente 11 dígitos numéricos.");
            }
            pf.setCpf(cpfLimpo);
        } else if (cliente instanceof ClienteJuridico pj) {
            String cnpjLimpo = limparMascara(pj.getCnpj());
            if (cnpjLimpo.length() != 14) {
                throw new IllegalArgumentException("O CNPJ deve conter exatamente 14 dígitos numéricos.");
            }
            pj.setCnpj(cnpjLimpo);
        }
    }

    private void verificarDocumentoUnico(Cliente clienteVerificado) throws IllegalArgumentException {
        List<Cliente> todosClientes = dao.buscarTodos();

        String docVerificado = (clienteVerificado instanceof ClienteFisico)
                ? ((ClienteFisico) clienteVerificado).getCpf()
                : ((ClienteJuridico) clienteVerificado).getCnpj();

        for (Cliente c : todosClientes) {
            if (clienteVerificado.getId() != null && clienteVerificado.getId().equals(c.getId())) {
                continue;
            }

            String docExistente = (c instanceof ClienteFisico)
                    ? ((ClienteFisico) c).getCpf()
                    : ((ClienteJuridico) c).getCnpj();

            if (docExistente != null && docExistente.equals(docVerificado)) {
                throw new IllegalArgumentException("Já existe um cliente cadastrado com este " +
                        (clienteVerificado instanceof ClienteFisico ? "CPF" : "CNPJ") + ".");
            }
        }
    }

    private String limparMascara(String texto) {
        if (texto == null)
            return "";
        return texto.replaceAll("[^0-9]", "");
    }
}