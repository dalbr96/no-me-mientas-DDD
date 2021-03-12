package org.example.domain.juego.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Name implements ValueObject<String> {

    private final String name;

    public Name(String name){
        this.name = Objects.requireNonNull(name, "El nombre no puede ser nulo");

        if(name.isBlank()){
            throw new IllegalArgumentException("Por favor, Ingrese un nombre");
        }

    }


    @Override
    public String value() {
        return name;
    }
}
