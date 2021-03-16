package org.example.domain.ronda.values;

import co.com.sofka.domain.generic.ValueObject;
import org.example.domain.juego.values.Dinero;

import java.util.Objects;


public class Apuesta implements ValueObject<Apuesta.Values> {

    private final Dinero dineroApostado;
    private final Adivinanza adivinanza;

    public Apuesta(Dinero dineroApostado, Adivinanza adivinanza){

        this.dineroApostado = Objects.requireNonNull(dineroApostado);
        this.adivinanza = Objects.requireNonNull(adivinanza, "Debe proveer una adivinanza");

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apuesta apuesta = (Apuesta) o;
        return Objects.equals(dineroApostado, apuesta.dineroApostado) && Objects.equals(adivinanza, apuesta.adivinanza);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dineroApostado, adivinanza);
    }

    @Override
    public Values value() {
        return new Values(){

            @Override
            public Dinero dineroApostado() {
                return dineroApostado;
            }

            @Override
            public Adivinanza adivinanzaRealizada() {
                return adivinanza;
            }
        };
    }

    public interface Values {

        Dinero dineroApostado();
        Adivinanza adivinanzaRealizada();

    }
}
