package org.example.domain.ronda.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.Jugador;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.Etapa;
import org.example.domain.ronda.values.Dado;
import org.example.domain.ronda.values.Puntaje;

import java.util.*;

public class RondaCreada extends DomainEvent {

    private final Map<JugadorId, Jugador> jugadoresRonda;
    private final Map<JugadorId, Puntaje> puntajes;
    private final List<Dado> dados;
    private final Dinero capitalAcumulado;
    private final Set<Etapa> etapas;

    public RondaCreada(Map<JugadorId, Jugador> jugadoresRonda, Map<JugadorId, Puntaje> puntajes) {
        super("nomemientas.RondaCreada");
        this.jugadoresRonda = jugadoresRonda;
        this.puntajes = puntajes;
        this.dados = new ArrayList<>();
        this.capitalAcumulado = new Dinero(0);
        this.etapas = Collections.emptySet();
    }

    public Map<JugadorId, Jugador> getJugadoresRonda() {
        return jugadoresRonda;
    }

    public Map<JugadorId, Puntaje> getPuntajes() {
        return puntajes;
    }

    public List<Dado> getDados() {
        return dados;
    }

    public Dinero getCapitalAcumulado() {
        return capitalAcumulado;
    }

    public Set<Etapa> getEtapas() {
        return etapas;
    }
}
