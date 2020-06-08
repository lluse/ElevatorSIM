package main.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Edifici {
    private int numpisos;
    private ArrayList<Ascensor> ascensor1List;
    private LinkedList<Passatger> personesList;


    public Edifici(int numpisos, ArrayList<Ascensor> ascensor1List, LinkedList<Passatger> persones_list) {
        this.numpisos = numpisos;
        this.ascensor1List = ascensor1List;
        personesList = persones_list;
    }

    public Edifici(int numpisos) {
        this.numpisos = numpisos;
        ascensor1List = new ArrayList<>();
        personesList = new LinkedList<>();
    }

    public ArrayList<Ascensor> getAscensor1List() {
        return ascensor1List;
    }

    public void setAscensor1List(ArrayList<Ascensor> ascensor1List) {
        this.ascensor1List = ascensor1List;
    }

    public LinkedList<Passatger> getPersonesList() {
        return personesList;
    }

    public void setPersonesList(LinkedList<Passatger> personesList) {
        this.personesList = personesList;
    }

    public int getNumpisos() {
        return numpisos;
    }

    public int getNumAscensors() {
        return ascensor1List.size();
    }

    public boolean totsElsPassatgersHanArribat() {
        for (Passatger p : personesList) {
            if (!p.haArribat()) return false;
        }
        return true;
    }

    public LinkedList<Passatger> getPassatgersEsperantAlPis(int pis) {
        LinkedList<Passatger> esperant = new LinkedList<>();
        for (Passatger p : personesList) {
            if (p.estaEsperantAlPis(pis)) esperant.add(p);
        }
        return esperant;
    }

    public Passatger getPassatgerEsperantAlPisAmbIndex(int pis, int index) {
        LinkedList<Passatger> ps = getPassatgersEsperantAlPis(pis);
        if (index >= ps.size()) return null;
        else return ps.get(index);
    }

    public int getNumPersonesEsperantAlPis(int pis) {
        int num = 0;
        for (Passatger p : getPassatgersEsperantAlPis(pis)) {
            num += 1;
        }
        return num;
    }

    public LinkedList<Passatger> getPassatgersAlPis(Passatger passatger) {
        if (passatger.haArribat()) {
            return getPassatgersQueHanArribatAlPis(passatger.getPisActual());
        } else {
            return getPassatgersEsperantAlPis(passatger.getPisActual());
        }
    }

    public int getTotalPersonesEsperant() {
        int num = 0;
        for (Passatger p : personesList) {
            if (!p.haArribat() && !p.estaAlAscensor()) num += 1;
        }
        return num;
    }

    public int getPassatgerIndex(Passatger passatger) {
        LinkedList<Passatger> list = getPassatgersAlPis(passatger);
        return list.indexOf(passatger);
    }

    private LinkedList<Passatger> getPassatgersQueHanArribatAlPis(int pisActual) {
        LinkedList<Passatger> list = new LinkedList<>();
        for (Passatger p : personesList) {
            if (p.haArribat()) list.add(p);
        }
        return list;
    }

    public int getNumPassatgerAlPis(int pis) {
        int num = 0;
        for (Passatger passatger : getPassatgersQueHanArribatAlPis(pis)) {
            num += 1;
        }
        return num;
    }

    public int getPisAmbMesGentEsperant() {
        int maxPis = 0;
        int numPersones = 0;
        for (int i = 0; i <= numpisos; ++i) {
            if (getNumPersonesEsperantAlPis(i) > numPersones) {
                maxPis = i;
                numPersones = getNumPersonesEsperantAlPis(i);
            }
        }
        return maxPis;
    }

    public ArrayList<Integer> getPisAmbNumPassatgersEsperant() {
        ArrayList<Integer> numEsperant = new ArrayList<>();
        for (int i = 0; i < numpisos; ++i) {
            if (getNumPersonesEsperantAlPis(i) > 0) {
                numEsperant.add(i);
            }
        }
        return numEsperant;
    }
}
