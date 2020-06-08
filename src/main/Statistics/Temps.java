package main.Statistics;

import java.util.ArrayList;

public class Temps {
    private ArrayList<Long> tempsEnEspera;
    private ArrayList<Long> tempsEnViatge;

    public Temps() {
        tempsEnEspera = new ArrayList<>();
        tempsEnViatge = new ArrayList<>();
    }

    public void resetTempsEnEspera() {
        tempsEnEspera.clear();
    }

    public void resetTempsEnViatge() {
        tempsEnViatge.clear();
    }

    public void afegirTempsEnEspera(long l) {
        tempsEnEspera.add(l);
    }

    public void afegirTempsEnViatge(long l) {
        tempsEnViatge.add(l);
    }

    public float mitjanaTempsEnEspera() {
        float sum = 0;
        for (long i : tempsEnEspera) {
            sum += i;
        }
        return sum / tempsEnEspera.size();
    }

    public float mitjanaTempsEnViatge() {
        float sum = 0;
        for (long i : tempsEnViatge) {
            sum += i;
        }
        return sum / tempsEnViatge.size();
    }
}
