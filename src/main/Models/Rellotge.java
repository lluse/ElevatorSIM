package main.Models;

import main.vistes.GUI.Simulacio;

import java.util.*;

public class Rellotge {

    private int hora, minuts, segons;

    public Rellotge(int hora, int minuts, int segons) {
        this.hora = hora;
        this.minuts = minuts;
        this.segons = segons;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuts() {
        return minuts;
    }

    public void setMinuts(int minuts) {
        this.minuts = minuts;
    }

    public int getSegons() {
        return segons;
    }

    public void setSegons(int segons) {
        this.segons = segons;
    }

    public String incrementar() {
        segons++;
        if (segons == 60) {
            segons = 0;
            minuts++;
        } if (minuts == 60) {
            minuts = 0;
            hora = (hora+1)%24;
        }
        return toString();
    }

    @Override
    public String toString() {
        String hora = (this.hora < 10) ? "0" + this.hora : String.valueOf(this.hora);
        String minuts = (this.minuts < 10) ? "0" + this.minuts : String.valueOf(this.minuts);
        String segons = (this.segons < 10) ? "0" + this.segons : String.valueOf(this.segons);
        return hora + ":" + minuts + ":" + segons;
    }
}
