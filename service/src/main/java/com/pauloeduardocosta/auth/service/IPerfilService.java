package com.pauloeduardocosta.auth.service;

import com.pauloeduardocosta.auth.dto.AtualizarPerfilDTO;
import com.pauloeduardocosta.auth.dto.NovoPerfilDTO;
import com.pauloeduardocosta.auth.dto.PerfilCompletoDTO;
import com.pauloeduardocosta.auth.dto.PerfilDTO;
import com.pauloeduardocosta.auth.entity.Perfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPerfilService {

    /**
     * Busca perfis dado uma lista de IDs
     *
     * @param perfis IDs dos perfis que dejas buscar
     * @return Lista de perfis encontrados
     */
    List<Perfil> buscarPerfis(List<Long> perfis);

    /**
     * Busca de perfis
     *
     * @param paginacao Pageable para paginação do resultado da busca
     * @return Pagina de DTOs de perfis
     */
    Page<PerfilDTO> buscarTodos(Pageable paginacao);

    /**
     * Pesquisa de perfis por nome
     *
     * @param perfil Login que deseja pesquisar
     * @param paginacao Pageable para paginação do resultado da busca
     * @return Pagina de DTOs de perfis
     */
    Page<PerfilDTO> buscarPorNome(String perfil, Pageable paginacao);

    /**
     * Pesquisa de perfil por ID
     *
     * @param id ID que deseja pesquisar
     * @return Dados do perfil
     */
    PerfilCompletoDTO buscarPorId(Long id);

    /**
     * Cria uma novo perfil
     *
     * @param novoPerfilDTO DTO com os dados para criação do novo perfil
     * @return DTO com os dados do novo perfil criado
     */
    PerfilCompletoDTO criarPerfil(NovoPerfilDTO novoPerfilDTO);

    /**
     * Atualizar um perfil
     *
     * @param id ID do perfil que deseja alterar
     * @param novoPerfilDTO DTO com as novas informações do perfil
     * @return DTO com o perfil atualizada
     */
    PerfilCompletoDTO atualizarPerfil(Long id, AtualizarPerfilDTO novoPerfilDTO);

    /**
     * Excluir um perfil
     *
     * @param id ID do perfil que deseja excluir
     */
    void excluirPerfil(Long id);
}
