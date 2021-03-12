package org.example.usecase;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import org.example.domain.juego.Juego;
import org.example.domain.juego.command.CrearJuego;
import org.example.domain.juego.values.JuegoId;

public class CrearJuegoUseCase extends UseCase<RequestCommand<CrearJuego>, ResponseEvents> {

    @Override
    public void executeUseCase(RequestCommand<CrearJuego> crearJuegoRequestCommand) {

        var command = crearJuegoRequestCommand.getCommand();
        var juegoId = new JuegoId();
        var juego = new Juego(juegoId, command.getJugadores());

        var commits = juego.getUncommittedChanges();

        emit().onResponse(new ResponseEvents(commits));
    }
}
