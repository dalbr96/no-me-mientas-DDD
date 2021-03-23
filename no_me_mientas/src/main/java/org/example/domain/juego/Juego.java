package org.example.domain.juego;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.events.*;
import org.example.domain.juego.factory.JugadorFactory;
import org.example.domain.juego.values.*;
import org.example.domain.ronda.values.CapitalesJugadoresRonda;
import org.example.domain.ronda.values.RondaId;

import java.util.*;
import java.util.stream.Collectors;


public class Juego extends AggregateEvent<JuegoId> {

    protected Map<JugadorId, Jugador> jugadores;
    protected Boolean juegoIniciado;
    protected Boolean hayGanador;
    protected RondaId rondaId;


    public Juego(JuegoId entityId, JugadorFactory jugadorFactory){
        super(entityId);
        appendChange(new JuegoCreado(entityId)).apply();
        jugadorFactory.jugadores()
                .forEach(jugador -> agregarJugador(jugador.identity(), jugador.nombre(), jugador.capital()));
    }

    private Juego(JuegoId entityId) {
        super(entityId);
        subscribe(new JuegoChange(this));
    }



    public static Juego from(JuegoId entityId, List<DomainEvent> events){
        var juego = new Juego(entityId);
        events.forEach(juego::applyEvent);
        return juego;
    }

    public void agregarJugador(JugadorId jugadorId, Name nombre, Dinero capital){
        appendChange(new JugadorAgregado(jugadorId, nombre, capital)).apply();
    }

    public void iniciarJuego(){
        appendChange( new JuegoIniciado()).apply();
    }

    public void iniciarRonda(){
        List<JugadorId> jugadoresId;
        List<CapitalesJugadoresRonda> jugadoresRonda = new ArrayList<>();

        jugadoresId = this.jugadores.entrySet().stream().
                filter(entry -> entry.getValue().capital().value() != 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        jugadoresId.forEach(jugadorId -> {
            jugadoresRonda.add(new CapitalesJugadoresRonda(this.jugadores.get(jugadorId).capital(), jugadorId));
        });

        RondaId rondaId = new RondaId();

        appendChange(new RondaIniciada(rondaId, jugadoresRonda)).apply();
    }

    public void finalizarJuegoGanador(){
        appendChange(new JuegoFinalizadoGanador()).apply();
    }

    public Map<JugadorId, Jugador> jugadores() {
        return jugadores;
    }

    public Boolean estaIniciado() {

        return juegoIniciado;
    }

    public Boolean hayGanador() {
        return hayGanador;
    }

    public RondaId rondaId() {
        return rondaId;
    }
}
