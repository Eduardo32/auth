package com.pauloeduardocosta.auth.service;

import com.pauloeduardocosta.auth.dto.NovoUsuarioDTO;
import com.pauloeduardocosta.auth.dto.UsuarioCompletoDTO;
import com.pauloeduardocosta.auth.dto.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsuarioService {
    UsuarioCompletoDTO criarUsuario(NovoUsuarioDTO novoUsuarioDTO);

    Page<UsuarioDTO> buscarTodos(Pageable paginacao);

    Page<UsuarioDTO> buscarPorLogin(String login, Pageable paginacao);

    UsuarioCompletoDTO buscarPorId(Long id);
}
