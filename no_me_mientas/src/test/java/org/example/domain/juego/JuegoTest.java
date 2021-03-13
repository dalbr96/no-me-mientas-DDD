package org.example.domain.juego;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.events.JuegoCreado;
import org.example.domain.juego.events.JuegoIniciado;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.juego.values.Name;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JuegoTest {

    @Test
    void iniciarJuego_CasoFeliz() {

        var juegoId = JuegoId.of("xxxx-xxx");
        var juego = Juego.from(juegoId, eventList());

        juego.iniciarJuego();
        var juegoIniciado = (JuegoIniciado)juego.getUncommittedChanges().get(0);

        Assertions.assertEquals(juegoId.value(), juegoIniciado.aggregateRootId());

    }

    @Test
    void iniciarJuego_NoSuficientesJugadores(){

        var juegoId = JuegoId.of("xxx-xx");
        var juego = Juego.from(juegoId, eventListCasoTriste());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            juego.iniciarJuego();
        });
    }

    private List<DomainEvent> eventListCasoTriste() {
        Name nameJugador1 = new Name("Daniel");
        Name nameJugador2 = new Name("Alejandro");

        var jugadores = Map.of(
                JugadorId.of("xxx1"), new Jugador(JugadorId.of("xxx1"), nameJugador1),
                JugadorId.of("xxx2"), new Jugador(JugadorId.of("xxx2"), nameJugador2)

        );

        return List.of( new JuegoCreado(jugadores));
    }

    private List<DomainEvent> eventList() {
        Name nameJugador1 = new Name("Daniel");
        Name nameJugador2 = new Name("Alejandro");
        Name nameJugador3 = new Name("Burgos");
        var jugadores = Map.of(
                JugadorId.of("xxx1"), new Jugador(JugadorId.of("xxx1"), nameJugador1),
                JugadorId.of("xxx2"), new Jugador(JugadorId.of("xxx2"), nameJugador2),
                JugadorId.of("xxx3"), new Jugador(JugadorId.of("xxx3"), nameJugador3)

        );
        return List.of( new JuegoCreado(jugadores));
    }
}