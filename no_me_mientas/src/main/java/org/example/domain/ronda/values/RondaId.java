package org.example.domain.ronda.values;

import co.com.sofka.domain.generic.Identity;
import org.example.domain.juego.values.JugadorId;

public class RondaId extends Identity {

    private RondaId(String uid) {
        super(uid);
    }

    public RondaId() { }

    public static RondaId of(String uid){
        return new RondaId(uid);
    }

}
