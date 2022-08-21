package com.pauloeduardocosta.auth.v1.rs.exception;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class StandardError {

    private Integer status;
    private String mensagem;
    private Long timeStamp;

    public StandardError(Integer status, String mensagem, Long timeStamp) {
        this.status = status;
        this.mensagem = mensagem;
        this.timeStamp = timeStamp;
    }
}
