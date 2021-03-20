package org.example.domain.juego.command;

import co.com.sofka.domain.generic.Command;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.juego.values.Name;

import java.util.Map;

public class CrearJuego implements Command {

    private final Map<JugadorId, Name> nombres;
    private final Map <JugadorId, Dinero> capitales;
    private final JuegoId juegoId;

    public CrearJuego(Map<JugadorId, Name> nombres, Map<JugadorId, Dinero> capitales, JuegoId juegoId) {
        this.nombres = nombres;
        this.capitales = capitales;
        this.juegoId = juegoId;

    }

    public Map<JugadorId, Name> getNombres() {
        return nombres;
    }

    public Map<JugadorId, Dinero> getCapitales() {
        return capitales;
    }

    public JuegoId getJuegoId() {
        return juegoId;
    }
}
