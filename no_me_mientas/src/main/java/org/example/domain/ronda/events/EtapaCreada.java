package org.example.domain.ronda.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JugadorId;

import java.util.Map;

public class EtapaCreada extends DomainEvent {
    private final Map<JugadorId, Dinero> capitales;

    public EtapaCreada(Map<JugadorId, Dinero> capitales) {
        super("nomemientas.EtapaCreada");
        this.capitales = capitales;
    }

    public Map<JugadorId, Dinero> getCapitales() {
        return capitales;
    }
}
