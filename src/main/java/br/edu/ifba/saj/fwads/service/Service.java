package br.edu.ifba.saj.fwads.service;


import java.util.List;

import br.edu.ifba.saj.fwads.dao.GenericDAO;
import br.edu.ifba.saj.fwads.dao.GenericDAOImpl;
import br.edu.ifba.saj.fwads.model.AbstractModel;

public abstract class Service<T extends AbstractModel<ID>, ID> {

    private GenericDAO<T, ID> dao;
    private final Class<T> classeEntidade;
    private final Class<ID> classeId;

    public Service(Class<T> classeEntidade, Class<ID> classeId) {
        this.classeEntidade = classeEntidade;
        this.classeId = classeId;
        this.dao = new GenericDAOImpl<>(classeEntidade, classeId);
    }

    public Service(Class<T> classeEntidade, Class<ID> classeId, GenericDAO<T, ID> dao) {
        this(classeEntidade, classeId);
        this.dao = dao;
    }

    public void salvar(T entidade) throws IllegalArgumentException {
        validar(entidade);
        dao.salvar(entidade);
    }

    public void atualizar(T entidade) throws IllegalArgumentException {
        validar(entidade);
        dao.atualizar(entidade);
    }

    public T buscarPorId(ID id){
        return dao.buscarPorId(id);
    }

    public void deletar(ID id){
        dao.deletar(id);
    }

    public List<T> buscarTodos(){
        return dao.buscarTodos();
    }


    public abstract void validar(T entidade);

}