package com.pauloeduardocosta.auth.dto;

import com.pauloeduardocosta.auth.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String uuid;
    private String login;
    private List<PerfilDTO> perfis = new ArrayList<>();

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.uuid = usuario.getUuid();
        this.login = usuario.getLogin();
        this.perfis = PerfilDTO.montarDTO(usuario.getPerfis());
    }

    public static Page<UsuarioDTO> montarDTO(Page<Usuario> usuarios) {
        return usuarios.map(UsuarioDTO::new);
    }
}
