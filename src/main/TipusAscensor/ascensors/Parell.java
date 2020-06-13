package main.TipusAscensor.ascensors;

import main.Models.Ascensor;
import main.Models.Passatger;
import main.Models.Rellotge;
import main.TipusAscensor.TipusAscensor;

public class Parell extends TipusAscensor {

    public Parell() {
        super();
    }

    public Parell(Ascensor ascensor) {
        super(ascensor);
    }

    @Override
    public Class getType() {
        return TipusAscensor.class;
    }

    @Override
    public void act() {
        if (!ascensor.getEdifici().totsElsPassatgersHanArribat()
                && (ascensor.getEdifici().getTotalPersonesEsperant() != 0 || ascensor.getNumPassatgersAscensor() != 0)) {
            ascensor.descarregar();
            if (ascensor.pisConteGentEsperant(ascensor.getPisActual())) {
                ascensor.carregar();
            }
        } else {
            this.ascensor.setEsperant(true);
        }
    }


    @Override
    public boolean pujaPassatger(Passatger passatger) {
        if (passatger.getPisDesitjat() % 2 == 0) {
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
