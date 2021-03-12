package org.example.domain.juego.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Dinero implements ValueObject<Integer> {

    private Integer dinero;

    public Dinero(Integer dinero){

        this.dinero = Objects.requireNonNull(dinero, "Por favor Ingrese un capital");

        if(dinero < 0){
            throw new IllegalArgumentException("El capital debe ser mayor o igual a 0");
        }

    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dinero dinero1 = (Dinero) o;
        return Objects.equals(dinero, dinero1.dinero);

    }

    @Override
    public int hashCode() {
        return Objects.hash(dinero);
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
