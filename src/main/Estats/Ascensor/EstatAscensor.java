package main.Estats.Ascensor;

import main.Models.Ascensor;

public interface EstatAscensor {

    void carregar() throws InterruptedException;
    void descarregar() throws InterruptedException;
    void desplasar(int planta) throws InterruptedException;


    void setAscensor(Ascensor ascensor);
}
