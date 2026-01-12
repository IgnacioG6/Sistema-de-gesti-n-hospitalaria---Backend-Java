package com.example.hospital.model.enums;

public enum TipoSangre {
    A_POSITIVO,
    A_NEGATIVO,
    B_POSITIVO,
    B_NEGATIVO,
    AB_POSITIVO,
    AB_NEGATIVO,
    O_POSITIVO,
    O_NEGATIVO;

    @Override
    public String toString() {
        return this.name().replace("_", " ");
    }
}
