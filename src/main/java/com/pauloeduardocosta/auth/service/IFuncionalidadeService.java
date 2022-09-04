package com.pauloeduardocosta.auth.service;

import com.pauloeduardocosta.auth.dto.NovaFuncionalidadeDTO;
import com.pauloeduardocosta.auth.dto.FuncionalidadeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFuncionalidadeService {

    /**
     * Cria uma nova funcionalidade
     *
     * @param novaFuncionalidadeDTO DTO com os dados para criação da nova funcionalidade
     * @return DTO com os dados da nova funcionalidade criado
     */
    FuncionalidadeDTO criarFuncionalidade(NovaFuncionalidadeDTO novaFuncionalidadeDTO);

    /**
     * Busca de funcionalidades
     *
     * @param paginacao Pageable para paginação do resultado da busca
     * @return Pagina de DTOs de funcionalidades
     */
    Page<FuncionalidadeDTO> buscarTodos(Pageable paginacao);

    /**
     * Pesquisa de funcionalidades por nome
     *
     * @param nomeFuncionalidade Login que deseja pesquisar
     * @param paginacao Pageable para paginação do resultado da busca
     * @return Pagina de DTOs de funcionalidades
     */
    Page<FuncionalidadeDTO> buscarPorNome(String nomeFuncionalidade, Pageable paginacao);

    /**
     * Pesquisa de funcionalidade por ID
     *
     * @param id ID que deseja pesquisar
     * @return Dados do funcionalidade
     */
    FuncionalidadeDTO buscarPorId(Long id);

    /**
     * Atualizar uma funcionalidade
     *
     * @param id ID da funcionalidade que deseja alterar
     * @param novaFuncionalidadeDTO DTO com as novas informações da funcionalidade
     * @return DTO com a funcionalidade atualizada
     */
    FuncionalidadeDTO atualizarFuncionalidade(Long id, NovaFuncionalidadeDTO novaFuncionalidadeDTO);

    /**
     * Excluir uma funcionalidade
     *
     * @param id ID da funcionalidade que deseja excluir
     */
    void excluirFuncionalidade(Long id);
}
