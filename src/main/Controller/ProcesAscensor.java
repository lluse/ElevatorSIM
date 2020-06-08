package main.Controller;

import main.Models.Ascensor;
import main.Models.Edifici;
import main.Models.Rellotge;
import main.vistes.GUI.Simulacio;

public class ProcesAscensor extends Thread {

    Edifici edifici;
    Ascensor ascensor;
    String nomThread;

    public ProcesAscensor(String nomThread, Edifici edifici, Ascensor ascensor) {
        this.edifici = edifici;
        this.ascensor = ascensor;
        this.nomThread = nomThread;
    }

    @Override
    public void run() {
        if (edifici.getTotalPersonesEsperant() > 0) {
            if (edifici.getNumPersonesEsperantAlPis(ascensor.getPisActual()) > 0) {
                if (passatgerEsperantHora(edifici.getPersonesList().get(0).getHoraEntrada())) {
                    synchronized (this) {
                        ascensor.acts();
                    }
                }
            }
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.run();
    }

    public boolean passatgerEsperantHora(Rellotge rellotge) {
        if (rellotge.getHora() <= Simulacio.rellotgeDinamic.getHora()) {
            if (rellotge.getMinuts() <= Simulacio.rellotgeDinamic.getMinuts()) return true;
        }
        return false;
    }
}
