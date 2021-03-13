package org.example.domain.ronda;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.Juego;
import org.example.domain.juego.Jugador;
import org.example.domain.juego.factory.JugadorFactory;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.juego.values.Name;
import org.example.domain.ronda.events.JugadorAgregadoARonda;
import org.example.domain.ronda.events.RondaCreada;
import org.example.domain.ronda.values.Dado;
import org.example.domain.ronda.values.Puntaje;
import org.example.domain.ronda.values.RondaId;

import java.util.*;

public class Ronda extends AggregateEvent<RondaId> {

    protected Map<JugadorId, Jugador> jugadoresRonda;
    protected Map<JugadorId, Puntaje> puntajes;
    protected Dinero capitalAcumulado;
    protected Set<Etapa> etapas;
    protected List<Dado> dados;
    protected JuegoId juegoId;


    private Ronda(RondaId entityId) {
        super(entityId);
        subscribe(new RondaChange(this));
    }

    public Ronda(RondaId entityId, JuegoId juegoId, JugadorFactory jugadorFactory){

        super(entityId);
        appendChange(new RondaCreada(entityId, juegoId)).apply();
        jugadorFactory.jugadores()
                .forEach(jugador -> agregarJugadorARonda(jugador.identity(), jugador.nombre(), jugador.capital()));
    }

    public static Ronda from(RondaId entityId, List<DomainEvent> events){
        var ronda = new Ronda(entityId);
        events.forEach(ronda::applyEvent);
        return ronda;
    }

    private void agregarJugadorARonda(JugadorId jugadorId, Name nombre, Dinero capital){
        appendChange(new JugadorAgregadoARonda(jugadorId, nombre, capital)).apply();
    }

    public Map<JugadorId, Jugador> jugadoresRonda() {
        return jugadoresRonda;
    }

    public Map<JugadorId, Puntaje> puntajes() {
        return puntajes;
    }

    public Dinero capitalAcumulado() {
        return capitalAcumulado;
    }

    public Set<Etapa> etapas() {
        return etapas;
    }

    public List<Dado> dados() {
        return dados;
    }
}
