package main.Models;

import java.util.*;

public class Edifici {
    private int numpisos;
    private ArrayList<Ascensor> ascensor1List;
    private LinkedList<Passatger> personesList;

    private volatile Map<Integer, LinkedList<Passatger>> gentEsperant; //La clau d'aquest map es el pis en el que estan esperant actualment


    public Edifici(int numpisos, ArrayList<Ascensor> ascensor1List, LinkedList<Passatger> persones_list) {
        this.numpisos = numpisos;
        this.ascensor1List = ascensor1List;
        personesList = persones_list;
        gentEsperant = new LinkedHashMap<>();
    }

    public Edifici(int numpisos) {
        this.numpisos = numpisos;
        ascensor1List = new ArrayList<>();
        personesList = new LinkedList<>();
        gentEsperant = new LinkedHashMap<>();
    }

    public ArrayList<Ascensor> getAscensor1List() {
        return ascensor1List;
    }

    public LinkedList<Passatger> getPersonesList() {
        return personesList;
    }

    public int getNumpisos() {
        return numpisos;
    }

    public boolean totsElsPassatgersHanArribat() {
        for (int i = 0; i < gentEsperant.size(); ++i) {
            LinkedList<Passatger> l = gentEsperant.get(i);
            if (l.size() > 0) return false;
        }
        return true;
    }

    public LinkedList<Passatger> getPassatgersEsperantAlPis(int pis) {
        LinkedList<Passatger> esperant = gentEsperant.get(pis);
        return esperant;
    }

    public Passatger getPassatgerEsperantAlPisAmbIndex(int pis, int index) {
        LinkedList<Passatger> ps = getPassatgersEsperantAlPis(pis);
        return ps.get(index);
    }

    public int getNumPersonesEsperantAlPis(int pis) {
        LinkedList<Passatger> passatgers = gentEsperant.get(pis);
        if (passatgers == null) return 0;
        else return passatgers.size();
    }

    public void afegeixPassatgerEsperant(Integer pisActual, Passatger passatger) {
        LinkedList<Passatger> currentValue = gentEsperant.get(pisActual);
        if (currentValue == null) {
            currentValue = new LinkedList<>();
            gentEsperant.put(pisActual, currentValue);
        }
        currentValue.add(passatger);
    }

    public int getTotalPersonesEsperant() {
        int num = 0;
        for (Passatger p : personesList) {
            if (!p.haArribat() && !p.estaAlAscensor()) num += 1;
        }
        return num;
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

    public void esborraPassatgerEsperant(int pisActual, Passatger passatger) {
        int numPrincipi = getNumPersonesEsperantAlPis(pisActual);
        LinkedList<Passatger> esperant = gentEsperant.get(pisActual);
        esperant.remove(passatger);
        int numFinal = getNumPersonesEsperantAlPis(pisActual);
        //gentEsperant.put(pisActual, esperant);
    }
}
