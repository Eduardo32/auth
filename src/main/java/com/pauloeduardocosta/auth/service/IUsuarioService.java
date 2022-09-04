package com.pauloeduardocosta.auth.service;

import com.pauloeduardocosta.auth.dto.AtualizarUsuarioDTO;
import com.pauloeduardocosta.auth.dto.NovoUsuarioDTO;
import com.pauloeduardocosta.auth.dto.UsuarioCompletoDTO;
import com.pauloeduardocosta.auth.dto.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsuarioService {

    /**
     * Cria um novo usuario
     *
     * @param novoUsuarioDTO DTO com os dados para criação do novo usuario
     * @return DTO com os dados do novo usuario criado
     */
    UsuarioCompletoDTO criarUsuario(NovoUsuarioDTO novoUsuarioDTO);

    /**
     * Busca de usuarios
     *
     * @param paginacao Pageable para paginação do resultado da busca
     * @return Pagina de DTOs de usuarios
     */
    Page<UsuarioDTO> buscarTodos(Pageable paginacao);

    /**
     * Pesquisa de usuarios por login
     *
     * @param login Login que deseja pesquisar
     * @param paginacao Pageable para paginação do resultado da busca
     * @return Pagina de DTOs de usuarios
     */
    Page<UsuarioDTO> buscarPorLogin(String login, Pageable paginacao);

    /**
     * Pesquisa de usuario por ID
     *
     * @param id ID que deseja pesquisar
     * @return Dados do usuario
     */
    UsuarioCompletoDTO buscarPorId(Long id);

    /**
     * Atualizar um usuario
     *
     * @param id ID do usuario que deseja alterar
     * @param atualizarUsuarioDTO DTO com as novas informações do usuario
     * @return DTO com o usuario atualizada
     */
    UsuarioCompletoDTO atualizarUsuario(Long id, AtualizarUsuarioDTO atualizarUsuarioDTO);

    /**
     * Excluir um usuario
     *
     * @param id ID do usuario que deseja excluir
     */
    void excluirPerfil(Long id);
}
