package org.example.usecase;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import org.example.domain.juego.Juego;
import org.example.domain.juego.command.IniciarJuego;
import org.example.domain.juego.factory.JugadorFactory;

public class IniciarJuegoUseCase extends UseCase<RequestCommand<IniciarJuego>, ResponseEvents> {
    @Override
    public void executeUseCase(RequestCommand<IniciarJuego> iniciarJuegoRequestCommand) {

        var command = iniciarJuegoRequestCommand.getCommand();

        var juego = Juego.from(command.getJuegoId(), retrieveEvents());

        if(juego.estaIniciado()){
            throw new BusinessException(juego.identity().value(), "El juego ya está iniciado");
        }
        if(juego.jugadores().size() > 23){
            throw new BusinessException(juego.identity().value(), "No se puede empezar el juego, hay más jugadores que los permitidos");
        }

        if (juego.jugadores().size() < 2) {
            throw new BusinessException(juego.identity().value(), "Se necesitan al menos 2 jugadores para comenzar el juego.");
        }

        juego.iniciarJuego();
        var commit = juego.getUncommittedChanges();

        emit().onResponse(new ResponseEvents(commit));

    }
}
