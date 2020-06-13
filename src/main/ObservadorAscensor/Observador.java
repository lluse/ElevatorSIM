package main.ObservadorAscensor;

import main.TipusAscensor.TipusAscensor;

public interface Observador {
    void cridat(int planta); //Notificar a l'ascensor que l'han cridat des d'una altra planta

    boolean haEstatCridat(int planta);

    boolean estaEsperant();

    int getPis();

    TipusAscensor getTipus();

    //boolean noHaEstatCridatEnTotEdifici();
}
