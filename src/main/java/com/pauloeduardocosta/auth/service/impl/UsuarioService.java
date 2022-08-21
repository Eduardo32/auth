package com.pauloeduardocosta.auth.service.impl;

import com.pauloeduardocosta.auth.dto.FuncionalidadeDTO;
import com.pauloeduardocosta.auth.dto.NovoUsuarioDTO;
import com.pauloeduardocosta.auth.dto.PerfilCompletoDTO;
import com.pauloeduardocosta.auth.dto.UsuarioCompletoDTO;
import com.pauloeduardocosta.auth.dto.UsuarioDTO;
import com.pauloeduardocosta.auth.entity.Funcionalidade;
import com.pauloeduardocosta.auth.entity.Perfil;
import com.pauloeduardocosta.auth.entity.Usuario;
import com.pauloeduardocosta.auth.repository.IUsuarioRepository;
import com.pauloeduardocosta.auth.service.IPerfilService;
import com.pauloeduardocosta.auth.service.IUsuarioService;
import com.pauloeduardocosta.auth.service.exception.LoginJaExistenteException;
import com.pauloeduardocosta.auth.service.exception.ObjetoNaoEncotradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService implements IUsuarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IPerfilService perfilService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UsuarioCompletoDTO criarUsuario(NovoUsuarioDTO novoUsuarioDTO) {
        LOGGER.info("Salvando novo usuario {}", novoUsuarioDTO);
        verificarLogin(novoUsuarioDTO.getLogin());
        List<Perfil> perfis = perfilService.montarListaPerfis(novoUsuarioDTO.getPerfis());
        Usuario usuario = Usuario.builder()
                .login(novoUsuarioDTO.getLogin())
                .uuid(UUID.randomUUID().toString())
                .perfis(perfis)
                .senha(passwordEncoder.encode(novoUsuarioDTO.getSenha()))
                .build();
        usuarioRepository.save(usuario);

        return UsuarioCompletoDTO.montarDTO(usuario);
    }

    @Override
    public Page<UsuarioDTO> buscarTodos(Pageable paginacao) {
        Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
        return UsuarioDTO.montarDTO(usuarios);
    }

    @Override
    public Page<UsuarioDTO> buscarPorLogin(String login, Pageable paginacao) {
        Page<Usuario> usuarios = usuarioRepository.findByLoginContainingIgnoreCase(login, paginacao);
        return UsuarioDTO.montarDTO(usuarios);
    }

    @Override
    public UsuarioCompletoDTO buscarPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isEmpty()) {
            throw new ObjetoNaoEncotradoException("Usuario com id " + id + " não encontrado.");
        }
        return UsuarioCompletoDTO.montarDTO(usuario.get());
    }

    private void verificarLogin(String login) {
        LOGGER.info("Verificando uso do login {}", login);
        Optional<Usuario> usuario = usuarioRepository.findByLogin(login);
        if(usuario.isPresent()) {
            LOGGER.error("O login {} já está em uso", login);
            throw new LoginJaExistenteException("O login " + login + " Já esta em uso.");
        }
    }

    private Usuario buscarUsuarioLogado() {
        Optional<Usuario> usuarioLogado = (Optional<Usuario>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioLogado.get();
    }
}
