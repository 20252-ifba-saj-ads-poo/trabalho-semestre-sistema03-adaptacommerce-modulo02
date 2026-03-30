package br.edu.ifba.saj.fwads.dao;

import br.edu.ifba.saj.fwads.model.AbstractModel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericDAOImpl<T extends AbstractModel<ID>, ID> implements GenericDAO<T, ID> {
    private static final Map<Class<?>, Map<Object, Object>> bancoDeDadosMock = new HashMap<>();
    private final Class<T> classeEntidade;
    private final Class<ID> classeId;

    public GenericDAOImpl(Class<T> classeEntidade, Class<ID> classeId) {
        this.classeEntidade = classeEntidade;
        this.classeId = classeId;
        bancoDeDadosMock.putIfAbsent(classeEntidade, new HashMap<>());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected Map<ID, T> getTabela() {
        return (Map<ID, T>) (Map) bancoDeDadosMock.get(classeEntidade);
    }

    @Override
    public ID salvar(T entidade) {
        ID novoId = IdGenerator.gerarNovoId(classeId);
        entidade.setId(novoId);

        LocalDateTime agora = LocalDateTime.now();
        entidade.setCreatedAt(agora);
        //entidade.setUpdatedAt(agora);

        getTabela().put(entidade.getId(), entidade);
        return novoId;
    }

    @Override
    public void atualizar(T entidade) {
        if (getTabela().containsKey(entidade.getId())) {
            entidade.setUpdatedAt(LocalDateTime.now());
            getTabela().put(entidade.getId(), entidade);
        }
    }

    @Override
    public T buscarPorId(ID id) {
        return getTabela().get(id);
    }

    @Override
    public void deletar(ID id) {
        getTabela().remove(id);
    }

    @Override
    public List<T> buscarTodos() {
        return new ArrayList<>(getTabela().values());
    }
}