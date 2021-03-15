package org.example.usecase.juego;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import org.example.domain.juego.Juego;
import org.example.domain.juego.command.AgregarJugador;

public class AgregarJugadorUseCase extends UseCase<RequestCommand<AgregarJugador>, ResponseEvents> {

    public static final int MAXIMO_JUGADORES_AÑADIR = 23;

    @Override
    public void executeUseCase(RequestCommand<AgregarJugador> agregarJugadorRequestCommand) {

        var command = agregarJugadorRequestCommand.getCommand();
        var juegoId = command.getJuegoId();

        var juego = Juego.from(juegoId, retrieveEvents());

        if(juego.jugadores().size() > MAXIMO_JUGADORES_AÑADIR){
            throw new BusinessException(juegoId.value(), "No se puede realizar la operación pq ya se llegó al máximo de jugadores.");
        }

        juego.agregarJugador(command.getJugadorId(), command.getNombre(), command.getCapital());

        var commit = juego.getUncommittedChanges();
        emit().onResponse( new ResponseEvents(commit));
    }
}
