package org.example.usecase;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.RequestCommand;
import org.example.domain.juego.Juego;
import org.example.domain.juego.Jugador;
import org.example.domain.juego.command.CrearJuego;
import org.example.domain.juego.events.JuegoCreado;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.juego.values.Name;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CrearJuegoUseCaseTest {

    @Test
    void crearJuego(){

        var nombres = Map.of(
                JugadorId.of("xxxx-1"), new Name("Daniel"),
                JugadorId.of("xxxx-2"), new Name("Alejandro")
                );

        var capitales = Map.of(
                JugadorId.of("xxxx-1"), new Dinero(500),
                JugadorId.of("xxxx-2"), new Dinero(600)
        );


        var command = new CrearJuego(nombres, capitales);
        var crearJuegoUseCase = new CrearJuegoUseCase();

        var events = UseCaseHandler
                .getInstance().syncExecutor(crearJuegoUseCase, new RequestCommand<>(command))
                .orElseThrow().getDomainEvents();

        var juegoCreado = (JuegoCreado)events.get(0);

        var juegoReconstruido = Juego.from(juegoCreado.getJuegoId(), events);

        Assertions.assertTrue(Objects.nonNull(juegoCreado.getJuegoId().value()));

        Assertions.assertEquals(2, juegoReconstruido.jugadores().size());
        Assertions.assertEquals("Daniel", juegoReconstruido.jugadores().get(JugadorId.of("xxxx-1")).nombre().value());
        Assertions.assertEquals(500, juegoReconstruido.jugadores().get(JugadorId.of("xxxx-1")).capital().value());

        Assertions.assertEquals("Alejandro", juegoReconstruido.jugadores().get(JugadorId.of("xxxx-2")).nombre().value());
        Assertions.assertEquals(600, juegoReconstruido.jugadores().get(JugadorId.of("xxxx-2")).capital().value());

    }

}