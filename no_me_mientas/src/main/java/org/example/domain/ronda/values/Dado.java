package org.example.domain.ronda.values;

import co.com.sofka.domain.generic.ValueObject;

public class Dado implements ValueObject<Dado.Values> {

    private final Integer cara;
    private final Boolean destapado;

    public Dado(Integer cara) {

        this.cara = cara;
        this.destapado = Boolean.FALSE;

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
