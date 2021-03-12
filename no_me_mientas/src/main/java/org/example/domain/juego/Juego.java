package org.example.domain.juego;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.events.JuegoCreado;
import org.example.domain.juego.events.JuegoFinalizadoGanador;
import org.example.domain.juego.events.JuegoIniciado;
import org.example.domain.juego.events.JugadorAnhadido;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.juego.values.Name;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Juego extends AggregateEvent<JuegoId> {

    protected Map<JugadorId, Jugador> jugadores = new HashMap<>();
    protected Boolean juegoIniciado;
    protected Boolean hayGanador;


    private Juego(JuegoId entityId) {
        super(entityId);
        subscribe(new JuegoChange(this));
    }

    public Juego(JuegoId entityId, Set<Jugador> jugadores){

        super(entityId);
        HashMap<JugadorId, Jugador> jugadoresNuevos = new HashMap<>();
        jugadores.forEach(jugador -> jugadoresNuevos.put(jugador.identity(), jugador));
        appendChange(new JuegoCreado(jugadoresNuevos)).apply();

    }

    public static Juego from(JuegoId entityId, List<DomainEvent> events){
        var juego = new Juego(entityId);
        events.forEach(juego::applyEvent);
        return juego;
    }

    public void addJugador(JugadorId jugadorId, Name nombre){
        appendChange(new JugadorAnhadido(jugadorId, nombre)).apply();
    }

    public void iniciarJuego(){
        appendChange( new JuegoIniciado()).apply();
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
}
