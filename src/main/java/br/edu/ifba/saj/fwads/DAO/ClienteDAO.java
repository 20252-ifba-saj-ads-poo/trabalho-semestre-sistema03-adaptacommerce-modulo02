package br.edu.ifba.saj.fwads.dao;

import br.edu.ifba.saj.fwads.model.Cliente;

import java.time.LocalDateTime;

public class ClienteDAO extends GenericDAOImpl<Cliente, String> {

    public ClienteDAO() {
        super(Cliente.class, String.class);
    }

    @Override
    public String salvar(Cliente entidade) {
        entidade.setId(entidade.getIdentificacao());
        entidade.setCreatedAt(LocalDateTime.now());
        getTabela().put(entidade.getId(), entidade);
        return entidade.getIdentificacao();
    }

}