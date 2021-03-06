package main.Estats.Ascensor;

import main.Models.Ascensor;

public class Lliure implements EstatAscensor {

    private Ascensor ascensor;

    @Override
    public void carregar() {
        if (this.ascensor != null) {
            if (!ascensor.estaPle()) {
                ascensor.setEstat(new Carregant());
                ascensor.carregar();
            } else {
                if (!ascensor.noHaEstatCridatEnTotEdifici()) { //si l'han cridat des d'alguna planta llavors es mou, sino res
                    if ((ascensor.dalt() && ascensor.Puja()) || (ascensor.baix() && !ascensor.Puja()) ||
                            (ascensor.noExisteixTrucadaPelCami())) {
                        ascensor.canviaDireccio();
                    }
                    if ((ascensor.getTipus().getUp().size() > 0 && ascensor.Puja()) ||
                            (ascensor.getTipus().getDown().size() > 0 && !ascensor.Puja())) {
                        int planta = ascensor.getTipus().nextFloor();
                        ascensor.setEstat(new Moviment());
                        ascensor.desplasar(planta);
                    } else ascensor.setEsperant(true);
                }
            }
        }
    }

    @Override
    public void descarregar() {
    }

    @Override
    public void desplasar(int planta) {
        if (!ascensor.noHaEstatCridatEnTotEdifici()) {
            ascensor.setEstat(new Moviment());
            ascensor.activarMoviment();
        }
    }




    @Override
    public void setAscensor(Ascensor ascensor) {
        this.ascensor = ascensor;
    }
}
