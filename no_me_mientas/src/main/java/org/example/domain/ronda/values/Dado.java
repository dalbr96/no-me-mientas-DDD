package org.example.domain.ronda.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;
import java.util.Random;

public class Dado implements ValueObject<Dado.Values> {

    private final Integer cara;
    private final Boolean destapado;

    public Dado() {

        Random rand = new Random();
        this.cara = (rand.nextInt(6) + 1);
        this.destapado = Boolean.FALSE;
    }

    private Dado(Integer cara){

        this.cara = cara;
        this.destapado = Boolean.TRUE;
    }

    public Dado destaparCara(){

        return new Dado(this.cara);
    }

    @Override
    public Values value() {
        return new Values(){

            @Override
            public Integer cara() {
                return cara;
            }

            @Override
            public Boolean estaDestapado() {
                return destapado;
            }
        };
    }

    public interface Values{

        Integer cara();

        Boolean estaDestapado();
    }
}
