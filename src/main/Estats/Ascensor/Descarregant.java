package main.Estats.Ascensor;

import main.Models.Ascensor;

public class Descarregant implements EstatAscensor {

    private Ascensor ascensor;

    @Override
    public void carregar() { }

    @Override
    public void descarregar() {
        ascensor.baixaPassatgersQueHanArribat();
        int pis = ascensor.getPisActual();
        ascensor.setEstat(new Lliure());
        if (ascensor.getEdifici().getNumPersonesEsperantAlPis(pis) == 0) {
            ascensor.desplasar(0);
        }
        else ascensor.carregar();
    }

    @Override
    public void desplasar(int planta) {}

    @Override
    public void setAscensor(Ascensor ascensor) {
        this.ascensor = ascensor;
    }
}
