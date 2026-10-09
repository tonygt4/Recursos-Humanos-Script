package com.kina.reclutamiento.exception;

public class AppException extends RuntimeException {
    public AppException(String mensaje) { super(mensaje); }
    public AppException(String mensaje, Throwable causa) { super(mensaje, causa); }
}
