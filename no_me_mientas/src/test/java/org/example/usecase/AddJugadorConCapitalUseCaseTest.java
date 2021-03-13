package org.example.usecase;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.Juego;
import org.example.domain.juego.Jugador;
import org.example.domain.juego.command.AddJugador;
import org.example.domain.juego.command.AddJugadorConCapital;
import org.example.domain.juego.events.JuegoCreado;
import org.example.domain.juego.events.JugadorAnhadido;
import org.example.domain.juego.events.JugadorAnhadidoConCapital;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.juego.values.Name;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddJugadorConCapitalUseCaseTest {

    @Mock
    private DomainEventRepository repository;

    @Test
    void addJugadorConCapital_CasoFeliz(){

        var juegoId = JuegoId.of("xxx");
        var jugador = new Jugador(JugadorId.of("xxx-p"), new Name("Daniel"), new Dinero(200));
        var command = new AddJugadorConCapital(juegoId, jugador.identity(), jugador.nombre(), jugador.capital());

        when(repository.getEventsBy(juegoId.value())).thenReturn(domainEvents_CasoFeliz());

        var addJugadorUseCaseConCapital = new AddJugadorConCapitalUseCase();
        addJugadorUseCaseConCapital.addRepository(repository);

        var events = UseCaseHandler.getInstance()
                .setIdentifyExecutor(juegoId.value())
                .syncExecutor(addJugadorUseCaseConCapital, new RequestCommand<>(command))
                .orElseThrow().getDomainEvents();

        var juegoConJugadorAñadido = (JugadorAnhadidoConCapital)events.get(0);

        var updatedEvents = new ArrayList<>(domainEvents_CasoFeliz());
        updatedEvents.add(juegoConJugadorAñadido);

        var juego = Juego.from(juegoId, updatedEvents);

        Assertions.assertEquals("Daniel", juegoConJugadorAñadido.getNombre().value());
        Assertions.assertEquals(jugador.identity(), juegoConJugadorAñadido.getJugadorId());
        Assertions.assertEquals(3, juego.jugadores().size());
        Assertions.assertEquals(jugador, juego.jugadores().get(jugador.identity()));


    }

    @Test
    void addJugadorConCapital_ErrorEsperado(){

        var juegoId = JuegoId.of("xxx");
        var jugador = new Jugador(JugadorId.of("xxx-p"), new Name("Daniel"), new Dinero(200));
        var command = new AddJugadorConCapital(juegoId, jugador.identity(), jugador.nombre(), jugador.capital());

        when(repository.getEventsBy(juegoId.value())).thenReturn(domainEvents_ErrorEsperado());

        var addJugadorUseCaseConCapital = new AddJugadorConCapitalUseCase();
        addJugadorUseCaseConCapital.addRepository(repository);

        Assertions.assertThrows(BusinessException.class, () -> {
            UseCaseHandler.getInstance().setIdentifyExecutor(juegoId.value()).syncExecutor(addJugadorUseCaseConCapital, new RequestCommand<>(command)).orElseThrow().getDomainEvents();
        });

    }

    private List<DomainEvent> domainEvents_CasoFeliz(){
        return List.of(
                new JuegoCreado(Map.of(
                        JugadorId.of("xxx-a"), new Jugador(JugadorId.of("xxx-a"), new Name("Alejandro")),
                        JugadorId.of("xxx-b"), new Jugador(JugadorId.of("xxx-b"), new Name("Gerson"))
                )));
    }

    private List<DomainEvent> domainEvents_ErrorEsperado(){

        Map<JugadorId, Jugador > jugadores = new HashMap<>();

        for(int i = 0; i<24; i++){
            jugadores.put(JugadorId.of(String.format("xxx-%d", i)), new Jugador(JugadorId.of(String.format("xxx-%d", i)), new Name("Prueba")));

        }

        return List.of(

                new JuegoCreado(jugadores));
    }
}