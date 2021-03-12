package org.example.domain.ronda.values;

import co.com.sofka.domain.generic.Identity;
import org.example.domain.juego.values.JuegoId;

public class EtapaId extends Identity {
    private EtapaId(String uid) {
        super(uid);
    }

    public EtapaId() { }

    public static EtapaId of(String uid){
        return new EtapaId(uid);
    }
}
