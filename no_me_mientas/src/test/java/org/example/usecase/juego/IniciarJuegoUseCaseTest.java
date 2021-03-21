package org.example.usecase.juego;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.Juego;
import org.example.domain.juego.command.IniciarJuego;
import org.example.domain.juego.events.JuegoCreado;
import org.example.domain.juego.events.JuegoIniciado;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IniciarJuegoUseCaseTest {

    @Mock
    private DomainEventRepository repository;

    @Test
    void iniciarJuego_CasoFeliz(){
        var juegoId = JuegoId.of("xxx");
        var command = new IniciarJuego(juegoId);
        var useCase = new IniciarJuegoUseCase();

        when(repository.getEventsBy(juegoId.value())).thenReturn(domainEvents_CasoFeliz(juegoId));

        useCase.addRepository(repository);

        var events = UseCaseHandler.getInstance()
                .setIdentifyExecutor(juegoId.value())
                .syncExecutor(useCase, new RequestCommand<>(command))
                .orElseThrow().getDomainEvents();

        var juegoIniciado = (JuegoIniciado)events.get(0);

        List<DomainEvent> eventos = new ArrayList<>(domainEvents_CasoFeliz(juegoId));
        eventos.add(juegoIniciado);

        var juegoReconstruido = Juego.from(juegoId, eventos);

        Assertions.assertEquals(3, juegoReconstruido.jugadores().size());

        Assertions.assertEquals("Daniel", juegoReconstruido.jugadores().get(JugadorId.of("xxx-1")).nombre().value());
        Assertions.assertEquals(200, juegoReconstruido.jugadores().get(JugadorId.of("xxx-1")).capital().value());

        Assertions.assertEquals("Alejandro", juegoReconstruido.jugadores().get(JugadorId.of("xxx-2")).nombre().value());
        Assertions.assertEquals(200, juegoReconstruido.jugadores().get(JugadorId.of("xxx-2")).capital().value());

    }

    @Test
    void iniciarJuego_juegoYaIniciada(){
        var juegoId = JuegoId.of("xxx");
        var command = new IniciarJuego(juegoId);
        var useCase = new IniciarJuegoUseCase();

        when(repository.getEventsBy(juegoId.value())).thenReturn(domainEvents_JuegoRondaYaIniciado(juegoId));

        useCase.addRepository(repository);

        Assertions.assertThrows(BusinessException.class, () ->{
            UseCaseHandler.getInstance()
                    .setIdentifyExecutor(juegoId.value())
                    .syncExecutor(useCase, new RequestCommand<>(command))
                    .orElseThrow().getDomainEvents();
        }, "El juego ya está iniciado");

    }

    @Test
    void iniciarJuego_insuficientesJugadores(){
        var juegoId = JuegoId.of("xxx");
        var command = new IniciarJuego(juegoId);
        var useCase = new IniciarJuegoUseCase();

        when(repository.getEventsBy(juegoId.value())).thenReturn(domainEvents_insuficientesJugadores(juegoId));

        useCase.addRepository(repository);

        Assertions.assertThrows(BusinessException.class, () ->{
            UseCaseHandler.getInstance()
                    .setIdentifyExecutor(juegoId.value())
                    .syncExecutor(useCase, new RequestCommand<>(command))
                    .orElseThrow().getDomainEvents();
        }, "Se necesitan al menos 2 jugadores para comenzar el juego.");
    }

    @Test
    void iniciarJuego_excesoDeJugadores(){
        var juegoId = JuegoId.of("xxx");
        var command = new IniciarJuego(juegoId);
        var useCase = new IniciarJuegoUseCase();

        when(repository.getEventsBy(juegoId.value())).thenReturn(domainEvents_excesoDeJugadores(juegoId));

        useCase.addRepository(repository);

        Assertions.assertThrows(BusinessException.class, () ->{
            UseCaseHandler.getInstance()
                    .setIdentifyExecutor(juegoId.value())
                    .syncExecutor(useCase, new RequestCommand<>(command))
                    .orElseThrow().getDomainEvents();
        }, "No se puede empezar el juego, hay más jugadores que los permitidos");
    }


    private List<DomainEvent> domainEvents_CasoFeliz(JuegoId juegoId) {
        List<DomainEvent> eventos =  List.of(
                new JuegoCreado(juegoId),
                new JugadorAgregado(JugadorId.of("xxx-1"), new Name("Daniel"), new Dinero(200)),
                new JugadorAgregado(JugadorId.of("xxx-2"), new Name("Alejandro"), new Dinero(200)),
                new JugadorAgregado(JugadorId.of("xxx-3"), new Name("Irenarco"), new Dinero(200))

        );
        return eventos;
    }

    private List<DomainEvent> domainEvents_insuficientesJugadores(JuegoId juegoId) {
        List<DomainEvent> eventos =  List.of(
                new JuegoCreado(juegoId),
                new JugadorAgregado(JugadorId.of("xxx-1"), new Name("Daniel"), new Dinero(200))
        );
        return eventos;
    }

    private List<DomainEvent> domainEvents_excesoDeJugadores(JuegoId juegoId) {
        List<DomainEvent> events = new ArrayList<>();
        var JuegoCreado = new JuegoCreado(juegoId);
        events.add(JuegoCreado);
        for(int i = 0; i <=24; i++){
            events.add( new JugadorAgregado(JugadorId.of(String.format("xxxx-%d", i)), new Name("Alejandro"), new Dinero(500)));
        }
        return events;
    }

    private List<DomainEvent> domainEvents_JuegoRondaYaIniciado(JuegoId juegoId) {
        List<DomainEvent> eventos =  List.of(
                new JuegoCreado(juegoId),
                new JugadorAgregado(JugadorId.of("xxx-1"), new Name("Daniel"), new Dinero(200)),
                new JugadorAgregado(JugadorId.of("xxx-2"), new Name("Alejandro"), new Dinero(200)),
                new JuegoIniciado()
        );
        return eventos;
    }

}