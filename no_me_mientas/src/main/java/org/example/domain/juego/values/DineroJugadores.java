package org.example.domain.juego.values;

import co.com.sofka.domain.generic.ValueObject;

public class DineroJugadores implements ValueObject<DineroJugadores.Values> {

    private final Boolean jugando;
    private final Dinero capital;
    private final JugadorId jugadorId;


    private DineroJugadores(Boolean jugando, Dinero capital, JugadorId jugadorId) {
        this.jugando = jugando;
        this.capital = capital;
        this.jugadorId = jugadorId;
    }

    public DineroJugadores(Dinero capital, JugadorId jugadorId) {
        this.jugando = Boolean.TRUE;
        this.capital = capital;
        this.jugadorId = jugadorId;
    }

    public DineroJugadores eliminarJugador(JugadorId jugadorId){
        return new DineroJugadores(Boolean.FALSE, this.capital, this.jugadorId);
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
