package com.pauloeduardocosta.auth.service;

import com.pauloeduardocosta.auth.entity.Perfil;

import java.util.List;

public interface IPerfilService {

    /**
     * Busca perfis dado uma lista de IDs
     *
     * @param perfis IDs dos perfis que dejas buscar
     * @return Lista de perfis encontrados
     */
    List<Perfil> buscarPerfis(List<Long> perfis);
}
