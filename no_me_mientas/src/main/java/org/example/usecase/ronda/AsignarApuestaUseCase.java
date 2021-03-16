package org.example.usecase.ronda;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.command.AsignarApuesta;

public class AsignarApuestaUseCase extends UseCase<RequestCommand<AsignarApuesta>, ResponseEvents> {
    @Override
    public void executeUseCase(RequestCommand<AsignarApuesta> asignarApuestaRequestCommand) {

        var command = asignarApuestaRequestCommand.getCommand();
        var rondaId = command.getRondaId();
        var jugadorId = command.getJugadorId();
        var apuesta = command.getApuesta();

        Ronda ronda = Ronda.from(rondaId, retrieveEvents());

        if(!ronda.etapas().iterator().next().orden().contains(jugadorId)){
            throw new BusinessException(rondaId.value(), "El Jugador no hace parte de la etapa");
        }

        ronda.asignarApuesta(jugadorId, apuesta);

        var uncommitedEvents = ronda.getUncommittedChanges();

        emit().onResponse(new ResponseEvents(uncommitedEvents));
    }
}
