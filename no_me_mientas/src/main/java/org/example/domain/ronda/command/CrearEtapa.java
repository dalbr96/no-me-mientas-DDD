package org.example.domain.ronda.command;

import co.com.sofka.domain.generic.Command;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.values.RondaId;

import java.util.Map;

public class CrearEtapa implements Command {

    private  final RondaId rondaId;


    public CrearEtapa(RondaId rondaId) {
        this.rondaId = rondaId;
    }

    public RondaId getRondaId() {
        return rondaId;
    }

}
