package com.luiz.restapi.enums;

public enum ProductStatus {
    AVAILABLE("Disponível"),
    OUT_OF_STOCK("Fora de estoque"),
    IN_PRODUCTION("Em produção");

    private final String desc;

    ProductStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String toString() {
        return desc;
    }
}
