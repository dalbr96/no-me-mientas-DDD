package org.example.domain.juego;

import co.com.sofka.domain.generic.Entity;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.juego.values.Name;

public class Jugador extends Entity<JugadorId>{

    private final Name name;
    private Dinero capital;


    public Jugador(JugadorId entityId, Name name) {
        super(entityId);
        this.name = name;
        this.capital = new Dinero(0);
    }

    public Jugador(JugadorId entityId, Name name, Dinero capital){
        super(entityId);
        this.name = name;
        this.capital = capital;
    }

    public Dinero capital(){
        return capital;
    }

    public Name nombre(){
        return name;
    }

    public void aumentarCapital(Integer capital){
        this.capital = this.capital.aumentar(capital);
    }

    public void reducirCapital(Integer capital){
        this.capital = this.capital.reducir(capital);
    }

}
