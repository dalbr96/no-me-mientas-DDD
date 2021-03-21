package org.example.domain.juego.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Dinero implements ValueObject<Integer> {

    private Integer dinero;
    private String id;

    public Dinero(Integer dinero){

        this.dinero = Objects.requireNonNull(dinero, "Por favor Ingrese un capital");

        if(dinero < 0){
            throw new IllegalArgumentException("El capital debe ser mayor o igual a 0");
        }

    }

    @Override
    public Integer value() {
        return dinero;
    }

    public Dinero aumentar(Integer aumentoACapital){
        return new Dinero(this.dinero + aumentoACapital);
    }

    public Dinero reducir(Integer reduccionACapital){
        return new Dinero(this.dinero - reduccionACapital);
    }
}
