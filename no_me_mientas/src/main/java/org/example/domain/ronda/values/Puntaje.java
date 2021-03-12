package org.example.domain.ronda.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Puntaje implements ValueObject<Integer> {

    private final Integer puntaje;

    public Puntaje(){
        this.puntaje = 0;
    }

    public Puntaje(Integer puntaje){
        this.puntaje = Objects.requireNonNull( puntaje, "Debe especificar el puntaje");

        if(puntaje < 0){
            throw new IllegalArgumentException("El puntaje debe ser mayor o igual a 0");
        }
    }

    public Puntaje aumentar(Integer puntos){
        return new Puntaje(this.puntaje + puntos);
    }

    @Override
    public Integer value() {
        return puntaje;
    }
}
