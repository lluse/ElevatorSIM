package main.Estats.Ascensor;

import main.Models.Ascensor;

public class Lliure implements EstatAscensor {

    private Ascensor ascensor;

    @Override
    public void carregar() {
        if (!ascensor.estaPle()) {
            ascensor.setEstat(new Carregant());
            ascensor.carregar();
        } else {
            if ((ascensor.dalt() && ascensor.Puja()) || (ascensor.baix() && !ascensor.Puja()) ||
                    (ascensor.noExisteixTrucadaPelCami())) {
                ascensor.canviaDireccio();
            }
            int planta = ascensor.getTipus().nextFloor();
            ascensor.setEstat(new Moviment());
            ascensor.desplasar(planta);
        }
    }

    @Override
    public void descarregar() {
    }

    @Override
    public void desplasar(int planta) {
        ascensor.setEstat(new Moviment());
    }


    @Override
    public void setAscensor(Ascensor ascensor) {
        this.ascensor = ascensor;
    }
}
