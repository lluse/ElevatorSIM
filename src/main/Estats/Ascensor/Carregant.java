package main.Estats.Ascensor;

import main.Estats.Ascensor.EstatAscensor;
import main.Models.Ascensor;

public class Carregant implements EstatAscensor {

    private Ascensor ascensor;

    @Override
    public void carregar() {

    }

    @Override
    public void descarregar() {

    }

    @Override
    public void desplasar(int planta) {

    }

    @Override
    public void setAscensor(Ascensor ascensor) {
        this.ascensor = ascensor;
    }
}
