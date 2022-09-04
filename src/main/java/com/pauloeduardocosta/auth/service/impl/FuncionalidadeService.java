package com.pauloeduardocosta.auth.service.impl;

import com.pauloeduardocosta.auth.dto.FuncionalidadeDTO;
import com.pauloeduardocosta.auth.dto.NovaFuncionalidadeDTO;
import com.pauloeduardocosta.auth.entity.Funcionalidade;
import com.pauloeduardocosta.auth.repository.IFuncionalidadeRepository;
import com.pauloeduardocosta.auth.service.IFuncionalidadeService;
import com.pauloeduardocosta.auth.service.exception.LoginJaExistenteException;
import com.pauloeduardocosta.auth.service.exception.ObjetoNaoEncotradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FuncionalidadeService implements IFuncionalidadeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuncionalidadeService.class);

    @Autowired
    private IFuncionalidadeRepository funcionalidadeRepository;

    @Override
    public FuncionalidadeDTO criarFuncionalidade(NovaFuncionalidadeDTO novaFuncionalidadeDTO) {
        LOGGER.info("Salvando nova funcionalidade {}", novaFuncionalidadeDTO);
        verificandoFuncionalidade(novaFuncionalidadeDTO.getNome());
        Funcionalidade funcionalidade = Funcionalidade.builder()
                .nome(novaFuncionalidadeDTO.getNome())
                .build();
        funcionalidadeRepository.save(funcionalidade);
        return new FuncionalidadeDTO(funcionalidade);
    }

    @Override
    public Page<FuncionalidadeDTO> buscarTodos(Pageable paginacao) {
        LOGGER.info("Buscando todas as funcionalidade");
        Page<Funcionalidade> funcionalidades = funcionalidadeRepository.findAll(paginacao);
        return FuncionalidadeDTO.montarDTO(funcionalidades);
    }

    @Override
    public Page<FuncionalidadeDTO> buscarPorNome(String nomeFuncionalidade, Pageable paginacao) {
        LOGGER.info("Buscando funcionalidade por nome {}", nomeFuncionalidade);
        Page<Funcionalidade> funcionalidades =
                funcionalidadeRepository.findByNomeContainingIgnoreCase(nomeFuncionalidade, paginacao);
        return FuncionalidadeDTO.montarDTO(funcionalidades);
    }

    @Override
    public FuncionalidadeDTO buscarPorId(Long id) {
        LOGGER.info("Buscando funcionalidade por id {}", id);
        Optional<Funcionalidade> funcionalidade = funcionalidadeRepository.findById(id);
        if(funcionalidade.isEmpty()) {
            LOGGER.info("Funcionalidade com id {} não encontrada", id);
            throw new ObjetoNaoEncotradoException("Funcionalidade com id " + id + " não encontrada.");
        }
        return new FuncionalidadeDTO(funcionalidade.get());
    }

    @Override
    @Transactional
    public FuncionalidadeDTO atualizarFuncionalidade(Long id, NovaFuncionalidadeDTO novaFuncionalidadeDTO) {
        LOGGER.info("Tentando atualizar a funcionalidade comid {}", id);
        Optional<Funcionalidade> funcionalidade = funcionalidadeRepository.findById(id);
        if(funcionalidade.isEmpty()) {
            LOGGER.error("Funcionalidade com id {} não encontrada", id);
            throw new ObjetoNaoEncotradoException("Funcionalidade com id " + id + " não encontrada.");
        }
        funcionalidade.get().setNome(novaFuncionalidadeDTO.getNome());
        return new FuncionalidadeDTO(funcionalidade.get());
    }

    @Override
    @Transactional
    public void excluirFuncionalidade(Long id) {
        LOGGER.info("Tentando excluir a funcionalidade comid {}", id);
        Optional<Funcionalidade> funcionalidade = funcionalidadeRepository.findById(id);
        if(funcionalidade.isEmpty()) {
            LOGGER.error("Funcionalidade com id {} não encontrada", id);
            throw new ObjetoNaoEncotradoException("Funcionalidade com id " + id + " não encontrada.");
        }
        funcionalidadeRepository.delete(funcionalidade.get());
    }

    private void verificandoFuncionalidade(String nome) {
        LOGGER.info("Verificando se a funcionalidade já existe {}", nome);
        Optional<Funcionalidade> funcionalidade = funcionalidadeRepository.findByNome(nome);
        if(funcionalidade.isPresent()) {
            LOGGER.error("A funcionalidade {} já existe", nome);
            throw new LoginJaExistenteException("A funcionalidade " + nome + " Já existe.");
        }
    }
}
