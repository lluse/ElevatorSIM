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

    public void incrementar() {
        segons++;
        if (segons == 60) {
            segons = 0;
            minuts++;
        } if (minuts == 60) {
            minuts = 0;
            hora = (hora+1)%24;
        }
    }

    @Override
    public String toString() {
        return hora + ":" + minuts + ":" + segons;
    }
}
