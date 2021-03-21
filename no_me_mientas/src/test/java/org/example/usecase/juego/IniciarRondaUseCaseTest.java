package org.example.usecase.juego;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import org.example.domain.juego.Juego;
import org.example.domain.juego.command.IniciarRonda;
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
import java.util.Objects;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class IniciarRondaUseCaseTest {

    @Mock
    DomainEventRepository repository;

    @Test
    void iniciarRonda(){

        var juegoId = JuegoId.of("xxx");
        var command = new IniciarRonda(juegoId);

        var iniciarRondaUseCase = new IniciarRondaUseCase();

        when(repository.getEventsBy(juegoId.value())).thenReturn(domainEvents_CasoFeliz(juegoId));
        iniciarRondaUseCase.addRepository(repository);

        var events = UseCaseHandler.getInstance()
                .setIdentifyExecutor(juegoId.value())
                .syncExecutor(iniciarRondaUseCase, new RequestCommand<>(command))
                .orElseThrow().getDomainEvents();

        var eventsUpdated = new ArrayList<>(domainEvents_CasoFeliz(juegoId));
        eventsUpdated.add(events.get(0));

        var juego = Juego.from(juegoId, eventsUpdated);

        Assertions.assertTrue(Objects.nonNull(juego.rondaId()));

    }

    private List<DomainEvent> domainEvents_CasoFeliz(JuegoId juegoId) {
        List<DomainEvent> eventos =  List.of(
                new JuegoCreado(juegoId),
                new JugadorAgregado(JugadorId.of("xxx-1"), new Name("Daniel"), new Dinero(200)),
                new JugadorAgregado(JugadorId.of("xxx-2"), new Name("Alejandro"), new Dinero(200)),
                new JuegoIniciado()
        );
        return eventos;
    }

}