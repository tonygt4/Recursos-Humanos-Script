package com.kina.reclutamiento.exception;

public class AspiranteNotFoundException extends AppException {
    public AspiranteNotFoundException(int id) { super("No existe el aspirante con ID " + id); }
}
