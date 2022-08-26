package com.pauloeduardocosta.auth.service.impl;

import com.pauloeduardocosta.auth.entity.Perfil;
import com.pauloeduardocosta.auth.repository.IPerfilRepository;
import com.pauloeduardocosta.auth.service.IPerfilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerfilService implements IPerfilService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerfilService.class);

    @Autowired
    private IPerfilRepository perfilRepository;

    @Override
    public List<Perfil> buscarPerfis(List<Long> perfis) {
        LOGGER.info("Buscando lista de perfis {}", perfis);
        return perfilRepository.findAllById(perfis);
    }
}
