package org.example.domain.ronda.command;

import co.com.sofka.domain.generic.Command;
import org.example.domain.ronda.values.RondaId;

public class LanzarDados implements Command {

    private final RondaId rondaId;


    public LanzarDados(RondaId rondaId) {
        this.rondaId = rondaId;
    }

    public RondaId getRondaId() {
        return rondaId;
    }
}
