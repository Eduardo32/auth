package com.pauloeduardocosta.auth.security.exception;

import javax.servlet.ServletException;

public class TesteException extends ServletException {

    public TesteException(String mensagem) {
        super(mensagem);
    }

    public TesteException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
