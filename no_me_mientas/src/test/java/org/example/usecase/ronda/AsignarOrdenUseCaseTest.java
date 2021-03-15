package org.example.usecase.ronda;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.events.DadoLanzado;
import org.example.domain.ronda.events.EtapaCreada;
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
class AsignarOrdenUseCaseTest {

    @Mock
    DomainEventRepository repository;

    @Test
    void asignarTurnos(){

        var rondaId = RondaId.of("xxxx");

        var jugadores = Map.of(
                JugadorId.of("xxx-1"), new Dinero(400),
                JugadorId.of("xxx-2"), new Dinero(300),
                JugadorId.of("xxx-3"), new Dinero(700),
                JugadorId.of("xxx-4"), new Dinero(900),
                JugadorId.of("xxx-5"), new Dinero(800)
        );

        var event = new EtapaCreada(jugadores);
        event.setAggregateRootId(rondaId.value());
        var useCase = new AsignarOrdenUseCase();

        when(repository.getEventsBy(rondaId.value())).thenReturn(domainEvents());
        useCase.addRepository(repository);

        var events = UseCaseHandler.getInstance().setIdentifyExecutor(event.aggregateRootId())
                .syncExecutor(useCase, new TriggeredEvent<>(event)).orElseThrow().getDomainEvents();

        var eventosActualizados = new ArrayList<>(domainEvents());
        eventosActualizados.add(events.get(0));

        var ronda = Ronda.from(rondaId, eventosActualizados);

        Assertions.assertEquals(5, ronda.etapas().iterator().next().orden().size());

    }

        @Test
        void asignarTurnos_CuatroJugadores(){

            var rondaId = RondaId.of("xxxx");

            var jugadores = Map.of(
                    JugadorId.of("xxx-1"), new Dinero(400),
                    JugadorId.of("xxx-2"), new Dinero(300),
                    JugadorId.of("xxx-3"), new Dinero(700),
                    JugadorId.of("xxx-4"), new Dinero(900)
            );

            var event = new EtapaCreada(jugadores);
            event.setAggregateRootId(rondaId.value());
            var useCase = new AsignarOrdenUseCase();

            when(repository.getEventsBy(rondaId.value())).thenReturn(domainEvents_MenosJugadores());
            useCase.addRepository(repository);

            var events = UseCaseHandler.getInstance().setIdentifyExecutor(event.aggregateRootId())
                    .syncExecutor(useCase, new TriggeredEvent<>(event)).orElseThrow().getDomainEvents();

            var eventosActualizados = new ArrayList<>(domainEvents_MenosJugadores());
            eventosActualizados.add(events.get(0));

            var ronda = Ronda.from(rondaId, eventosActualizados);

            Assertions.assertEquals(4, ronda.etapas().iterator().next().orden().size());

        }

    private List<DomainEvent> domainEvents(){
        var rondaId = RondaId.of("xxxx");
        var juegoId = JuegoId.of("xxx-j");

        var jugadores = List.of(
                JugadorId.of("xxx-1"),
                JugadorId.of("xxx-2"),
                JugadorId.of("xxx-3"),
                JugadorId.of("xxx-4"),
                JugadorId.of("xxx-5")
        );

        var capitales = Map.of(
                JugadorId.of("xxx-1"), new Dinero(400),
                JugadorId.of("xxx-2"), new Dinero(300),
                JugadorId.of("xxx-3"), new Dinero(700),
                JugadorId.of("xxx-4"), new Dinero(900),
                JugadorId.of("xxx-5"), new Dinero(800)
        );

        return List.of(
                new RondaCreada(rondaId, juegoId, jugadores, capitales),
                new DadoLanzado(),
                new EtapaCreada(capitales)
        );
    }

    private List<DomainEvent> domainEvents_MenosJugadores(){
        var capitales = Map.of(
                JugadorId.of("xxx-1"), new Dinero(400),
                JugadorId.of("xxx-2"), new Dinero(300),
                JugadorId.of("xxx-3"), new Dinero(700),
                JugadorId.of("xxx-4"), new Dinero(900)
        );

        var listaEventos = new ArrayList<>(domainEvents());

        listaEventos.add(new EtapaCreada(capitales));
        return listaEventos;
    }

}