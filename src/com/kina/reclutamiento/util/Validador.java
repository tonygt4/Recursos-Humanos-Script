package com.kina.reclutamiento.util;

import com.kina.reclutamiento.exception.ValidationException;

import java.util.regex.Pattern;

public final class Validador {
    private static final Pattern CORREO = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    private static final Pattern TELEFONO = Pattern.compile("^[0-9+\\-\\s]{7,20}$");

    private Validador() { }

    public static void requerido(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidationException("El campo \"" + campo + "\" es obligatorio");
        }
    }

    public static void correoValido(String correo) {
        if (!CORREO.matcher(correo.trim()).matches()) {
            throw new ValidationException("El correo no tiene un formato válido");
        }
    }

    public static void telefonoValido(String telefono) {
        if (!TELEFONO.matcher(telefono.trim()).matches()) {
            throw new ValidationException("El teléfono debe tener entre 7 y 20 dígitos");
        }
    }

    public static void edadEnRango(int edad, int min, int max) {
        if (edad < min || edad > max) {
            throw new ValidationException("La edad debe estar entre " + min + " y " + max);
        }
    }
}
