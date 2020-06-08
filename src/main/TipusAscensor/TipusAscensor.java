package main.TipusAscensor;

import main.Models.Ascensor;
import main.Models.Passatger;
import main.Models.Rellotge;
import main.vistes.GUI.Simulacio;

public abstract class TipusAscensor {

    protected Ascensor ascensor;
    protected boolean espatllat = false;

    public TipusAscensor() {

    }

    public TipusAscensor(Ascensor ascensor) {
        this.ascensor = ascensor;
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

    protected boolean comprovaHora(Rellotge horaEntrada) {
        if (horaEntrada.getHora() <= Simulacio.rellotgeDinamic.getHora()) {
            if (horaEntrada.getMinuts() <= Simulacio.rellotgeDinamic.getMinuts()) return true;
        }
        return false;
    }
}
