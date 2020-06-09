package main.Estats.Ascensor;

import main.Models.Ascensor;

public class Descarregant implements EstatAscensor {

    private Ascensor ascensor;

    @Override
    public void carregar() {

    }

    @Override
    public void descarregar() {
        ascensor.baixaPassatgersQueHanArribat();
        ascensor.setEstat(new Lliure());
    }

    @Override
    public void desplasar(int planta) {

    }

    @Override
    public void setAscensor(Ascensor ascensor) {
        this.ascensor = ascensor;
    }
}
