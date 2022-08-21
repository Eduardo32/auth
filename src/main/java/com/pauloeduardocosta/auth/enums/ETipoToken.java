package com.pauloeduardocosta.auth.enums;

public enum ETipoToken {
    BEARER(1, "Bearer");

    private final int cod;
    private final String descricao;

    ETipoToken(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }
}
