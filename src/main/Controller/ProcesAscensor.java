package main.Controller;

import main.Models.Ascensor;
import main.Models.Edifici;
import main.Models.Passatger;
import main.Models.Rellotge;
import main.vistes.GUI.Simulacio;

import java.util.LinkedList;

public class ProcesAscensor extends Thread {

    Edifici edifici;
    LinkedList<Passatger> passatgers;
    private volatile static int index;

    public ProcesAscensor(Edifici edifici, LinkedList<Passatger> passatgers) {
        this.edifici = edifici;
        this.passatgers = passatgers;
    }

    @Override
    public void run() {
        index = 0;
        while (edifici.getTotalPersonesEsperant() > 0) {
            boolean horaArribada = true;
            while (horaArribada) {
                for (int i = index; i < passatgers.size() && horaArribada; ++i) {
                    Passatger passatger = passatgers.get(i);
                    if (passatgerEsperantHora(passatger.getHoraEntrada())) {
                        edifici.afegeixPassatgerEsperant(passatger.getPisActual(), passatger);
                        passatger.cridar();
                        ++index;
                    } else horaArribada = false;
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
        //System.out.println("Ascensor " + ascensor.getId() + " Rellotge entrada passatgers: "
        //        + rellotge.getHora() + ":" + rellotge.getMinuts());
        //System.out.println("Ascensor " + ascensor.getId() + " Rellotge del simulador: "
        //        + Simulacio.rellotgeDinamic.getHora() + ":" + Simulacio.rellotgeDinamic.getMinuts());
        //if (rellotge.getHora() < Simulacio.rellotgeDinamic.getHora()) System.out.println("Es mes petit");

        if (rellotge.getHora() < Simulacio.rellotgeDinamic.getHora()) {
            return true;
        }
        else if (rellotge.getHora() == Simulacio.rellotgeDinamic.getHora()) {
            if (rellotge.getMinuts() <= Simulacio.rellotgeDinamic.getMinuts()) {
                //System.out.println("Es mes petit");
                return true;
            }
        }
        return false;
    }

    public static int getIndex() {
        return index;
    }
}
