package com.pauloeduardocosta.auth.dto;

import com.pauloeduardocosta.auth.config.security.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCompletoDTO {

    private Long id;
    private String uuid;
    private String login;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private List<PerfilCompletoDTO> perfis = new ArrayList<>();

    public static UsuarioCompletoDTO montarDTO(Usuario usuario) {
        return UsuarioCompletoDTO.builder()
                .id(usuario.getId())
                .login(usuario.getLogin())
                .uuid(usuario.getUuid())
                .dataCriacao(usuario.getDataCriacao())
                .dataAtualizacao(usuario.getDataAtualizacao())
                .perfis(PerfilCompletoDTO.montarDTO(usuario.getPerfis()))
                .build();
    }
}
