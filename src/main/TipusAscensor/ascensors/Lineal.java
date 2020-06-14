package main.TipusAscensor.ascensors;

import main.Models.Ascensor;
import main.Models.Direccio;
import main.Models.Passatger;
import main.TipusAscensor.TipusAscensor;

import java.util.*;

public class Lineal extends TipusAscensor {

    public Lineal() {
        super();
    }

    public Lineal(Ascensor ascensor) {
        super(ascensor);
    }

    @Override
    public Class getType() {
        return TipusAscensor.class;
    }

    @Override
    public void act() {
        if (!ascensor.getEdifici().totsElsPassatgersHanArribat()
                && (ascensor.getEdifici().getNumPersonesEsperantAlPis(ascensor.getPisActual()) > 0
                || ascensor.getNumPassatgersAscensor() > 0)) {

            ascensor.descarregar();
            if (ascensor.pisConteGentEsperant(ascensor.getPisActual())) {
                ascensor.carregar();
            }

            /*
            if (!ascensor.estaPle()) {
                int i = 0;
                while (!ascensor.estaPle() &&
                        i < ascensor.getEdifici().getNumPersonesEsperantAlPis(ascensor.getPisActual()) &&
                        comprovaHora(ascensor.getEdifici()
                                .getPassatgerEsperantAlPisAmbIndex(ascensor.getPisActual(), i).getHoraEntrada())) {
                    Passatger p = ascensor.getEdifici().getPassatgerEsperantAlPisAmbIndex(ascensor.getPisActual(), i);
                    if (p != null) { ascensor.pujaPassatger(p); }
                    ++i;
                    ascensor.incrementaTempsAturatTotal(1);
                }
            }

            if ((ascensor.dalt() && ascensor.Puja()) || (ascensor.baix() && !ascensor.Puja()) ||
                    (ascensor.noExisteixTrucadaPelCami())) {
                ascensor.canviaDireccio();
            }
             */

            //if (ascensor.getTempsAturatTotal() >= ascensor.getTempsParada()) ascensor.activarMoviment();
        } else {
            this.ascensor.setEsperant(true);
        }
    }

    private void afegirPis(int pisDesitjat) {
        if (pisDesitjat > ascensor.getPisActual()) up.add(pisDesitjat);
        else if (pisDesitjat < ascensor.getPisActual()) down.add(pisDesitjat);

        //else pisDesitjat == pisActual aixi que no l'afegim enlloc
    }

    @Override
    public boolean pujaPassatger(Passatger passatger) {
        ascensor.incrementaTempsParada(5);
        ascensor.incrementaTempsAturatTotal(5);
        return true;
    }

    @Override
    public void baixaPassatger(Passatger passatger) {
        System.out.println("Passatger baixa al pis " + passatger.getPisDesitjat());

    }

    @Override
    public void activarMoviment() {
        int pisSeguent = nextFloor();
        ascensor.setPisActual(pisSeguent);
    }

}
