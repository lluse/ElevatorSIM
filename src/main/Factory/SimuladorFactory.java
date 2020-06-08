package main.Factory;

import main.Models.*;

import java.util.ArrayList;
import java.util.LinkedList;

public class SimuladorFactory {

    public SimuladorFactory() {}

    public Edifici getEdifici (int numpisos, ArrayList<Ascensor> ascensor1List, LinkedList<Passatger> persones_list) {
        return new Edifici(numpisos, ascensor1List, persones_list);
    }
}
