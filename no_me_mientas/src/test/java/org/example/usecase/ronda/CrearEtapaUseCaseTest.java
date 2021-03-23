package org.example.usecase.ronda;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.DineroJugadores;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.Etapa;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.command.CrearEtapa;
import org.example.domain.ronda.events.EtapaCreada;
import org.example.domain.ronda.events.RondaCreada;
import org.example.domain.ronda.values.RondaId;
import org.example.usecase.ronda.CrearEtapaUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrearEtapaUseCaseTest {

    @Mock
    DomainEventRepository repository;

    @Test
    void crearEtapa(){
        var rondaId = RondaId.of("xxxx");
        var command = new CrearEtapa(rondaId);
        var useCase = new CrearEtapaUseCase();

        when(repository.getEventsBy(rondaId.value())).thenReturn(domainEvents());
        useCase.addRepository(repository);

        var events = UseCaseHandler.getInstance().setIdentifyExecutor(rondaId.value())
                .syncExecutor(useCase, new RequestCommand<>(command)).orElseThrow().getDomainEvents();

        List<DomainEvent> eventosRondaReconstruida = new ArrayList<DomainEvent>(domainEvents());

        eventosRondaReconstruida.add(events.get(0));

        var rondaReconstruida = Ronda.from(rondaId, eventosRondaReconstruida);
        var etapa = rondaReconstruida.etapas().stream().filter(Etapa::esActual).findFirst().get();

        Assertions.assertEquals(1, rondaReconstruida.etapas().size());
        Assertions.assertEquals(400, etapa.apuestaMaxima().value());
        Assertions.assertEquals(Boolean.TRUE, etapa.esActual());
    }

    @Test
    void crearSegundaEtapa(){
        var rondaId = RondaId.of("xxxx");
        var command = new CrearEtapa(rondaId);
        var useCase = new CrearEtapaUseCase();

        when(repository.getEventsBy(rondaId.value())).thenReturn(domainEvents_segundaEtapa());
        useCase.addRepository(repository);

        var events = UseCaseHandler.getInstance().setIdentifyExecutor(rondaId.value())
                .syncExecutor(useCase, new RequestCommand<>(command)).orElseThrow().getDomainEvents();

        List<DomainEvent> eventosRondaReconstruida = new ArrayList<DomainEvent>(domainEvents_segundaEtapa());

        eventosRondaReconstruida.add(events.get(0));

        var rondaReconstruida = Ronda.from(rondaId, eventosRondaReconstruida);
        var etapa = rondaReconstruida.etapas().stream().filter(Etapa::esActual).findFirst().get();


        Assertions.assertEquals(2, rondaReconstruida.etapas().size());
        Assertions.assertEquals(400, etapa.apuestaMaxima().value());
        Assertions.assertEquals(Boolean.TRUE, etapa.esActual());
    }

    @Test
    void crearTerceraEtapa(){
        var rondaId = RondaId.of("xxxx");
        var command = new CrearEtapa(rondaId);
        var useCase = new CrearEtapaUseCase();

        when(repository.getEventsBy(rondaId.value())).thenReturn(domainEvents_terceraEtapa());
        useCase.addRepository(repository);

        var events = UseCaseHandler.getInstance().setIdentifyExecutor(rondaId.value())
                .syncExecutor(useCase, new RequestCommand<>(command)).orElseThrow().getDomainEvents();

        List<DomainEvent> eventosRondaReconstruida = new ArrayList<DomainEvent>(domainEvents_terceraEtapa());

        eventosRondaReconstruida.add(events.get(0));

        var rondaReconstruida = Ronda.from(rondaId, eventosRondaReconstruida);
        var etapa =         rondaReconstruida.etapas().stream().filter(Etapa::esActual).findFirst().get();


        Assertions.assertEquals(3, rondaReconstruida.etapas().size());
        Assertions.assertEquals(400, etapa.apuestaMaxima().value());
        Assertions.assertEquals(Boolean.TRUE, etapa.esActual());
    }

    @Test
    void crearEtapa_ErrorEsperadoTresEtapas(){
        var rondaId = RondaId.of("xxxx");
        var command = new CrearEtapa(rondaId);
        var useCase = new CrearEtapaUseCase();

        when(repository.getEventsBy(rondaId.value())).thenReturn(domainEvents_ErrorEsperado());
        useCase.addRepository(repository);

        Assertions.assertThrows(BusinessException.class, ()->{
            UseCaseHandler.getInstance().setIdentifyExecutor(rondaId.value())
                    .syncExecutor(useCase, new RequestCommand<>(command)).orElseThrow().getDomainEvents();
        }, "El Máximo numero de etapas es 3");



    }

    @Test
    void crearEtapa_ErrorEsperadoJugadores(){
        var rondaId = RondaId.of("xxxx");
        var command = new CrearEtapa(rondaId);
        var useCase = new CrearEtapaUseCase();

        when(repository.getEventsBy(rondaId.value())).thenReturn(domainEvents_FaltaJugadores());
        useCase.addRepository(repository);

        Assertions.assertThrows(BusinessException.class, ()->{
            UseCaseHandler.getInstance().setIdentifyExecutor(rondaId.value())
                    .syncExecutor(useCase, new RequestCommand<>(command)).orElseThrow().getDomainEvents();
        }, "No se puede crear una Etapa con solo un jugador");



    }

    private List<DomainEvent> domainEvents() {

        var rondaId = RondaId.of("xxx");
        var juegoId = JuegoId.of("xxx-j");
        var jugadorEliminado = new DineroJugadores(new Dinero(200), JugadorId.of("eliminado"));
        jugadorEliminado = jugadorEliminado.eliminarJugador(JugadorId.of("eliminado"));

        var jugadores = List.of(
                new DineroJugadores(new Dinero(400), JugadorId.of("xxx-1")),
                new DineroJugadores(new Dinero(300), JugadorId.of("xxx-2")),
                jugadorEliminado
        );

        return List.of(
                new RondaCreada(rondaId, juegoId, jugadores)
        );
    }

    private List<DomainEvent> domainEvents_segundaEtapa(){
        return List.of(
                domainEvents().get(0),
                new EtapaCreada(500, List.of(JugadorId.of("xxx-1"),JugadorId.of("xxx-2")))
        );
    }

    private List<DomainEvent> domainEvents_terceraEtapa(){
        var domainEvents = new ArrayList<>(domainEvents_segundaEtapa());
        domainEvents.add(
                new EtapaCreada(200, List.of(JugadorId.of("xxx-1"),JugadorId.of("xxx-2")))
        );
        return domainEvents;
    }

    private List<DomainEvent> domainEvents_ErrorEsperado() {

        return List.of(
                domainEvents().get(0),
                new EtapaCreada(400, List.of(JugadorId.of("xxx-1"),JugadorId.of("xxx-2"))),
                new EtapaCreada(400, List.of(JugadorId.of("xxx-1"),JugadorId.of("xxx-2"))),
                new EtapaCreada(400, List.of(JugadorId.of("xxx-1"),JugadorId.of("xxx-2")))
                );
    }

    private List<DomainEvent> domainEvents_FaltaJugadores() {

        var rondaId = RondaId.of("xxx");
        var juegoId = JuegoId.of("xxx-j");

        var jugadores = List.of(
                new DineroJugadores(new Dinero(400), JugadorId.of("xxx-1"))
        );

        return List.of(
                new RondaCreada(rondaId, juegoId, jugadores)
        );
    }
}