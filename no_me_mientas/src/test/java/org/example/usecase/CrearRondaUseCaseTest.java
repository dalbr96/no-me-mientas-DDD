package org.example.usecase;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.RequestCommand;
import org.example.domain.juego.Jugador;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.juego.values.Name;
import org.example.domain.ronda.command.CrearRonda;
import org.example.domain.ronda.events.RondaCreada;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.beans.EventHandler;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CrearRondaUseCaseTest {

    @Test
    void crearRonda(){

        Map<JugadorId, Jugador> jugadoresRonda = Map.of(
                JugadorId.of("xxx-a"), new Jugador(JugadorId.of("xxx-a"), new Name("Alejandro")),
                JugadorId.of("xxx-b"), new Jugador(JugadorId.of("xxx-b"), new Name("Gerson"))
        );

        var command = new CrearRonda(jugadoresRonda);

        var crearRondaUseCase = new CrearRondaUseCase();

        var events = UseCaseHandler.getInstance()
                .syncExecutor(crearRondaUseCase, new RequestCommand<>(command))
                .orElseThrow().getDomainEvents();

        var rondaCreada = (RondaCreada)events.get(0);

        Assertions.assertEquals(2, rondaCreada.getJugadoresRonda().size());
        Assertions.assertEquals("Alejandro", rondaCreada.getJugadoresRonda().get(JugadorId.of("xxx-a")).nombre().value());
        Assertions.assertEquals("Gerson", rondaCreada.getJugadoresRonda().get(JugadorId.of("xxx-b")).nombre().value());
        Assertions.assertEquals(0, rondaCreada.getCapitalAcumulado().value());
    }



}