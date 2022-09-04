package com.pauloeduardocosta.auth.repository;

import com.pauloeduardocosta.auth.dto.PerfilDTO;
import com.pauloeduardocosta.auth.entity.Perfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IPerfilRepository extends JpaRepository<Perfil, Long> {

    @Transactional(readOnly = true)
    Page<Perfil> findByNomeContainingIgnoreCase(String perfil, Pageable paginacao);

    @Transactional(readOnly = true)
    Optional<Perfil> findByNome(String nome);
}
