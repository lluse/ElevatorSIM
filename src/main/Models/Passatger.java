package main.Models;

import main.Controller.MainController;
import main.ObservadorAscensor.Observador;
import main.ObservadorAscensor.SubjecteObservable;
import main.TipusAscensor.ascensors.Imparell;
import main.TipusAscensor.ascensors.Lineal;
import main.TipusAscensor.ascensors.Parell;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Passatger implements SubjecteObservable {

    private volatile CopyOnWriteArrayList<Observador> observador;

    protected MainController controller;

    protected long tempsEspera;
    protected long tempsViatge;
    protected long tempsInici;

    protected Ascensor ascensor;

    protected int pisActual;
    protected int pisDesitjat;

    private Rellotge horaEntrada;

    private static volatile boolean cridats;


    public Passatger(int pisActual, int pisDesitjat, Rellotge horaEntrada) {
        observador = new CopyOnWriteArrayList<>();
        controller = MainController.getInstance();

        this.pisActual = pisActual;
        this.pisDesitjat = pisDesitjat;
        this.horaEntrada = horaEntrada;

        ascensor = null;
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

    public void setTempsEspera() {
        this.tempsEspera = getTime();
        resetTempsInici();
    }

    public void setTempsViatge() {
        this.tempsViatge = getTime();
        resetTime();
    }

    public void setTempsInici(long tempsInici) {
        this.tempsInici = System.currentTimeMillis();
    }

    public void afegirTempsEnViatge(long temps) {
        tempsViatge += temps;
    }

    public void resetTempsInici() {
        tempsInici = System.currentTimeMillis();
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

    public void cridarAscensor() {
        cridar();
    }

    public void enllasarObservador(Observador o) {
        if (o == null) return;
        observador.add(o);
    }

    public static boolean isAlreadyCridat() {
        return cridats;
    }

    @Override
    public void cridar() {
        cridats = true;
        for (Observador o : observador) {
            if (pisActual == o.getPis() && o.estaEsperant() && comprovaPisos(o, pisActual)) {
                resetTime();
                o.cridat(pisActual);
            }
            else if (!o.haEstatCridat(pisActual) && comprovaPisos(o, pisActual)) {
                if (o.estaEsperant()) {
                    //System.out.println("El thread " + o + " esta esperant");
                }
                //System.out.println("Es crida a l'ascensor " + o );
                o.cridat(pisActual);
            }
        }
        cridats = false;
    }

    private boolean comprovaPisos(Observador o, int pisActual) {
        if (o.getTipus() instanceof Lineal) return true;
        else if ((o.getTipus() instanceof Imparell && pisActual % 2 != 0) || (pisActual == 0)) return true;
        else if (o.getTipus() instanceof Parell && pisActual % 2 == 0) return true;
        else return false;
    }
}
