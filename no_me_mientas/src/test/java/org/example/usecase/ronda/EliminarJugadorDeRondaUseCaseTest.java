package org.example.usecase.ronda;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.command.EliminarJugador;
import org.example.domain.ronda.events.RondaCreada;
import org.example.domain.ronda.values.RondaId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EliminarJugadorDeRondaUseCaseTest {

    @Mock
    DomainEventRepository repository;

    @Test
    void eliminarJugador(){
        var command = new EliminarJugador(JugadorId.of("xxx"), RondaId.of("ppp"));
        var useCase = new EliminarJugadorDeRondaUseCase();

        when(repository.getEventsBy(command.getRondaId().value())).thenReturn(domainList());
        useCase.addRepository(repository);

        var events = UseCaseHandler.getInstance()
                .setIdentifyExecutor(command.getRondaId().value())
                .syncExecutor(useCase, new RequestCommand<>(command))
                .orElseThrow().getDomainEvents();

        var eventosActualizados = new ArrayList<>(domainList());
        eventosActualizados.add(events.get(0));

        var ronda = Ronda.from(command.getRondaId(), eventosActualizados);

        Assertions.assertEquals(4, ronda.jugadoresRonda().size());
        Assertions.assertFalse(ronda.jugadoresRonda().contains(command.getJugadorId()));
    }

    @Test
    void eliminarJugador_ErrorEsperado(){
        var command = new EliminarJugador(JugadorId.of("lll"), RondaId.of("ppp"));
        var useCase = new EliminarJugadorDeRondaUseCase();

        when(repository.getEventsBy(command.getRondaId().value())).thenReturn(domainList());
        useCase.addRepository(repository);

        Assertions.assertThrows(BusinessException.class, () ->{
            UseCaseHandler.getInstance()
                    .setIdentifyExecutor(command.getRondaId().value())
                    .syncExecutor(useCase, new RequestCommand<>(command))
                    .orElseThrow();
        });
    }

    private List<DomainEvent> domainList() {
        var rondaId = RondaId.of("ppp");
        var juegoId = JuegoId.of("xxx-j");

        var jugadores = List.of(
                JugadorId.of("xxx"),
                JugadorId.of("yyy"),
                JugadorId.of("zzz"),
                JugadorId.of("aaa"),
                JugadorId.of("bbb")
        );

        var capitales = Map.of(
                JugadorId.of("xxx"), new Dinero(400),
                JugadorId.of("yyy"), new Dinero(300),
                JugadorId.of("zzz"), new Dinero(700),
                JugadorId.of("aaa"), new Dinero(900),
                JugadorId.of("bbb"), new Dinero(800)
        );

        return List.of(
                new RondaCreada(rondaId, juegoId, jugadores, capitales)
        );
    }
}