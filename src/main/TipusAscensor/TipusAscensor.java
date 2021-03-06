package main.TipusAscensor;

import main.Models.Ascensor;
import main.Models.Direccio;
import main.Models.Passatger;
import main.Models.Rellotge;
import main.vistes.GUI.Simulacio;

import java.util.TreeSet;

public abstract class TipusAscensor {

    protected Ascensor ascensor;
    protected boolean espatllat = false;
    protected TreeSet<Integer> up;
    protected TreeSet<Integer> down;

    public TipusAscensor() {
        up = new TreeSet<>();
        down = new TreeSet<>();
    }

    public TipusAscensor(Ascensor ascensor) {
        this.ascensor = ascensor;
        up = new TreeSet<>();
        down = new TreeSet<>();
    }

    public TreeSet<Integer> getUp() {
        return up;
    }

    public TreeSet<Integer> getDown() {
        return down;
    }

    public Ascensor getAscensor() {
        return ascensor;
    }

    public void setAscensor(Ascensor ascensor) {
        this.ascensor = ascensor;
    }

    public boolean isEspatllat() {
        return espatllat;
    }

    public void setEspatllat(boolean espatllat) {
        this.espatllat = espatllat;
    }

    public abstract Class getType();

    public abstract void act();

    public abstract boolean pujaPassatger(Passatger passatger);

    public abstract void baixaPassatger(Passatger passatger);

    public abstract void activarMoviment();

    public void afegirPlantaDesti(int planta) {
        if (planta > this.ascensor.getPisActual()) up.add(planta);
        else if (planta <= this.ascensor.getPisActual()) down.add(planta);
    }

    protected boolean comprovaHora(Rellotge horaEntrada) {
        if (horaEntrada.getHora() <= Simulacio.rellotgeDinamic.getHora()) {
            if (horaEntrada.getMinuts() <= Simulacio.rellotgeDinamic.getMinuts()) return true;
        }
        return false;
    }

    public synchronized int nextFloor() {
        int direccio;
        if (ascensor.getSentit() == Direccio.PUJA) direccio = up.pollFirst();
        else direccio = down.pollLast();
        return direccio;
    }
}
