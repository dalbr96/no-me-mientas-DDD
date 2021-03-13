package org.example.usecase;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.Juego;
import org.example.domain.juego.command.AgregarJugador;
import org.example.domain.juego.events.JuegoCreado;
import org.example.domain.juego.events.JugadorAgregado;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AgregarJugadorUseCaseTest {

    @Mock
    DomainEventRepository repository;

    @Test
    void AgregarJugador_CasoFeliz(){

        var juegoId = JuegoId.of("xxxx");
        var command = new AgregarJugador(juegoId, JugadorId.of("xxxx-1"), new Name("Daniel"), new Dinero(300));
        var agregarJugadorUseCase = new AgregarJugadorUseCase();

        when(repository.getEventsBy(juegoId.value())).thenReturn(eventosCasoFeliz(juegoId));
        agregarJugadorUseCase.addRepository(repository);

        var events = UseCaseHandler.getInstance().setIdentifyExecutor(juegoId.value())
                .syncExecutor(agregarJugadorUseCase, new RequestCommand<>(command))
                .orElseThrow().getDomainEvents();

        List<DomainEvent> eventos = new ArrayList<>(eventosCasoFeliz(juegoId));
        eventos.add(events.get(0));

        var juegoConJugadorAgregado = Juego.from(juegoId, eventos);

        Assertions.assertEquals(2, juegoConJugadorAgregado.jugadores().size());
        Assertions.assertEquals("Daniel", juegoConJugadorAgregado.jugadores().get(JugadorId.of("xxxx-1")).nombre().value());
        Assertions.assertEquals("Alejandro", juegoConJugadorAgregado.jugadores().get(JugadorId.of("xxxx-2")).nombre().value());
    }

    @Test
    void AgregarJugador_ErrorEsperado(){
        var juegoId = JuegoId.of("xxxx");
        var command = new AgregarJugador(juegoId, JugadorId.of("xxxx-1"), new Name("Daniel"), new Dinero(300));
        var agregarJugadorUseCase = new AgregarJugadorUseCase();

        when(repository.getEventsBy(juegoId.value())).thenReturn(eventosErrorPorJugadores(juegoId));
        agregarJugadorUseCase.addRepository(repository);

        Assertions.assertThrows(BusinessException.class,()->{
            UseCaseHandler.getInstance().setIdentifyExecutor(juegoId.value())
                    .syncExecutor(agregarJugadorUseCase, new RequestCommand<>(command))
                    .orElseThrow().getDomainEvents();
        }, "No se puede realizar la operación pq ya se llegó al máximo de jugadores.");
    }

    private List<DomainEvent> eventosCasoFeliz(JuegoId juegoId) {
        var JuegoCreado = new JuegoCreado(juegoId);
        var JugadorAgregado = new JugadorAgregado(JugadorId.of("xxxx-2"), new Name("Alejandro"), new Dinero(500));
        return List.of(
                JuegoCreado,
                JugadorAgregado
        );
    }

    private List<DomainEvent> eventosErrorPorJugadores(JuegoId juegoId) {
        List<DomainEvent> events = new ArrayList<>();
        var JuegoCreado = new JuegoCreado(juegoId);
        events.add(JuegoCreado);
        for(int i = 0; i <24; i++){
            events.add( new JugadorAgregado(JugadorId.of(String.format("xxxx-%d", i)), new Name("Alejandro"), new Dinero(500)));
        }
        return events;
    }



}