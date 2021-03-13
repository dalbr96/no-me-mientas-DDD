package org.example.usecase;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import org.example.domain.juego.Juego;
import org.example.domain.juego.command.AddJugadorConCapital;

public class AddJugadorConCapitalUseCase  extends UseCase<RequestCommand<AddJugadorConCapital>, ResponseEvents> {
    @Override
    public void executeUseCase(RequestCommand<AddJugadorConCapital> addJugadorConCapitalRequestCommand) {

        var command = addJugadorConCapitalRequestCommand.getCommand();

        var juegoId = command.getJuegoId();
        var jugadorId = command.getJugadorId();
        var nombre = command.getNombre();
        var capital = command.getCapital();

        var juego = Juego.from(juegoId, retrieveEvents());

        if(juego.jugadores().size() > 23){
            throw new BusinessException(juegoId.value(), "No puede haber más de 24 jugadores");
        }

        juego.addJugadorConCapital(jugadorId, nombre, capital);

        emit().onResponse(new ResponseEvents(juego.getUncommittedChanges()));
    }
}

