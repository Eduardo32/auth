package com.pauloeduardocosta.auth.v1.rs;

import com.pauloeduardocosta.auth.dto.NovoUsuarioDTO;
import com.pauloeduardocosta.auth.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/usuario")
public class UsuarioRS {

    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping
    @PreAuthorize("hasRole('CRIAR_USUARIO')")
    public ResponseEntity<Object> criarUsuario(@RequestBody NovoUsuarioDTO novoUsuarioDTO) {
        return ResponseEntity.ok().build();
    }
}
