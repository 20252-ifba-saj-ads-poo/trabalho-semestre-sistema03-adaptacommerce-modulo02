package br.edu.ifba.saj.fwads.dao;

import java.util.UUID;

public class IdGenerator {
    @SuppressWarnings("unchecked")
    public static <ID> ID gerarNovoId(Class<ID> tipoIdClass) {
        if (tipoIdClass == UUID.class) {
            return (ID) UUID.randomUUID();
        }
        throw new IllegalArgumentException("Tipo de ID não suportado pelo gerador.");
    }
}
