package org.example.usecase;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.Juego;
import org.example.domain.juego.Jugador;
import org.example.domain.juego.command.AddJugador;
import org.example.domain.juego.events.JuegoCreado;
import org.example.domain.juego.events.JugadorAnhadido;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddJugadorUseCaseTest {

    @Mock
    private DomainEventRepository repository;

    @Test
    void addJugador_CasoFeliz(){

        var juegoId = JuegoId.of("xxx");
        var jugador = new Jugador(JugadorId.of("xxx-p"), new Name("Daniel"));
        var command = new AddJugador(juegoId, jugador.identity(), jugador.nombre());

        when(repository.getEventsBy(juegoId.value())).thenReturn(domainEvents_CasoFeliz());

        var addJugadorUseCase = new AddJugadorUseCase();
        addJugadorUseCase.addRepository(repository);

        var events = UseCaseHandler.getInstance().setIdentifyExecutor(juegoId.value()).syncExecutor(addJugadorUseCase, new RequestCommand<>(command)).orElseThrow().getDomainEvents();

        var juegoConJugadorAñadido = (JugadorAnhadido)events.get(0);

        var updatedEvents = new ArrayList<DomainEvent>();
        domainEvents_CasoFeliz().forEach(event -> { updatedEvents.add(event); });
        updatedEvents.add(juegoConJugadorAñadido);

        var juego = Juego.from(juegoId, updatedEvents);

        Assertions.assertEquals("Daniel", juegoConJugadorAñadido.getNombre().value());
        Assertions.assertEquals(jugador.identity(), juegoConJugadorAñadido.getJugadorId());
        Assertions.assertEquals(3, juego.jugadores().size());
        Assertions.assertEquals(jugador, juego.jugadores().get(jugador.identity()));


    }

    @Test
    void addJugador_ErrorEsperado(){

        var juegoId = JuegoId.of("xxx");
        var jugador = new Jugador(JugadorId.of("xxx-p"), new Name("Daniel"));
        var command = new AddJugador(juegoId, jugador.identity(), jugador.nombre());

        when(repository.getEventsBy(juegoId.value())).thenReturn(domainEvents_ErrorEsperado());

        var addJugadorUseCase = new AddJugadorUseCase();
        addJugadorUseCase.addRepository(repository);

        Assertions.assertThrows(BusinessException.class, () -> {
            UseCaseHandler.getInstance().setIdentifyExecutor(juegoId.value()).syncExecutor(addJugadorUseCase, new RequestCommand<>(command)).orElseThrow().getDomainEvents();
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
        jugadores.put(JugadorId.of("xxx-1"), new Jugador(JugadorId.of("xxx-1"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-2"), new Jugador(JugadorId.of("xxx-2"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-3"), new Jugador(JugadorId.of("xxx-3"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-4"), new Jugador(JugadorId.of("xxx-4"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-5"), new Jugador(JugadorId.of("xxx-5"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-6"), new Jugador(JugadorId.of("xxx-6"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-7"), new Jugador(JugadorId.of("xxx-7"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-8"), new Jugador(JugadorId.of("xxx-8"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-9"), new Jugador(JugadorId.of("xxx-9"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-10"), new Jugador(JugadorId.of("xxx-10"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-11"), new Jugador(JugadorId.of("xxx-11"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-12"), new Jugador(JugadorId.of("xxx-12"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-13"), new Jugador(JugadorId.of("xxx-13"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-14"), new Jugador(JugadorId.of("xxx-14"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-15"), new Jugador(JugadorId.of("xxx-15"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-16"), new Jugador(JugadorId.of("xxx-16"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-17"), new Jugador(JugadorId.of("xxx-17"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-18"), new Jugador(JugadorId.of("xxx-18"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-19"), new Jugador(JugadorId.of("xxx-19"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-20"), new Jugador(JugadorId.of("xxx-20"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-21"), new Jugador(JugadorId.of("xxx-21"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-22"), new Jugador(JugadorId.of("xxx-22"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-23"), new Jugador(JugadorId.of("xxx-23"), new Name("Prueba")));
        jugadores.put(JugadorId.of("xxx-24"), new Jugador(JugadorId.of("xxx-24"), new Name("Prueba")));


        return List.of(

                new JuegoCreado(jugadores));
    }

}