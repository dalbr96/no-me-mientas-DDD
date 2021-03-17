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

        if(ronda.etapas().iterator().next().apuestaMaxima().value() < apuesta.value().dineroApostado().value()){
            throw new BusinessException(rondaId.value(), "El dinero apostado sobrepasa la apuesta maxima.");
        }

        if(ronda.capitales().get(jugadorId).value() < apuesta.value().dineroApostado().value()){
            throw new BusinessException(rondaId.value(), "El jugador no cuenta con fondos suficientes");
        }

        ronda.etapas().iterator().next().turnos().entrySet().forEach(entry -> {
            if(entry.getValue().value().adivinanzaRealizada().equals(apuesta.value().adivinanzaRealizada())){
                throw new BusinessException(rondaId.value(),
                        "No se puede asignar la apuesta porque alguien ya escogió esa adivinanza");
            }
        });


        ronda.asignarApuesta(jugadorId, apuesta);

        var uncommitedEvents = ronda.getUncommittedChanges();

        emit().onResponse(new ResponseEvents(uncommitedEvents));
    }
}
