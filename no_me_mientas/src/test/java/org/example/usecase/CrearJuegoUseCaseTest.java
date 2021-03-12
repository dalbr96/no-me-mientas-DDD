package org.example.usecase;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.RequestCommand;
import org.example.domain.juego.Jugador;
import org.example.domain.juego.command.CrearJuego;
import org.example.domain.juego.events.JuegoCreado;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.juego.values.Name;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CrearJuegoUseCaseTest {

    @Test
    void crearJuego(){

        Set<Jugador> jugadores = Set.of(
                new Jugador(JugadorId.of("xxx-1"), new Name("Daniel")),
                new Jugador(JugadorId.of("xxx-2"), new Name("Alejandro")),
                new Jugador(JugadorId.of("xxx-3"), new Name("Jorge")),
                new Jugador(JugadorId.of("xxx-4"), new Name("Gabriel"))
        );

        var command = new CrearJuego(jugadores);

        var crearJuegoUseCase = new CrearJuegoUseCase();
        var events = UseCaseHandler
                .getInstance().syncExecutor(crearJuegoUseCase, new RequestCommand<>(command))
                .orElseThrow().getDomainEvents();

        var juegoCreado = (JuegoCreado)events.get(0);

        Assertions.assertEquals(4, juegoCreado.getJugadores().size());
        Assertions.assertEquals("Daniel", juegoCreado.getJugadores().get(JugadorId.of("xxx-1")).nombre().value());
        Assertions.assertEquals("Alejandro", juegoCreado.getJugadores().get(JugadorId.of("xxx-2")).nombre().value());
        Assertions.assertEquals("Jorge", juegoCreado.getJugadores().get(JugadorId.of("xxx-3")).nombre().value());
        Assertions.assertEquals("Gabriel", juegoCreado.getJugadores().get(JugadorId.of("xxx-4")).nombre().value());

    }

}