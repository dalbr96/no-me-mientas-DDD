package org.example.domain.ronda.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JugadorId;

import java.util.List;
import java.util.Map;

public class EtapaCreada extends DomainEvent {
    private final Integer apuestaMaxima;
    private final List<JugadorId> jugadoresEtapa;

    public EtapaCreada(Integer apuestaMaxima, List<JugadorId> jugadoresEtapa ) {
        super("nomemientas.EtapaCreada");
        this.apuestaMaxima = apuestaMaxima;
        this.jugadoresEtapa = jugadoresEtapa;
    }

    public Integer getApuestaMaxima() {
        return apuestaMaxima;
    }

    public List<JugadorId> getJugadoresEtapa() {
        return jugadoresEtapa;
    }
}
