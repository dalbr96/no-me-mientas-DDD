package org.example.usecase.ronda;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.command.EliminarJugador;

public class EliminarJugadorDeRondaUseCase extends UseCase<RequestCommand<EliminarJugador>, ResponseEvents> {
    @Override
    public void executeUseCase(RequestCommand<EliminarJugador> eliminarJugadorRequestCommand) {

        var command = eliminarJugadorRequestCommand.getCommand();
        var jugadorId = command.getJugadorId();
        var rondaId = command.getRondaId();

        Ronda ronda = Ronda.from(rondaId, retrieveEvents());

        if(!ronda.jugadoresRonda().contains(jugadorId)){
            throw new BusinessException(jugadorId.value(),
                    "No se puede eliminar jugador porque no hace parte de la ronda");
        }

        ronda.eliminarJugador(jugadorId);

        var uncommitedChanges = ronda.getUncommittedChanges();

        emit().onResponse(new ResponseEvents(uncommitedChanges));
    }
}
