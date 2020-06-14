package main.Controller;

import main.Models.Edifici;
import main.Models.Passatger;
import main.Models.Rellotge;
import main.vistes.GUI.Simulacio;

import java.util.LinkedList;
import java.util.Map;

public class ProcesAscensor extends Thread {

    private static Edifici edifici;
    LinkedList<Passatger> passatgers;
    private volatile static int index;

    private static int gentEsperant;

    public ProcesAscensor(Edifici edifici, LinkedList<Passatger> passatgers) {
        this.edifici = edifici;
        this.passatgers = passatgers;
    }

    @Override
    public void run() {
        index = 0;
        while (index < edifici.getPersonesList().size() - 1) {
            boolean horaArribada = true;
            int stop = 0;
            boolean cridarAscensor = false;
            while (horaArribada) {
                for (int i = index; i < passatgers.size() && horaArribada; ++i) {
                    Passatger passatger = passatgers.get(i);
                    if (passatger != null && passatgerEsperantHora(passatger.getHoraEntrada())) {
                        //System.out.println("Index passatger: " + index);
                        passatger.setTempsInici();
                        edifici.afegeixPassatgerEsperant(passatger.getPisActual(), passatger);
                        if (!Passatger.isAlreadyCridat()) passatger.cridar();
                        ++index;
                        ++stop;
                        Map<Integer, LinkedList<Passatger>> esperant = edifici.getGentEsperant();
                        if (stop > 30) horaArribada = false;
                    } else horaArribada = false;
                }
            }
        }
        Simulacio.go = false;
        Simulacio.showStats();
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
