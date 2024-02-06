package com.alm.crudspring.enums;

public enum Status {
    ACTIVE("Ativo"), INACTIVE("Inativo");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }
}
