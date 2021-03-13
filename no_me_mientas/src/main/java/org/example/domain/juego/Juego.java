package org.example.domain.juego;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.events.*;
import org.example.domain.juego.factory.JugadorFactory;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.juego.values.Name;
import org.example.domain.ronda.values.RondaId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


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
        //TODO: Buscar como conectar agregados mediante eventos.
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
