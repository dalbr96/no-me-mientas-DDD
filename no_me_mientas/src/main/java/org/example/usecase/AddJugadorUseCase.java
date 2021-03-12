package org.example.usecase;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import org.example.domain.juego.Juego;
import org.example.domain.juego.command.AñadirJugador;

public class AñadirJugadorUseCase extends UseCase<RequestCommand<AñadirJugador>, ResponseEvents> {


    @Override
    public void executeUseCase(RequestCommand<AñadirJugador> añadirJugadorRequestCommand) {

        var command = añadirJugadorRequestCommand.getCommand();
        var juegoId = command.getJuegoId();
        var jugador = command.getJugador();

        var juego = Juego.from(juegoId, retrieveEvents());
        juego.añadirJugador(jugador);

        var commits = juego.getUncommittedChanges();

        emit().onResponse(new ResponseEvents(commits));

    }
}
