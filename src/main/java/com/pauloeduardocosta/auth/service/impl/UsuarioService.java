package com.pauloeduardocosta.auth.service.impl;

import com.pauloeduardocosta.auth.dto.AtualizarUsuarioDTO;
import com.pauloeduardocosta.auth.dto.NovoUsuarioDTO;
import com.pauloeduardocosta.auth.dto.UsuarioCompletoDTO;
import com.pauloeduardocosta.auth.dto.UsuarioDTO;
import com.pauloeduardocosta.auth.entity.Perfil;
import com.pauloeduardocosta.auth.entity.Usuario;
import com.pauloeduardocosta.auth.repository.IUsuarioRepository;
import com.pauloeduardocosta.auth.service.IPerfilService;
import com.pauloeduardocosta.auth.service.IUsuarioService;
import com.pauloeduardocosta.auth.service.exception.AtualizacaoDeSenhaException;
import com.pauloeduardocosta.auth.service.exception.LoginJaExistenteException;
import com.pauloeduardocosta.auth.service.exception.ObjetoNaoEncotradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<Perfil> perfis = perfilService.buscarPerfis(novoUsuarioDTO.getPerfis());
        validarPerfis(novoUsuarioDTO.getPerfis(), perfis);
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

    @Override
    @Transactional
    public UsuarioCompletoDTO atualizarUsuario(Long id, AtualizarUsuarioDTO atualizarUsuarioDTO) {
        LOGGER.info("Tentando atualizar o usuario com id {}", id);
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isEmpty()) {
            throw new ObjetoNaoEncotradoException("Usuario com id " + id + " não encontrado.");
        }
        if(atualizarUsuarioDTO.getLogin() != null && !atualizarUsuarioDTO.getLogin().isBlank()) {
            verificarLogin(atualizarUsuarioDTO.getLogin());
            usuario.get().setLogin(atualizarUsuarioDTO.getLogin());
        }
        if(atualizarUsuarioDTO.getPerfis() != null) {
            List<Perfil> perfis = perfilService.buscarPerfis(atualizarUsuarioDTO.getPerfis());
            validarPerfis(atualizarUsuarioDTO.getPerfis(), perfis);
            usuario.get().setPerfis(perfis);
        }
        if(atualizarUsuarioDTO.getNovaSenha() != null && !atualizarUsuarioDTO.getNovaSenha().isBlank()) {
            verificarSenha(usuario.get().getSenha(), atualizarUsuarioDTO.getSenhaAtual());
            usuario.get().setSenha(passwordEncoder.encode(atualizarUsuarioDTO.getNovaSenha()));
        }
        return UsuarioCompletoDTO.montarDTO(usuario.get());
    }

    @Override
    @Transactional
    public void excluirPerfil(Long id) {
        LOGGER.info("Tentando excluir o usuario com id {}", id);
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isEmpty()) {
            LOGGER.error("Usuario com id {} não encontrado", id);
            throw new ObjetoNaoEncotradoException("Usuario com id " + id + " não encontrado.");
        }
        usuarioRepository.delete(usuario.get());
    }

    private void verificarSenha(String senhaAtualCriptografada, String senhaAtual) {
        if(senhaAtual == null || senhaAtual.isBlank()) {
            throw new AtualizacaoDeSenhaException("Para atualizar sua senha você precisa informar sua senha atual.");
        }
        if(!passwordEncoder.matches(senhaAtual, senhaAtualCriptografada)) {
            throw new AtualizacaoDeSenhaException("Senha atual incorreta.");
        }
    }

    private void verificarLogin(String login) {
        LOGGER.info("Verificando uso do login {}", login);
        Optional<Usuario> usuario = usuarioRepository.findByLogin(login);
        if(usuario.isPresent()) {
            LOGGER.error("O login {} já está em uso", login);
            throw new LoginJaExistenteException("O login " + login + " Já esta em uso.");
        }
    }

    private void validarPerfis(List<Long> perfis, List<Perfil> perfisEncontrados) {
        perfis.forEach(id -> {
            Optional<Perfil> perfilEncontrado = perfisEncontrados.stream()
                    .filter(perfil -> perfil.getId().equals(id))
                    .findFirst();

            if(perfilEncontrado.isEmpty()) {
                LOGGER.error("Perfil não encotrado {}", id);
                throw new ObjetoNaoEncotradoException("Perfil com id: " + id + " não encontrado.");
            }
        });
    }
}
