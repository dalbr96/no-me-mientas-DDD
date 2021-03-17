package org.example.domain.ronda.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.JugadorId;

public class JugadorEliminado extends DomainEvent {

    private final JugadorId jugadorId;

    public JugadorEliminado(JugadorId jugadorId) {
        super("nomemientas.JugadorEliminado");
        this.jugadorId = jugadorId;
    }

    public JugadorId getJugadorId() {
        return jugadorId;
    }
}
