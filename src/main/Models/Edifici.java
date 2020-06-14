package main.Models;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Edifici {
    private int numpisos;
    private ArrayList<Ascensor> ascensor1List;
    private final LinkedList<Passatger> personesList;

    private volatile Map<Integer, LinkedList<Passatger>> gentEsperant; //La clau d'aquest map es el pis en el que estan esperant actualment


    public Edifici(int numpisos, ArrayList<Ascensor> ascensor1List, LinkedList<Passatger> persones_list) {
        this.numpisos = numpisos;
        this.ascensor1List = ascensor1List;
        personesList = persones_list;
        gentEsperant = Collections.synchronizedMap(new HashMap<>());
    }

    public Edifici(int numpisos) {
        this.numpisos = numpisos;
        ascensor1List = new ArrayList<>();
        personesList = new LinkedList<>();
        gentEsperant = Collections.synchronizedMap(new HashMap<>());
    }

    public Map<Integer, LinkedList<Passatger>> getGentEsperant() {
        return gentEsperant;
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

    public synchronized boolean totsElsPassatgersHanArribat() {
        for (int i = 0; i < gentEsperant.size(); ++i) {
            LinkedList<Passatger> l = gentEsperant.get(i);
            if (l != null && l.size() > 0) return false;
        }
        return true;
    }

    public LinkedList<Passatger> getPassatgersEsperantAlPis(int pis) {
        LinkedList<Passatger> esperant = gentEsperant.get(pis);
        return esperant;
    }

    public Passatger getPassatgerEsperantAlPisAmbIndex(int pis, int index) {
        LinkedList<Passatger> ps = getPassatgersEsperantAlPis(pis);
        synchronized(this) {
            if (ps.size() > 0 && ps.size() != index && ps.size() > index) {
                System.out.println("En el thread: " + Thread.currentThread()
                        + " el numero de gent que espera es: " + ps.size());

                Passatger p;
                if (ps.size() > 0 && ps.size() != index) p = ps.get(index);
                else p = null;


                if (p != null) return p;
                else return null;
            } else return null;
        }
    }

    public int getNumPersonesEsperantAlPis(int pis) {
        LinkedList<Passatger> passatgers = gentEsperant.get(pis);
        if (passatgers == null) return 0;
        else return passatgers.size();
    }

    public synchronized void afegeixPassatgerEsperant(Integer pisActual, Passatger passatger) {
        LinkedList<Passatger> currentValue = gentEsperant.get(pisActual);
        if (currentValue == null) {
            currentValue = new LinkedList<>();
            gentEsperant.put(pisActual, currentValue);
        }
        currentValue.add(passatger);
    }

    public synchronized int getTotalPersonesEsperant() {
        int num = 0;
        for (Passatger p : personesList) {
            if (!p.haArribat() && !p.estaAlAscensor()) num += 1;
        }
        return num;
    }

    public int getTotalEsperant() {
        int num = 0;
        for (int i = 0; i < gentEsperant.size(); ++i) {
            num += gentEsperant.get(i).size();
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
