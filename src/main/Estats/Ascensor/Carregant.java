package main.Estats.Ascensor;

import main.Controller.ProcesAscensor;
import main.Estats.Ascensor.EstatAscensor;
import main.Models.Ascensor;
import main.Models.Passatger;
import main.TipusAscensor.ascensors.Imparell;
import main.TipusAscensor.ascensors.Lineal;
import main.TipusAscensor.ascensors.Parell;

public class Carregant implements EstatAscensor {

    private Ascensor ascensor;

    @Override
    public synchronized void carregar() throws InterruptedException {
        int i = 0;
        if (ascensor != null) {
            while (!ascensor.estaPle() &&
                    i < ascensor.getEdifici().getNumPersonesEsperantAlPis(ascensor.getPisActual())) {
                if ((ascensor.getTipus() instanceof Parell && ascensor.getPisActual() % 2 == 0) ||
                        (ascensor.getTipus() instanceof Imparell && (ascensor.getPisActual() % 2 != 0
                        || ascensor.getPisActual() == 0)) ||
                        (ascensor.getTipus() instanceof Lineal)) {
                    Passatger p = ascensor.getEdifici().getPassatgerEsperantAlPisAmbIndex(ascensor.getPisActual(), i);
                    if (p != null) {
                        ascensor.pujaPassatger(p);
                        Thread.sleep(1);
                    }
                }
                ++i;
            }
            if (ascensor.getPassatgers().size() > 0) ascensor.setEstat(new Moviment());
            else ascensor.setEstat(new Lliure());
        }
    }

    @Override
    public void descarregar() {

    }

    @Override
    public void desplasar(int planta) {

    }

    @Override
    public void setAscensor(Ascensor ascensor) {
        this.ascensor = ascensor;
    }
}
