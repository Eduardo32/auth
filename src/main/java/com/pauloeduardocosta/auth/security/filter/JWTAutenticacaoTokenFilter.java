package com.pauloeduardocosta.auth.security.filter;

import com.pauloeduardocosta.auth.entitie.Usuario;
import com.pauloeduardocosta.auth.repository.IUsuarioRepository;
import com.pauloeduardocosta.auth.security.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JWTAutenticacaoTokenFilter extends OncePerRequestFilter {

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_PREFIXO = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = recuperarToken(request);
        Boolean valido = tokenService.isTokenValido(token);
        if(valido) {
            autenticarCliente(token);
        }
        filterChain.doFilter(request, response);
    }

    private void autenticarCliente(String token) {
        Long idUsuario = tokenService.getIdUsuario(token);
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(usuario, null, usuario.get().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recuperarToken(HttpServletRequest request) {
        String authorization = request.getHeader(AUTH_HEADER);
        if(authorization == null || authorization.isEmpty() || !authorization.startsWith(TOKEN_PREFIXO)) {
            return null;
        }
        return authorization.split(" ")[1];
    }
}
