package org.example.domain.ronda.command;

import co.com.sofka.domain.generic.Command;
import org.example.domain.juego.Jugador;
import org.example.domain.juego.values.JugadorId;

import java.util.Map;

public class CrearRonda implements Command {

    private final Map<JugadorId, Jugador> jugadores;

    public CrearRonda(Map<JugadorId, Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public Map<JugadorId, Jugador> getJugadores() {
        return jugadores;
    }
}
