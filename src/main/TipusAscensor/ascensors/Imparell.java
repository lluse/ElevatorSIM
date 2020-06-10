package main.TipusAscensor.ascensors;

import main.Models.Passatger;
import main.TipusAscensor.TipusAscensor;

public class Imparell extends TipusAscensor {
    @Override
    public Class getType() {
        return TipusAscensor.class;
    }

    @Override
    public void act() {
        if (!ascensor.getEdifici().totsElsPassatgersHanArribat()
                && (ascensor.getEdifici().getTotalPersonesEsperant() != 0 || ascensor.getNumPassatgersAscensor() != 0)) {

            ascensor.baixaPassatgersQueHanArribat();

            if (!ascensor.estaPle()) {
                int i = 0;
                while (!ascensor.estaPle() &&
                        i < ascensor.getEdifici().getNumPersonesEsperantAlPis(ascensor.getPisActual()) &&
                        comprovaHora(ascensor.getEdifici()
                                .getPassatgerEsperantAlPisAmbIndex(ascensor.getPisActual(), i).getHoraEntrada())) {
                    if (ascensor.getPassatgers().get(i).getPisDesitjat() % 2 != 0
                            || ascensor.getPassatgers().get(i).getPisDesitjat() == 0) { //Comprovem que va a un pis imparell
                        Passatger p = ascensor.getEdifici().getPassatgerEsperantAlPisAmbIndex(ascensor.getPisActual(), i);
                        if (p != null) {
                            p.potPujarAlAscensor(ascensor);
                            System.out.println("El passatger puja a l'ascensor i va del pis " + p.getPisActual()
                                    + " -> " + p.getPisDesitjat());
                        }
                    }
                    ++i;
                }
            }

            ascensor.incrementaTempsAturatTotal(1);

            if ((ascensor.dalt() && ascensor.Puja()) || (ascensor.baix() && !ascensor.Puja()) ||
                    (ascensor.noExisteixTrucadaPelCami())) {
                ascensor.canviaDireccio();
            }

            if (ascensor.getTempsAturatTotal() >= ascensor.getTempsParada()) ascensor.activarMoviment();
        } else {
        }
    }

    @Override
    public boolean pujaPassatger(Passatger passatger) {
        if (passatger.getPisDesitjat() % 2 != 0 || passatger.getPisDesitjat() == 0) {
            ascensor.incrementaTempsParada(5);
            return true;
        }
        else return false;
    }

    @Override
    public void baixaPassatger(Passatger passatger) {
        ascensor.incrementaTempsParada(5);
    }

    @Override
    public void activarMoviment() {
    }
}
