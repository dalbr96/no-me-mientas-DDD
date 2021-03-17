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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adivinanza that = (Adivinanza) o;
        return Objects.equals(cara, that.cara) && Objects.equals(numeroRepeticiones, that.numeroRepeticiones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cara, numeroRepeticiones);
    }

    @Override
    public Values value() {
        return new Values() {
            @Override
            public Integer cara() {
                return cara;
            }

            @Override
            public Integer carasRepetidas() {
                return numeroRepeticiones;
            }
        };
    }

    public interface Values {
        Integer cara();
        Integer carasRepetidas();
    }
}
