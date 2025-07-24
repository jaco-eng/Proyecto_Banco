package com.ejemplo.mvc.model;

import java.time.LocalDate; 

public class Hora {
    private static Hora instancia;

    public Hora() {
    	
    }

    public static Hora getInstance() {
        if (instancia == null) {
            instancia = new Hora();
        }
        return instancia;
    }

    public LocalDate today() {
        return LocalDate.now();
    }

    public int year(LocalDate fecha) {
        return fecha.getYear();
    }
}
