package org.example.domain.ronda.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Adivinanza implements ValueObject<Adivinanza.Values> {

    private final Integer cara;
    private final Integer numeroRepeticiones;

    public Adivinanza(Integer cara, Integer numeroRepeticiones){

        this.cara = Objects.requireNonNull(cara, "Debe proveer una cara");

        if(cara > 6 || cara < 1){
            throw new IllegalArgumentException("La cara debe ser un numero entre 1 y 6");
        }

        this.numeroRepeticiones = Objects.requireNonNull(numeroRepeticiones, "Debe proveer el numero de repeticiones");

        if(numeroRepeticiones < 3){
            throw new IllegalArgumentException("El numero de repeticiones debe ser de al menos 3");
        }

    }

    @Override
    public Values value() {
        return null;
    }

    public interface Values {
        Integer cara();
        Integer carasRepetidas();
    }
}
