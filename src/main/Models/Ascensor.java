package main.Models;

import main.Controller.MainController;
import main.Statistics.Temps;
import main.TipusAscensor.TipusAscensor;
import main.TipusAscensor.ascensors.Imparell;
import main.TipusAscensor.ascensors.Lineal;
import main.TipusAscensor.ascensors.Parell;

import java.util.LinkedList;

public class Ascensor {

    private static final int PUJA = 1;
    private static final int BAIXA = -1;
    private int maxPassatgers = 11;

    private int id;

    private MainController controller;
    private Edifici edifici;
    private TipusAscensor tipus;
    private Temps tempsEspera;

    protected int pisActual;
    private int pisDesti;
    private boolean puja; //indica si l'ascensor va cap a munt
    private int tempsAturatTotal = 0;
    private int tempsParada = 0;

    private LinkedList<Passatger> passatgers;
    private boolean enMoviment;

    public Ascensor(TipusAscensor tipus) {
        this.controller = MainController.getInstance();
        this.tipus = tipus;
        edifici = controller.getEdifici();
        this.tipus = tipus; //tipus de l'ascensor
        this.tipus.setAscensor(this);
        this.pisActual = 0;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getNumPassatgersAscensor() {
        return passatgers.size();
    }

    public Temps getTempsEspera() {
        return tempsEspera;
    }

    public boolean estaPle() {
        return getNumPassatgersAscensor() >= maxPassatgers;
    }

    /**
     *
     * Indica si l'ascesor esta al pis mes alt
     * @return
     */
    public boolean dalt() {
        return pisActual >= controller.getEdifici().getNumpisos();
    }

    /**
     * Indica si l'ascensors esta al pis mes baix
     * @return
     */
    public boolean baix() {
        return pisActual <= 0;
    }

    public int getSentit() {
        return puja ? PUJA : BAIXA;
    }

    public void setPuja(boolean puja) {
        this.puja = puja;
    }

    public void acts() {
        tipus.act();
    }

    public boolean pujaPassatger(Passatger passatger) {
        if (getNumPassatgersAscensor() + 1 > maxPassatgers) return false;
        else {
            boolean puja = tipus.pujaPassatger(passatger);
            if (!puja) return false;
            else {
                passatgers.add(passatger);
                passatger.setAscensor(this);
                tempsEspera.afegirTempsEnEspera(passatger.getTime());
                passatger.resetTime();
                System.out.println("Un passatger puja a " + passatger.getPisActual() + " -> " + passatger.getPisDesitjat());
                return true;
            }
        }
    }

    public void baixaPassatger(Passatger passatger) {
        if (passatgers.contains(passatger)) {
            passatgers.remove(passatger);
            passatger.setAscensor(null);
            tempsEspera.afegirTempsEnViatge(passatger.getTime());
            tipus.baixaPassatger(passatger);
        }
    }

    public void baixaPassatgersQueHanArribat() {
        for (int i = 0; i < getNumPassatgersAscensor(); ++i) {
            if (passatgers.get(i).getPisDesitjat() == pisActual) {
                baixaPassatger(passatgers.get(i));
            }
        }
    }

    public boolean noExisteixTrucadaPelCami() {
        if (puja) {
            for (int i = pisActual; i <= edifici.getNumpisos(); ++i) {
                if (edifici.getNumPersonesEsperantAlPis(i) > 0) {
                    return comprovaPis(i);
                }
                for (Passatger p : passatgers) {
                    if (p.getPisDesitjat() == i) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            for (int i = pisActual; i >= 0; --i) {
                if (edifici.getNumPersonesEsperantAlPis(i) > 0) {
                    return comprovaPis(i);
                }
                for (Passatger p : passatgers) {
                    if (p.getPisDesitjat() == i) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private boolean comprovaPis(int pis) {
        if (pis % 2 == 0 && this.tipus instanceof Parell) return false;
        else if (pis % 2 != 0 && this.tipus instanceof Imparell) return false;
        else if (this.tipus instanceof Lineal) return false;
        else return true;
    }

    public Edifici getEdifici() {
        return edifici;
    }

    public void setEdifici(Edifici edifici) {
        this.edifici = edifici;
    }

    public TipusAscensor getTipus() {
        return tipus;
    }

    public void setTipus(TipusAscensor tipus) {
        this.tipus = tipus;
    }

    public void setTempsEspera(Temps tempsEspera) {
        this.tempsEspera = tempsEspera;
    }

    public int getPisActual() {
        return pisActual;
    }

    public void setPisActual(int pisActual) {
        this.pisActual = pisActual;
    }

    public int getPisDesti() {
        return pisDesti;
    }

    public void setPisDesti(int pisDesti) {
        this.pisDesti = pisDesti;
        if (pisDesti < getPisActual()) {
            setPuja(false);
        } else {
            setPuja(true);
        }
    }

    public void canviaDireccio() {
        puja = !puja;
    }

    public boolean Puja() {
        return puja;
    }

    public int getTempsAturatTotal() {
        return tempsAturatTotal;
    }

    public void setTempsAturatTotal(int tempsAturatTotal) {
        this.tempsAturatTotal = tempsAturatTotal;
    }

    public int getTempsParada() {
        return tempsParada;
    }

    public void setTempsParada(int tempsParada) {
        this.tempsParada = tempsParada;
    }

    public void incrementaTempsAturatTotal(int tempsAturatTotal) {
        this.tempsAturatTotal += tempsAturatTotal;
    }

    public void incrementaTempsParada(int tempsParada) {
        this.tempsParada += tempsParada;
    }

    public LinkedList<Passatger> getPassatgers() {
        return passatgers;
    }

    public void setPassatgers(LinkedList<Passatger> passatgers) {
        this.passatgers = passatgers;
    }

    public boolean isEnMoviment() {
        return enMoviment;
    }

    public void setEnMoviment(boolean enMoviment) {
        this.enMoviment = enMoviment;
    }

    public void activarMoviment() {
        setTempsParada(0);
        setTempsAturatTotal(0);
        setEnMoviment(true);
        tipus.activarMoviment();
    }
}
