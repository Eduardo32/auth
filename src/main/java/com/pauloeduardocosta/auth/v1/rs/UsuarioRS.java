package com.pauloeduardocosta.auth.v1.rs;

import com.pauloeduardocosta.auth.dto.NovoUsuarioDTO;
import com.pauloeduardocosta.auth.dto.UsuarioCompletoDTO;
import com.pauloeduardocosta.auth.dto.UsuarioDTO;
import com.pauloeduardocosta.auth.service.IUsuarioService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Api(value = "Usuario", description = "Serviço de Usuarios", basePath = "v1")
@RestController
@RequestMapping("/v1/usuario")
public class UsuarioRS {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("hasRole('LISTAR_USUARIO') OR hasRole('ADMIN')")
    public Page<UsuarioDTO> listarUsuarios(@RequestParam(required = false) String login,
                                           @PageableDefault(sort = "login", direction = Sort.Direction.ASC) Pageable paginacao) {
        if(login == null) {
            return usuarioService.buscarTodos(paginacao);
        }
        return usuarioService.buscarPorLogin(login, paginacao);
    }

    @ApiOperation(value = "Endpoint de busca por ID", notes = "Endpoint para busca de usuarios por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Acesso negado")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Token de autorizacao - Parametro obrigatorio",
                    required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('LISTAR_USUARIO') OR hasRole('ADMIN')")
    public ResponseEntity<UsuarioCompletoDTO> listarUsurarios(@ApiParam(value = "Id do usuario de deseja buscar",
            example = "1", required = true) @PathVariable Long id) {
        UsuarioCompletoDTO usuarioCompletoDTO = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuarioCompletoDTO);
    }


    @PostMapping
    @PreAuthorize("hasRole('CRIAR_USUARIO') OR hasRole('ADMIN')")
    public ResponseEntity<UsuarioCompletoDTO> criarUsuario(@Validated @RequestBody NovoUsuarioDTO novoUsuarioDTO,
                                                           UriComponentsBuilder uriBuilder) {
        UsuarioCompletoDTO usuarioCompletoDTO = usuarioService.criarUsuario(novoUsuarioDTO);
        URI uri = uriBuilder.path("/v1/usuario/{id}").buildAndExpand(usuarioCompletoDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(usuarioCompletoDTO);
    }
}
