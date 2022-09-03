package com.pauloeduardocosta.auth.v1.rs;

import com.pauloeduardocosta.auth.dto.LoginDTO;
import com.pauloeduardocosta.auth.dto.TokenDTO;
import com.pauloeduardocosta.auth.service.IAutenticacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Auth", description = "Serviço de Autenticação", basePath = "v1")
@RestController()
@RequestMapping("v1/auth")
public class AuthRS {

    @Autowired
    private IAutenticacaoService autenticacaoService;

    @ApiOperation(value = "Endpoint de autenticação", notes = "Endpoint para autenticação do usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Usuário inexistente ou senha inválida")
    })
    @PostMapping()
    public ResponseEntity<TokenDTO> autenticar(@RequestBody LoginDTO loginDTO) {
        TokenDTO tokenDTO = autenticacaoService.autenticar(loginDTO);
        return ResponseEntity.ok(tokenDTO);
    }
}
