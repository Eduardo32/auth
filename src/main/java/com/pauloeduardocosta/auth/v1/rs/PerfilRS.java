package com.pauloeduardocosta.auth.v1.rs;

import com.pauloeduardocosta.auth.dto.AtualizarPerfilDTO;
import com.pauloeduardocosta.auth.dto.NovoPerfilDTO;
import com.pauloeduardocosta.auth.dto.PerfilCompletoDTO;
import com.pauloeduardocosta.auth.dto.PerfilDTO;
import com.pauloeduardocosta.auth.service.IPerfilService;
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

@Api(value = "Perfil", description = "Serviço de Perfis", basePath = "v1")
@RestController
@RequestMapping("/v1/perfil")
public class PerfilRS {
    
    @Autowired
    private IPerfilService perfilService;

    @ApiOperation(value = "Endpoint de listagem de perfis",
            notes = "Endpoint para listagem de todos as perfis cadastrados")
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
    @PreAuthorize("hasRole('LISTAR_PERFIL') OR hasRole('ADMIN')")
    public Page<PerfilDTO> buscarPerfis(@ApiParam(value = "Funcionalidade que deseja buscar", example = "admin")
                                                    @RequestParam(required = false) String perfil,
                                        @ApiIgnore("Ignorados pq o swagger ui mostra os parametros errados.")
                                                    @PageableDefault(sort = "nome", direction = Sort.Direction.ASC) Pageable paginacao) {
        if(perfil == null) {
            return perfilService.buscarTodos(paginacao);
        }
        return perfilService.buscarPorNome(perfil, paginacao);
    }

    @ApiOperation(value = "Endpoint de busca de perfil por ID",
            notes = "Endpoint para buscar um perfil por ID")
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
    @PreAuthorize("hasRole('LISTAR_PERFIL') OR hasRole('ADMIN')")
    public ResponseEntity<PerfilCompletoDTO> buscarPerfilId(@ApiParam(value = "Id do perfil que deseja buscar",
            example = "1", required = true) @PathVariable Long id) {
        PerfilCompletoDTO perfilCompletoDTO = perfilService.buscarPorId(id);
        return ResponseEntity.ok(perfilCompletoDTO);
    }

    @ApiOperation(value = "Endpoint de criação de perfil",
            notes = "Endpoint para criação de uma nova perfil")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Criado"),
            @ApiResponse(code = 400, message = "Perfil Ja existente"),
            @ApiResponse(code = 403, message = "Acesso negado")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Token de autorizacao",
                    required = true, dataType = "string", paramType = "header")
    })
    @PostMapping
    @PreAuthorize("hasRole('CRIAR_PERFIL') OR hasRole('ADMIN')")
    public ResponseEntity<PerfilCompletoDTO> criarPerfil(@Validated @RequestBody NovoPerfilDTO novoPerfilDTO,
                                                                 UriComponentsBuilder uriBuilder) {
        PerfilCompletoDTO perfilCompletoDTO = perfilService.criarPerfil(novoPerfilDTO);
        URI uri = uriBuilder.path("/v1/perfil/{id}").buildAndExpand(perfilCompletoDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(perfilCompletoDTO);
    }

    @ApiOperation(value = "Endpoint de atualização de perfil",
            notes = "Endpoint para atualização de um perfil")
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
    @PreAuthorize("hasRole('ATUALIZAR_PERFIL') OR hasRole('ADMIN')")
    public ResponseEntity<PerfilCompletoDTO> atualizarPerfil(@ApiParam(value = "Id do perfil que deseja atualizar", example = "1", required = true)
                                                                 @PathVariable Long id,
                                                             @Validated @RequestBody AtualizarPerfilDTO atualizarPerfilDTO) {
        PerfilCompletoDTO perfilCompletoDTO = perfilService.atualizarPerfil(id, atualizarPerfilDTO);
        return ResponseEntity.ok(perfilCompletoDTO);
    }

    @ApiOperation(value = "Endpoint de exclusão do perfil",
            notes = "Endpoint para exclusão de um perfil")
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
    @PreAuthorize("hasRole('EXCLUIR_PERFIL') OR hasRole('ADMIN')")
    public ResponseEntity excluirPerfil(@ApiParam(value = "Id do perfil que deseja excluir", example = "1", required = true)
                                                @PathVariable Long id) {
        perfilService.excluirPerfil(id);
        return ResponseEntity.noContent().build();
    }
}
