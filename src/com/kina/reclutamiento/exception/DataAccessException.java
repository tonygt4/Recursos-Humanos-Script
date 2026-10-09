package com.kina.reclutamiento.exception;

public class DataAccessException extends AppException {
    public DataAccessException(String mensaje, Throwable causa) { super(mensaje, causa); }
    
    public DataAccessException(String mensaje) {
        super(mensaje);
    }
}
