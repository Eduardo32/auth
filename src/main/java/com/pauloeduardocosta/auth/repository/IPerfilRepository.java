package com.pauloeduardocosta.auth.repository;

import com.pauloeduardocosta.auth.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPerfilRepository extends JpaRepository<Perfil, Long> {
}
