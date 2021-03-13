package org.example.usecase;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import org.example.domain.juego.events.JuegoIniciado;
import org.example.domain.juego.events.RondaIniciada;
import org.example.domain.juego.factory.JugadorFactory;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.command.CrearRonda;
import org.example.domain.ronda.values.RondaId;

public class CrearRondaUseCase extends UseCase<TriggeredEvent<RondaIniciada>, ResponseEvents> {

    @Override
    public void executeUseCase(TriggeredEvent<RondaIniciada> rondaIniciadaTriggeredEvent) {
        var event = rondaIniciadaTriggeredEvent.getDomainEvent();
        var rondaId = event.getRondaId();
        var juegoId = JuegoId.of(event.aggregateRootId());

        var factory = JugadorFactory.builder();
        event.getNombres().forEach(
                (jugadorId, nombre) -> factory.nuevoJugador(jugadorId,
                        nombre,
                        event.getCapitales().get(jugadorId))
        );

        if(event.getJugadoresIds().size() < 2){
            throw new BusinessException(rondaId.value(), "No se puede iniciar ronda por falta de jugadores");
        }

        var ronda = new Ronda(rondaId, juegoId, factory);


        var commits = ronda.getUncommittedChanges();

        emit().onResponse(new ResponseEvents(commits));
    }
}
