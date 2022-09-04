package com.pauloeduardocosta.auth.service.exception;

public class PerfilJaExistenteException extends RuntimeException {
    public PerfilJaExistenteException(String mensagem) {
        super(mensagem);
    }

    public PerfilJaExistenteException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
