package org.example.domain.juego;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.events.JuegoCreado;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Juego extends AggregateEvent<JuegoId> {

    protected Map<JugadorId, Jugador> jugadores;
    protected Boolean juegoIniciado;
    protected Boolean tieneGanador;


    private Juego(JuegoId entityId) {
        super(entityId);
        subscribe(new JuegoChange(this));
    }

    public Juego(JuegoId entityId, Set<Jugador> jugadores){

        super(entityId);
        Map<JugadorId, Jugador> jugadoresNuevos = new HashMap<>();
        jugadores.forEach(jugador -> jugadoresNuevos.put(jugador.identity(), jugador));
        appendChange(new JuegoCreado(jugadoresNuevos)).apply();

    }

    public static Juego from(JuegoId entityId, List<DomainEvent> events){
        var juego = new Juego(entityId);
        events.forEach(juego::applyEvent);
        return juego;
    }


}
