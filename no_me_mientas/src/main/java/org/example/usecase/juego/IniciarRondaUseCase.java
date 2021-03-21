package org.example.usecase.juego;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import org.example.domain.juego.Juego;
import org.example.domain.juego.command.IniciarRonda;


public class IniciarRondaUseCase extends UseCase<RequestCommand<IniciarRonda>, ResponseEvents> {


    @Override
    public void executeUseCase(RequestCommand<IniciarRonda> iniciarRondaRequestCommand) {
        var command = iniciarRondaRequestCommand.getCommand();

        var juego = Juego.from(command.getJuegoId(), retrieveEvents());

        juego.iniciarRonda();

        var uncommittedEvents = juego.getUncommittedChanges();

        emit().onResponse(new ResponseEvents(uncommittedEvents));
    }
}
