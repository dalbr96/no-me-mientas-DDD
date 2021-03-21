package org.example.usecase.juego.handler;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.support.RequestCommand;
import org.example.domain.juego.command.IniciarJuego;
import org.example.domain.juego.values.JuegoId;
import org.example.usecase.juego.IniciarJuegoUseCase;

import java.util.Map;


@CommandHandles
@CommandType(name = "no_me_mientas.juego.iniciar_juego", aggregate = "juego")
public class IniciarJuegoHandler extends UseCaseExecutor {
    private RequestCommand<IniciarJuego> request;


    @Override
    public void run() {
        runUseCase(new IniciarJuegoUseCase(), request);
    }

    @Override
    public void accept(Map<String, String> args) {
        var command = new IniciarJuego(JuegoId.of(aggregateId()));
        request = new RequestCommand<>(command);
    }
}
