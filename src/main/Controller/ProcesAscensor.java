package main.Controller;

import main.Models.Ascensor;
import main.Models.Edifici;
import main.Models.Passatger;
import main.Models.Rellotge;
import main.vistes.GUI.Simulacio;

import java.util.LinkedList;

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
        while (edifici.getTotalPersonesEsperant() > 0) {
            if (edifici.getNumPersonesEsperantAlPis(ascensor.getPisActual()) > 0) {
                boolean horaArribada = true;
                synchronized (this) {
                    while (horaArribada) {
                        LinkedList<Passatger> passatgers = edifici.getPersonesList();
                        for (int i = 0; i < passatgers.size() && horaArribada; ++i) {
                            if (passatgerEsperantHora(passatgers.get(i).getHoraEntrada())) {
                                passatgers.get(i).cridar();
                            } else horaArribada = false;
                            //edifici.getPersonesList().remove(passatgers.get(i));
                        }
                    }
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
