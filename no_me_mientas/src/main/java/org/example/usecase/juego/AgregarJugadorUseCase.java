package org.example.usecase.juego;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import org.example.domain.juego.Juego;
import org.example.domain.juego.command.AgregarJugador;

public class AgregarJugadorUseCase extends UseCase<RequestCommand<AgregarJugador>, ResponseEvents> {
    @Override
    public void executeUseCase(RequestCommand<AgregarJugador> agregarJugadorRequestCommand) {

        var command = agregarJugadorRequestCommand.getCommand();
        var juegoId = command.getJuegoId();

        var juego = Juego.from(juegoId, retrieveEvents());

        if(juego.jugadores().size() > 23){
            throw new BusinessException(juegoId.value(), "No se puede realizar la operación pq ya se llegó al máximo de jugadores.");
        }

        juego.agregarJugador(command.getJugadorId(), command.getNombre(), command.getCapital());

        var commit = juego.getUncommittedChanges();
        emit().onResponse( new ResponseEvents(commit));
    }
}
