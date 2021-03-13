package org.example.domain.juego.factory;

import org.example.domain.juego.Jugador;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.juego.values.Name;

import java.util.HashSet;
import java.util.Set;

public class JugadorFactory {

    private final Set<Jugador> jugadores;

    private JugadorFactory(){

        jugadores = new HashSet<>();
    }

    public static JugadorFactory builder(){return new JugadorFactory();}

    public JugadorFactory nuevoJugador(JugadorId JugadorId, Name nombre, Dinero capital){

        jugadores.add(new Jugador(JugadorId, nombre, capital));
        return this;
    }

    public Set<Jugador> jugadores() {
        return jugadores;
    }
}
