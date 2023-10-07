package com.pauloeduardocosta.auth.service.impl;

import com.pauloeduardocosta.auth.dto.AtualizarPerfilDTO;
import com.pauloeduardocosta.auth.dto.NovoPerfilDTO;
import com.pauloeduardocosta.auth.dto.PerfilCompletoDTO;
import com.pauloeduardocosta.auth.dto.PerfilDTO;
import com.pauloeduardocosta.auth.entity.Funcionalidade;
import com.pauloeduardocosta.auth.entity.Perfil;
import com.pauloeduardocosta.auth.repository.IPerfilRepository;
import com.pauloeduardocosta.auth.service.IFuncionalidadeService;
import com.pauloeduardocosta.auth.service.IPerfilService;
import com.pauloeduardocosta.auth.service.exception.ObjetoNaoEncotradoException;
import com.pauloeduardocosta.auth.service.exception.PerfilJaExistenteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PerfilService implements IPerfilService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerfilService.class);

    @Autowired
    private IPerfilRepository perfilRepository;

    @Autowired
    private IFuncionalidadeService funcionalidadeService;

    @Override
    public List<Perfil> buscarPerfis(List<Long> perfis) {
        LOGGER.info("Buscando lista de perfis {}", perfis);
        return perfilRepository.findAllById(perfis);
    }

    @Override
    public Page<PerfilDTO> buscarTodos(Pageable paginacao) {
        LOGGER.info("Buscando todos os perfis");
        Page<Perfil> perfis = perfilRepository.findAll(paginacao);
        return PerfilDTO.montarDTO(perfis);
    }

    @Override
    public Page<PerfilDTO> buscarPorNome(String perfil, Pageable paginacao) {
        LOGGER.info("Pesquisando perfis por nome {}", perfil);
        Page<Perfil> perfis =
                perfilRepository.findByNomeContainingIgnoreCase(perfil, paginacao);
        return PerfilDTO.montarDTO(perfis);
    }

    @Override
    public PerfilCompletoDTO buscarPorId(Long id) {
        LOGGER.info("Buscando perfil por id {}", id);
        Optional<Perfil> perfil = perfilRepository.findById(id);
        if(perfil.isEmpty()) {
            LOGGER.info("Perfil com id {} não encontrada", id);
            throw new ObjetoNaoEncotradoException("Perfil com id " + id + " não encontrada.");
        }
        return new PerfilCompletoDTO(perfil.get());
    }

    @Override
    public PerfilCompletoDTO criarPerfil(NovoPerfilDTO novoPerfilDTO) {
        LOGGER.info("Salvando novo perfil {}", novoPerfilDTO);
        verificandoPerfil(novoPerfilDTO.getNome());
        List<Funcionalidade> funcionalidades = funcionalidadeService.buscarFuncionalidades(novoPerfilDTO.getFuncionalidades());
        validarFuncionalidade(novoPerfilDTO.getFuncionalidades(), funcionalidades);
        Perfil perfil = Perfil.builder()
                .nome(novoPerfilDTO.getNome())
                .funcionalidades(funcionalidades)
                .build();
        perfilRepository.save(perfil);
        return new PerfilCompletoDTO(perfil);
    }

    @Override
    @Transactional
    public PerfilCompletoDTO atualizarPerfil(Long id, AtualizarPerfilDTO atualizarPerfilDTO) {
        LOGGER.info("Tentando atualizar o perfil com id {}", id);
        Optional<Perfil> perfil = perfilRepository.findById(id);
        if(perfil.isEmpty()) {
            LOGGER.error("Perfil com id {} não encontrado", id);
            throw new ObjetoNaoEncotradoException("Perfil com id " + id + " não encontrado.");
        }
        if(atualizarPerfilDTO.getFuncionalidades() != null) {
            List<Funcionalidade> funcionalidades = funcionalidadeService.buscarFuncionalidades(atualizarPerfilDTO.getFuncionalidades());
            validarFuncionalidade(atualizarPerfilDTO.getFuncionalidades(), funcionalidades);
            perfil.get().setFuncionalidades(funcionalidades);
        }
        if(atualizarPerfilDTO.getNome() != null && !atualizarPerfilDTO.getNome().isBlank()) {
            perfil.get().setNome(atualizarPerfilDTO.getNome());
        }
        return new PerfilCompletoDTO(perfil.get());
    }

    @Override
    @Transactional
    public void excluirPerfil(Long id) {
        LOGGER.info("Tentando excluir o perfil com id {}", id);
        Optional<Perfil> perfil = perfilRepository.findById(id);
        if(perfil.isEmpty()) {
            LOGGER.error("Perfil com id {} não encontrado", id);
            throw new ObjetoNaoEncotradoException("Perfil com id " + id + " não encontrado.");
        }
        perfilRepository.delete(perfil.get());
    }

    private void validarFuncionalidade(List<Long> funcionalidades, List<Funcionalidade> funcionalidadesEncontradas) {
        funcionalidades.forEach(id -> {
            Optional<Funcionalidade> funcionalidadeEncontrado = funcionalidadesEncontradas.stream()
                    .filter(funcionalidade -> funcionalidade.getId().equals(id))
                    .findFirst();

            if(funcionalidadeEncontrado.isEmpty()) {
                LOGGER.error("Funcionalidade não encotrada {}", id);
                throw new ObjetoNaoEncotradoException("Funcionalidade com id: " + id + " não encontrada.");
            }
        });
    }

    private void verificandoPerfil(String nome) {
        LOGGER.info("Verificando se o perfil já existe {}", nome);
        Optional<Perfil> perfil = perfilRepository.findByNome(nome);
        if(perfil.isPresent()) {
            LOGGER.error("O perfil {} já existe", nome);
            throw new PerfilJaExistenteException("O perfil " + nome + " Já existe.");
        }
    }
}
