package main.Estats.Ascensor;

import main.Models.Ascensor;

public interface EstatAscensor {

    void carregar();
    void descarregar();
    void desplasar(int planta);


    void setAscensor(Ascensor ascensor);
}
