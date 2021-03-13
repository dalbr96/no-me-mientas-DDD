package org.example.usecase;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.TriggeredEvent;
import org.example.domain.juego.Jugador;
import org.example.domain.juego.events.RondaIniciada;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.juego.values.Name;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.command.CrearRonda;
import org.example.domain.ronda.events.RondaCreada;
import org.example.domain.ronda.values.RondaId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.beans.EventHandler;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CrearRondaUseCaseTest {

    @Test
    void crearRonda(){

        var rondaId = RondaId.of("xxx");
        var jugadoresId = Set.of(JugadorId.of("xxx-1"), JugadorId.of("xxx-2"));
        var nombres = Map.of(
                JugadorId.of("xxx-1"), new Name("Daniel"),
                JugadorId.of("xxx-2"), new Name("Alejandro")
                );
        var capitales = Map.of(
                JugadorId.of("xxx-1"), new Dinero(400),
                JugadorId.of("xxx-2"), new Dinero(300)
        );

        var event = new RondaIniciada(rondaId, jugadoresId, nombres, capitales);

        event.setAggregateRootId("jjj");

        var crearRondaUseCase = new CrearRondaUseCase();

        var events = UseCaseHandler.getInstance()
                .syncExecutor(crearRondaUseCase, new TriggeredEvent<>(event))
                .orElseThrow().getDomainEvents();

        var rondaCreada = (RondaCreada)events.get(0);

        var rondaIniciada = Ronda.from(rondaId, events);

        Assertions.assertEquals("jjj", rondaCreada.getJuegoId().value());
        Assertions.assertEquals(0, rondaIniciada.capitalAcumulado().value());

        Assertions.assertEquals("Daniel", rondaIniciada.jugadoresRonda().get(JugadorId.of("xxx-1")).nombre().value());
        Assertions.assertEquals(400, rondaIniciada.jugadoresRonda().get(JugadorId.of("xxx-1")).capital().value());
        Assertions.assertEquals(0, rondaIniciada.puntajes().get(JugadorId.of("xxx-1")).value());

        Assertions.assertEquals("Alejandro", rondaIniciada.jugadoresRonda().get(JugadorId.of("xxx-2")).nombre().value());
        Assertions.assertEquals(300, rondaIniciada.jugadoresRonda().get(JugadorId.of("xxx-2")).capital().value());
        Assertions.assertEquals(0, rondaIniciada.puntajes().get(JugadorId.of("xxx-2")).value());
    }

    @Test
    void crearRonda_FaltaDeJugadores(){
        var rondaId = RondaId.of("xxx");
        var jugadoresId = Set.of(JugadorId.of("xxx-1"));
        var nombres = Map.of(
                JugadorId.of("xxx-1"), new Name("Daniel")
        );
        var capitales = Map.of(
                JugadorId.of("xxx-1"), new Dinero(400)
        );

        var event = new RondaIniciada(rondaId, jugadoresId, nombres, capitales);

        event.setAggregateRootId("jjj");

        var crearRondaUseCase = new CrearRondaUseCase();

        Assertions.assertThrows(BusinessException.class, () -> {
            UseCaseHandler.getInstance()
                    .syncExecutor(crearRondaUseCase, new TriggeredEvent<>(event))
                    .orElseThrow().getDomainEvents();
        }, "No se puede iniciar ronda por falta de jugadores");
    }


}