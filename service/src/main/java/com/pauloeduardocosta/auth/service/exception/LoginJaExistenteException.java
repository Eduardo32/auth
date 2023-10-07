package com.pauloeduardocosta.auth.service.exception;

public class LoginJaExistenteException extends RuntimeException {
    public LoginJaExistenteException(String mensagem) {
        super(mensagem);
    }

    public LoginJaExistenteException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
