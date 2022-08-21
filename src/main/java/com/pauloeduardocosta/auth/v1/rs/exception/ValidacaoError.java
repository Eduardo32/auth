package com.pauloeduardocosta.auth.v1.rs.exception;

import com.pauloeduardocosta.auth.dto.CampoInvalidoDTO;

import java.util.ArrayList;
import java.util.List;

public class ValidacaoError {

    private Integer status;
    private List<CampoInvalidoDTO> camposInvalidos;
    private Long timeStamp;

    public ValidacaoError() {
    }
    public ValidacaoError(Integer status, Long timeStamp) {
        this.status = status;
        this.camposInvalidos = new ArrayList<>();
        this.timeStamp = timeStamp;
    }

    public Integer getStatus() {
        return status;
    }

    public List<CampoInvalidoDTO> getCamposInvalidos() {
        return camposInvalidos;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }
}
