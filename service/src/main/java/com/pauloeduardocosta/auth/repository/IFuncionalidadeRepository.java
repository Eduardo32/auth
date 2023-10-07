package com.pauloeduardocosta.auth.repository;

import com.pauloeduardocosta.auth.entity.Funcionalidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IFuncionalidadeRepository extends JpaRepository<Funcionalidade, Long> {

    @Transactional(readOnly = true)
    Page<Funcionalidade> findByNomeContainingIgnoreCase(String nome, Pageable paginacao);

    @Transactional(readOnly = true)
    Optional<Funcionalidade> findByNome(String nome);
}
