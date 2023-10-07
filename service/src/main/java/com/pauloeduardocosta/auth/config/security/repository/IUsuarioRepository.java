package com.pauloeduardocosta.auth.config.security.repository;

import com.pauloeduardocosta.auth.config.security.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    @Transactional(readOnly = true)
    Optional<Usuario> findByLogin(String login);

    @Transactional(readOnly = true)
    Page<Usuario> findByLoginContainingIgnoreCase(String login, Pageable paginacao);
}
