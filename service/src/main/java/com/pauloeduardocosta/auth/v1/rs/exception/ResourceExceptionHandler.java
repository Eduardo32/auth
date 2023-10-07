package com.pauloeduardocosta.auth.v1.rs.exception;

import com.pauloeduardocosta.auth.dto.CampoInvalidoDTO;
import com.pauloeduardocosta.auth.service.exception.AtualizacaoDeSenhaException;
import com.pauloeduardocosta.auth.service.exception.FuncionalidadeJaExistenteException;
import com.pauloeduardocosta.auth.service.exception.LoginJaExistenteException;
import com.pauloeduardocosta.auth.service.exception.ObjetoNaoEncotradoException;
import com.pauloeduardocosta.auth.service.exception.PerfilJaExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StandardError> AcessoNegado(AccessDeniedException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandardError> CredenciaisIncorretas(BadCredentialsException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidacaoError> ArgumentoInvalido(MethodArgumentNotValidException exception) {
        ValidacaoError validacaoError = new ValidacaoError(HttpStatus.BAD_REQUEST.value(), System.currentTimeMillis());
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(erro -> {
            String mensagem = messageSource.getMessage(erro, LocaleContextHolder.getLocale());
            CampoInvalidoDTO campoInvalidoDTO = new CampoInvalidoDTO(erro.getField(), mensagem);

            validacaoError.getCamposInvalidos().add(campoInvalidoDTO);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validacaoError);
    }

    @ExceptionHandler(ObjetoNaoEncotradoException.class)
    public ResponseEntity<StandardError> objetoNaoEncontrado(ObjetoNaoEncotradoException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(LoginJaExistenteException.class)
    public ResponseEntity<StandardError> loginEmUso(LoginJaExistenteException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(FuncionalidadeJaExistenteException.class)
    public ResponseEntity<StandardError> funcionalidadeJaExistente(FuncionalidadeJaExistenteException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(PerfilJaExistenteException.class)
    public ResponseEntity<StandardError> perfilJaExistente(PerfilJaExistenteException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AtualizacaoDeSenhaException.class)
    public ResponseEntity<StandardError> atualizacaoDeSenha(AtualizacaoDeSenhaException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
