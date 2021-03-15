package org.example.domain.juego.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.values.RondaId;

import java.util.List;
import java.util.Map;

public class RondaIniciada extends DomainEvent {

    private List<JugadorId> jugadoresIds;
    private Map<JugadorId, Dinero> capitales;
    private RondaId rondaId;

    public RondaIniciada(RondaId rondaId, List<JugadorId> jugadoresIds,
                         Map<JugadorId, Dinero> capitales) {

        super("nomemientas.RondaIniciada");

        this.rondaId = rondaId;
        this.jugadoresIds = jugadoresIds;
        this.capitales = capitales;

    }

    public List<JugadorId> getJugadoresIds() {
        return jugadoresIds;
    }


    public Map<JugadorId, Dinero> getCapitales() {
        return capitales;
    }

    public RondaId getRondaId() {
        return rondaId;
    }
}
