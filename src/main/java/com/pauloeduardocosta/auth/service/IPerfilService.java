package com.pauloeduardocosta.auth.service;

import com.pauloeduardocosta.auth.entity.Perfil;

import java.util.List;

public interface IPerfilService {

    List<Perfil> montarListaPerfis(List<Long> perfilIds);
}
