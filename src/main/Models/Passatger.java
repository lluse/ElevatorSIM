package main.Models;

import main.Controller.MainController;

public class Passatger {

    protected MainController controller;

    protected long tempsEspera;
    protected long tempsViatge;
    protected long tempsInici;

    protected Ascensor ascensor;
    protected int pisActual;
    protected int pisDesitjat;
    private Rellotge horaEntrada;


    public Passatger(int pisActual, int pisDesitjat, Rellotge horaEntrada) {
        controller = MainController.getInstance();
        this.pisActual = pisActual;
        this.pisDesitjat = pisDesitjat;
        this.horaEntrada = horaEntrada;

        tempsEspera = 0;
        tempsViatge = 0;

        ascensor = null;
        resetTime();
    }

    public boolean potPujarAlAscensor(Ascensor ascensor) {
        return true;
    }

    public Ascensor getAscensor() {
        return ascensor;
    }

    public void setAscensor(Ascensor ascensor) {
        this.ascensor = ascensor;
    }

    public int getPisActual() {
        return pisActual;
    }

    public void setPisActual(int pisActual) {
        this.pisActual = pisActual;
    }

    public int getPisDesitjat() {
        return pisDesitjat;
    }

    public long getTime() {
        return ((System.currentTimeMillis() - tempsInici) / 1000);
    }

    public long getTempsEspera() {
        return tempsEspera/1000;
    }

    public long getTempsViatge() {
        return tempsViatge/1000;
    }

    public void resetTime() {
        tempsEspera = 0;
        tempsViatge = 0;
        tempsInici = System.currentTimeMillis();
    }

    public Rellotge getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Rellotge horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public boolean haArribat() {
        return (pisDesitjat == pisActual) && !estaAlAscensor();
    }

    public boolean estaAlAscensor() {
        return ascensor != null;
    }

    public boolean estaEsperantAlPis(int pis) {
        return !haArribat() && !estaAlAscensor() && (pisActual == pis);
    }

    public boolean haArribatAlPis(int pis) {
        return !haArribat() && (pisActual == pis);
    }

    /*
    1: content/satisfet, 2: una mica enfada, 3: bastant enfadat
     */
    public int satisfaccioPassatger() {
        if (haArribat()) return 1;
        if (getTime() > 240) {
            return 3;
        }
        else if (getTime() > 60) return 2;
        else return 1;
    }
}
