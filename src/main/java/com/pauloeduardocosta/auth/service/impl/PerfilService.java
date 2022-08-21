package com.pauloeduardocosta.auth.service.impl;

import com.pauloeduardocosta.auth.entity.Perfil;
import com.pauloeduardocosta.auth.repository.IPerfilRepository;
import com.pauloeduardocosta.auth.service.IPerfilService;
import com.pauloeduardocosta.auth.service.exception.ObjetoNaoEncotradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PerfilService implements IPerfilService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerfilService.class);

    @Autowired
    private IPerfilRepository perfilRepository;

    @Override
    public List<Perfil> montarListaPerfis(List<Long> perfis) {
        List<Perfil> perfilList = new ArrayList<>();
        if(perfis.isEmpty()) {
            return perfilList;
        }
        LOGGER.info("Buscando lista de perfis {}", perfis);
        perfis.forEach(perfil -> {
            Optional<Perfil> perfilEncontrado = perfilRepository.findById(perfil);
            if(perfilEncontrado.isEmpty()) {
                LOGGER.error("Perfil não encotrado {}", perfil);
                throw new ObjetoNaoEncotradoException("Perfil com id: " + perfil + " não encontrado.");
            }
            perfilList.add(perfilEncontrado.get());
        });

        return perfilList;
    }
}
