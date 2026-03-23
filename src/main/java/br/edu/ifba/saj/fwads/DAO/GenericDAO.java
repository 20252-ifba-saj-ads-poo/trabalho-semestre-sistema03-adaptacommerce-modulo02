package br.edu.ifba.saj.fwads.dao;

import br.edu.ifba.saj.fwads.model.AbstractModel;
import java.util.List;

public interface GenericDAO<T extends AbstractModel<ID>, ID> {
    ID salvar(T entidade);

    void atualizar(T entidade);

    T buscarPorId(ID id);

    void deletar(ID id);

    List<T> buscarTodos();

}
