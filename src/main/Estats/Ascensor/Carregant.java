package main.Estats.Ascensor;

import main.Controller.ProcesAscensor;
import main.Estats.Ascensor.EstatAscensor;
import main.Models.Ascensor;
import main.Models.Passatger;

public class Carregant implements EstatAscensor {

    private Ascensor ascensor;

    @Override
    public synchronized void carregar() throws InterruptedException {
        int i = 0;
        if (ascensor != null) {
            while (!ascensor.estaPle() &&
                    i < ascensor.getEdifici().getNumPersonesEsperantAlPis(ascensor.getPisActual())) {
                Passatger p = ascensor.getEdifici().getPassatgerEsperantAlPisAmbIndex(ascensor.getPisActual(), i);
                if (p != null) {
                    ascensor.pujaPassatger(p);
                    Thread.sleep(1);
                }
                ++i;
                //ascensor.incrementaTempsAturatTotal(1);
            }
            ascensor.setEstat(new Moviment());
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
