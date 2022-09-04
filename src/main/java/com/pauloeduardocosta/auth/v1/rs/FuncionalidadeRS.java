package com.pauloeduardocosta.auth.v1.rs;

import com.pauloeduardocosta.auth.dto.FuncionalidadeDTO;
import com.pauloeduardocosta.auth.dto.NovaFuncionalidadeDTO;
import com.pauloeduardocosta.auth.service.IFuncionalidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;

@Api(value = "Funcionalidade", description = "Serviço de Funcionalidades", basePath = "v1")
@RestController
@RequestMapping("/v1/funcionalidade")
public class FuncionalidadeRS {

    @Autowired
    private IFuncionalidadeService funcionalidadeService;

    @ApiOperation(value = "Endpoint de listagem de funcionalidades",
            notes = "Endpoint para listagem de todos as funcionalidades cadastrados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Acesso negado")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Token de autorizacao",
                    required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Pagina de resultados que deseja recuperar (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Numero de resultados por página"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Criterios de ordenação dos resultados: propriedade(,asc|desc)")
    })
    @GetMapping
    @PreAuthorize("hasRole('LISTAR_FUNCIONALIDADE') OR hasRole('ADMIN')")
    public Page<FuncionalidadeDTO> buscarFuncionalidade(@ApiParam(value = "Funcionalidade que deseja buscar", example = "admin")
                                                            @RequestParam(required = false) String funcionalidade,
                                                        @ApiIgnore("Ignorados pq o swagger ui mostra os parametros errados.")
                                                        @PageableDefault(sort = "nome", direction = Sort.Direction.ASC) Pageable paginacao) {
        if(funcionalidade == null) {
            return funcionalidadeService.buscarTodos(paginacao);
        }
        return funcionalidadeService.buscarPorNome(funcionalidade, paginacao);
    }

    @ApiOperation(value = "Endpoint de busca de funcionalidade por ID",
            notes = "Endpoint para buscar funcionalidade por um ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Acesso negado"),
            @ApiResponse(code = 403, message = "Não encontrado")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Token de autorizacao",
                    required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('LISTAR_FUNCIONALIDADE') OR hasRole('ADMIN')")
    public ResponseEntity<FuncionalidadeDTO> buscarFuncionalidadeId(@ApiParam(value = "Id da funcionalidade de deseja buscar",
            example = "1", required = true) @PathVariable Long id) {
        FuncionalidadeDTO funcionalidadeDTO = funcionalidadeService.buscarPorId(id);
        return ResponseEntity.ok(funcionalidadeDTO);
    }

    @ApiOperation(value = "Endpoint de criação de funcionalidade",
            notes = "Endpoint para criação de uma nova funcionalidade")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Criado"),
            @ApiResponse(code = 400, message = "Funcionalidade Ja existente"),
            @ApiResponse(code = 403, message = "Acesso negado")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Token de autorizacao",
                    required = true, dataType = "string", paramType = "header")
    })
    @PostMapping
    @PreAuthorize("hasRole('CRIAR_FUNCIONALIDADE') OR hasRole('ADMIN')")
    public ResponseEntity<FuncionalidadeDTO> criarFuncionalidade(@Validated @RequestBody NovaFuncionalidadeDTO novaFuncionalidadeDTO,
                                                                 UriComponentsBuilder uriBuilder) {
        FuncionalidadeDTO funcionalidadeDTO = funcionalidadeService.criarFuncionalidade(novaFuncionalidadeDTO);
        URI uri = uriBuilder.path("/v1/funcionalidade/{id}").buildAndExpand(funcionalidadeDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(funcionalidadeDTO);
    }

    @ApiOperation(value = "Endpoint de atualização de funcionalidade",
            notes = "Endpoint para atualização de uma funcionalidade")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Acesso negado"),
            @ApiResponse(code = 403, message = "Não encontrado")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Token de autorizacao",
                    required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ATUALIZAR_FUNCIONALIDADE') OR hasRole('ADMIN')")
    public ResponseEntity<FuncionalidadeDTO> atualizarFuncionalidade(@ApiParam(value = "Id da funcionalidade que deseja atualizar", example = "1", required = true)
                                                                         @PathVariable Long id,
                                                                     @Validated @RequestBody NovaFuncionalidadeDTO novaFuncionalidadeDTO) {
        FuncionalidadeDTO funcionalidadeDTO = funcionalidadeService.atualizarFuncionalidade(id, novaFuncionalidadeDTO);
        return ResponseEntity.ok(funcionalidadeDTO);
    }

    @ApiOperation(value = "Endpoint de exclusão de funcionalidade",
            notes = "Endpoint para exclusão de uma funcionalidade")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Sem Conteudo"),
            @ApiResponse(code = 403, message = "Acesso negado"),
            @ApiResponse(code = 403, message = "Não encontrado")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Token de autorizacao",
                    required = true, dataType = "string", paramType = "header")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EXCLUIR_FUNCIONALIDADE') OR hasRole('ADMIN')")
    public ResponseEntity excluirFuncionalidade(@ApiParam(value = "Id da funcionalidade que deseja excluir", example = "1", required = true)
                                                    @PathVariable Long id) {
        funcionalidadeService.excluirFuncionalidade(id);
        return ResponseEntity.noContent().build();
    }
}
