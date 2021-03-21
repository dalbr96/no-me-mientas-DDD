package org.example.domain.juego.command;

import co.com.sofka.domain.generic.Command;
import org.example.domain.juego.values.JuegoId;

public class IniciarRonda implements Command {
    private final JuegoId juegoId;

    public IniciarRonda(JuegoId juegoId){
        this.juegoId = juegoId;
    }

    public JuegoId getJuegoId() {
        return juegoId;
    }
}
