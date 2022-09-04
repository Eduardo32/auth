package com.pauloeduardocosta.auth.service.exception;

public class FuncionalidadeJaExistenteException extends RuntimeException {
    public FuncionalidadeJaExistenteException(String mensagem) {
        super(mensagem);
    }

    public FuncionalidadeJaExistenteException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
