package main.Models;

import main.Controller.MainController;
import main.Estats.Ascensor.EstatAscensor;
import main.Estats.Ascensor.Lliure;
import main.ObservadorAscensor.Observador;
import main.Statistics.Temps;
import main.TipusAscensor.TipusAscensor;
import main.TipusAscensor.ascensors.Imparell;
import main.TipusAscensor.ascensors.Lineal;
import main.TipusAscensor.ascensors.Parell;
import main.vistes.GUI.Simulacio;

import java.util.LinkedList;

public class Ascensor extends Thread implements Observador, EstatAscensor {

    private EstatAscensor estat;

    private static Direccio direccio;
    private int maxPassatgers = 11;

    private int id;

    private MainController controller;
    private Edifici edifici;
    private TipusAscensor tipus;
    private Temps tempsEspera;

    protected int pisActual;
    private int pisDesti;

    private boolean puja; //indica si l'ascensor va cap a munt
    private boolean apagat;


    private int tempsAturatTotal = 0;
    private int tempsParada = 0;

    private LinkedList<Passatger> passatgers;
    private boolean cridat;

    public Ascensor(int numAscensor, TipusAscensor tipus) {
        this.id = numAscensor;
        this.tipus = tipus; //tipus de l'ascensor

        this.controller = MainController.getInstance();
        this.tipus = tipus;
        edifici = controller.getInstance().getEdifici();
        this.tipus.setAscensor(this);
        this.pisActual = 0;
        direccio = Direccio.PUJA;
        apagat = false;

        passatgers = new LinkedList<>();
        tempsEspera = new Temps();

        setEstat(new Lliure());
    }

    public void setEstat(EstatAscensor estat) {
        this.estat = estat;
        this.estat.setAscensor(this);
    }

    public long getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getNumPassatgersAscensor() {
        if (passatgers == null) return 0;
        return passatgers.size();
    }

    public Temps getTempsEspera() {
        return tempsEspera;
    }

    public boolean estaPle() {
        return getNumPassatgersAscensor() >= maxPassatgers;
    }

    public boolean estaBuit() { return getNumPassatgersAscensor() == 0; }

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

    public Direccio getSentit() {
        return puja ? Direccio.PUJA : Direccio.BAIXA;
    }

    public void setPuja(boolean puja) {
        this.puja = puja;
    }

    public void acts() {
        tipus.act();
    }

    public boolean pujaPassatger(Passatger passatger) {
        boolean puja = tipus.pujaPassatger(passatger);
        if (!puja) return false;
        else {
            passatgers.add(passatger);
            passatger.setAscensor(this);
            //tempsEspera.afegirTempsEnEspera(passatger.getTime());
            edifici.esborraPassatgerEsperant(passatger.pisActual, passatger);
            passatger.resetTime();
            tipus.afegirPlantaDesti(passatger.getPisDesitjat());
            System.out.println("A les " + Simulacio.rellotgeDinamic.getHora() + ":" + Simulacio.rellotgeDinamic.getMinuts()
                    + " En l'ascensor: " + getId() + ", un passatger puja al pis "
                    + passatger.getPisActual() + " -> " + passatger.getPisDesitjat());

            return true;
        }
    }

    public void baixaPassatger(Passatger passatger) {
        if (passatgers.contains(passatger)) {
            edifici.getPersonesList().remove(passatger);
            passatgers.remove(passatger);
            passatger.setAscensor(null);
            tempsEspera.afegirTempsEnViatge(passatger.getTime());
            incrementaTempsParada(5);
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
            if (!tipus.getUp().isEmpty()) return false;
            for (int i = pisActual; i <= edifici.getNumpisos(); ++i) {
                if (edifici.getNumPersonesEsperantAlPis(i) > 0) {
                    return comprovaPis(i);
                }
            }
            return true;
        } else {
            if (!tipus.getDown().isEmpty()) return false;
            for (int i = pisActual; i >= 0; --i) {
                if (edifici.getNumPersonesEsperantAlPis(i) > 0) {
                    return comprovaPis(i);
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

    public boolean pisConteGentEsperant(int pis) {
        return edifici.getNumPersonesEsperantAlPis(pis) > 0;
    }


    public void activarMoviment() {
        setTempsParada(0);
        setTempsAturatTotal(0);
        if ((dalt() && Puja()) || (baix() && !Puja()) || noExisteixTrucadaPelCami()) {
            canviaDireccio();
        }
        int nextFloor = tipus.nextFloor();
        estat.desplasar(nextFloor);
    }

    public boolean comprovaHora(Rellotge horaEntrada) {
        if (horaEntrada.getHora() <= Simulacio.rellotgeDinamic.getHora()) {
            if (horaEntrada.getMinuts() <= Simulacio.rellotgeDinamic.getMinuts()) return true;
        }
        return false;
    }

    @Override
    public void cridat(int planta) {
        if (planta < pisActual) tipus.getDown().add(planta);
        else if (planta > pisActual) tipus.getUp().add(planta);
        else {
            this.carregar();
        }
        if (this.estat instanceof Lliure) {
            this.desplasar(planta);
        }
    }

    @Override
    public boolean haEstatCridat(int planta) {
        if (planta < pisActual && tipus.getDown().contains(planta)) return true;
        if (planta > pisActual && tipus.getUp().contains(planta)) return true;
        return false;
    }

    public void apagarAscensor() {
        if (!apagat) apagat = true;
    }

    public boolean noHaEstatCridatEnTotEdifici() {
        int numPlantes = edifici.getNumpisos();
        for (int i = 0; i < numPlantes; ++i) {
            if (haEstatCridat(i)) return false;
        }
        return true;
    }

    @Override
    public void carregar() {
        this.estat.carregar();
    }

    @Override
    public void descarregar() {
        this.estat.descarregar();
    }

    @Override
    public void desplasar(int planta) {
        this.estat.desplasar(planta);
    }

    @Override
    public void setAscensor(Ascensor ascensor) {
        this.estat.setAscensor(ascensor);
    }

    @Override
    public void run() {
        while (!apagat) {
            if (noHaEstatCridatEnTotEdifici()) {
                //System.out.println(Thread.currentThread() + " " + " encara no m'han cridat");
                try {
                    this.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                //System.out.println(Thread.currentThread() + " " + " ja m'han cridat");
                acts();
            }
        }
    }
}
