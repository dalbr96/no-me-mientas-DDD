package org.example.usecase.juego.handler;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.support.RequestCommand;
import org.example.domain.juego.command.AgregarJugador;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.juego.values.Name;
import org.example.usecase.juego.AgregarJugadorUseCase;

import java.util.Map;
import java.util.Objects;

@CommandHandles
@CommandType(name = "no_me_mientas.juego.agregar_jugador", aggregate = "juego")
public class AgregarJugadorHandler extends UseCaseExecutor {

    private RequestCommand<AgregarJugador> request;

    @Override
    public void run() {
        runUseCase(new AgregarJugadorUseCase(), request);
    }

    @Override
    public void accept(Map<String, String> args) {
        var nombre = Objects.requireNonNull(args.get("nombre"));
        var capital = Objects.requireNonNull(args.get("capital"));
        var jugadorId = Objects.requireNonNull(args.get("jugadorId"));

        request = new RequestCommand<>(new AgregarJugador(JuegoId.of(aggregateId()),
                JugadorId.of(jugadorId),
                new Name(nombre),
                new Dinero(Integer.parseInt(capital))));
    }
}
