package org.example.domain.ronda.values;

import co.com.sofka.domain.generic.ValueObject;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JugadorId;

public class CapitalesJugadoresRonda implements ValueObject<CapitalesJugadoresRonda.Values> {

    private final Boolean jugando;
    private final Dinero capital;
    private final JugadorId jugadorId;


    private CapitalesJugadoresRonda(Boolean jugando, Dinero capital, JugadorId jugadorId) {
        this.jugando = jugando;
        this.capital = capital;
        this.jugadorId = jugadorId;
    }

    public CapitalesJugadoresRonda(Dinero capital, JugadorId jugadorId) {
        this.jugando = Boolean.TRUE;
        this.capital = capital;
        this.jugadorId = jugadorId;
    }

    public CapitalesJugadoresRonda eliminarJugador(JugadorId jugadorId){
        return new CapitalesJugadoresRonda(Boolean.FALSE, this.capital, this.jugadorId);
    }

    @Override
    public Values value() {
        return new Values(){

            @Override
            public Boolean jugando() {
                return jugando;
            }

            @Override
            public Dinero capital() {
                return capital;
            }

            @Override
            public JugadorId jugadorId() {
                return jugadorId;
            }
        };
    }


    public interface Values{
        Boolean jugando();
        Dinero capital();
        JugadorId jugadorId();
    }
}
