package main.Estats.Ascensor;

import main.Estats.Ascensor.Descarregant;
import main.Estats.Ascensor.EstatAscensor;
import main.Estats.Ascensor.Lliure;
import main.Models.Ascensor;
import main.Models.Direccio;
import main.Models.Passatger;

public class Moviment implements EstatAscensor {

    private Ascensor ascensor;

    @Override
    public void carregar() {

    }

    @Override
    public void descarregar() {
        if (ascensor.estaBuit()) {
            ascensor.setEstat(new Lliure());
        } else {
            ascensor.setEstat(new Descarregant());
            ascensor.descarregar();
        }
    }

    @Override
    public void desplasar(int planta) {
        int diferencia = Math.abs(planta - ascensor.getPisActual());

        ascensor.setPisActual(planta);

        for (Passatger p : ascensor.getPassatgers()) {
            p.afegirTempsEnViatge(5000 * diferencia);
        }

        Direccio dir = ascensor.getSentit();
        if (dir == Direccio.PUJA) ascensor.getTipus().getUp().remove(planta);
        else ascensor.getTipus().getDown().remove(planta);
        System.out.println("L'ascensor es desplaça cap a la planta " + planta);
    }

    @Override
    public void setAscensor(Ascensor ascensor) {
        this.ascensor = ascensor;
    }
}
