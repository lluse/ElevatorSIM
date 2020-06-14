package main.Controller;

import main.Models.*;
import main.TipusAscensor.TipusAscensor;
import main.TipusAscensor.ascensors.Imparell;
import main.TipusAscensor.ascensors.Lineal;
import main.TipusAscensor.ascensors.Parell;
import main.vistes.GUI.ConfiguracioModel;

import java.util.*;

public class MainController {
    private static MainController INSTANCE = null;
    public static Edifici edifici = null;

    public static MainController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainController();
        }
        return INSTANCE;
    }

    public static Edifici getEdifici() {
        return edifici;
    }

    public static void setEdifici(Edifici edifici) {
        MainController.edifici = edifici;
    }

    public void start_simulation(int numPisos, int numAscensors, int numPersones, ArrayList<String> tipusAscensors) throws InterruptedException {
        crearEdifici(numPisos, numAscensors, numPersones, tipusAscensors);

        ProcesAscensor ascensor = new ProcesAscensor(getEdifici(), getEdifici().getPersonesList());
        ascensor.start();
        enllasarPassatgers(getEdifici().getAscensor1List().get(0), getInstance().getEdifici().getPersonesList());
        new Thread(getEdifici().getAscensor1List().get(0)).start();

        if (numAscensors > 1) {
            enllasarPassatgers(getEdifici().getAscensor1List().get(1), getInstance().getEdifici().getPersonesList());
            new Thread(getEdifici().getAscensor1List().get(1)).start();

            if (numAscensors > 2) {
                enllasarPassatgers(getEdifici().getAscensor1List().get(2), getInstance().getEdifici().getPersonesList());
                new Thread(getEdifici().getAscensor1List().get(2)).start();

                if (numAscensors > 3) {
                    enllasarPassatgers(getEdifici().getAscensor1List().get(3), getInstance().getEdifici().getPersonesList());
                    new Thread(getEdifici().getAscensor1List().get(3)).start();

                }
            }
        }
    }

    private void enllasarPassatgers(Ascensor ascensor, LinkedList<Passatger> personesList) {
        for (Passatger p : personesList) {
            checkEnllas(ascensor, p);
        }
    }

    private void checkEnllas(Ascensor ascensor, Passatger p) {
        if (ascensor.getTipus() instanceof Lineal) p.enllasarObservador(ascensor);
        if (p.getPisDesitjat() % 2 == 0 && (ascensor.getTipus() instanceof Parell)) p.enllasarObservador(ascensor);
        else if (p.getPisDesitjat() % 2 != 0 && (ascensor.getTipus() instanceof Imparell)) p.enllasarObservador(ascensor);
    }


    private void crearEdifici(int numPisos, int numAscensors, int numPersones, ArrayList<String> tipusAscensors) {
        ArrayList<Ascensor> ascensors = crearAscensors(numAscensors, tipusAscensors);
        LinkedList<Passatger> passatgers = crearPassatgers(numPersones);

        Collections.sort(passatgers, new Comparator<Passatger>() {
            @Override
            public int compare(Passatger p1, Passatger p2) {
                if (p1.getHoraEntrada().getHora() < p2.getHoraEntrada().getHora()) {
                    return -1;
                } else if (p1.getHoraEntrada().getHora() > p2.getHoraEntrada().getHora()) {
                    return 1;
                } else {
                    if (p1.getHoraEntrada().getMinuts() < p2.getHoraEntrada().getMinuts()) {
                        return -1;
                    }  else if (p1.getHoraEntrada().getMinuts() > p2.getHoraEntrada().getMinuts()) {
                        return 1;
                    } else return 0;
                }
            }
        });

        Edifici edifici = new Edifici(numPisos, ascensors, passatgers);
        getInstance().setEdifici(edifici);
        for (Ascensor ascensor : ascensors) {
            ascensor.setEdifici(edifici);
        }

        passatgers.forEach((v) -> {
            System.out.println("Pis Actual: " + v.getPisActual() + ", Pis Desitjat: " + v.getPisDesitjat() + ". HORA: "
                    + v.getHoraEntrada().toString());
        });


    }

    private LinkedList<Passatger> crearPassatgers(int numPersones) {
        LinkedList<Passatger> passatgers = new LinkedList<>();

        for (int i = 0; i < numPersones; ++i) {
            int pisDesitjat = 1 + (int) Math.floor(Math.random()*ConfiguracioModel.getNum_plantes_final());

            Passatger passatgerAnada, passatgerTornada, passatgerEsporadic;

            int horaEntrada = ConfiguracioModel.getHora_entrada();
            int horaSortida = ConfiguracioModel.getHora_sortida();
            int minuts = ConfiguracioModel.getMinuts();
            //System.out.println("Hora entrada");
            passatgerAnada = new Passatger(0, pisDesitjat, obtenirHora(horaEntrada, minuts));
            //System.out.println("Hora sortida");
            passatgerTornada = new Passatger(pisDesitjat, 0, obtenirHora(horaSortida, minuts));

            if (i % 5 == 0) {
                int pisInici = pisDesitjat;
                int pisDesti = (int) Math.floor(Math.random()*ConfiguracioModel.getNum_plantes_final());
                while (pisInici == pisDesti) {
                    pisDesti = (int) Math.floor(Math.random()*ConfiguracioModel.getNum_plantes_final());
                }
                int hora = (horaEntrada + horaSortida)/2;
                passatgerEsporadic = new Passatger(pisInici, pisDesti, obtenirHora(hora, minuts));
            }
            else passatgerEsporadic = null;

            passatgers.add(passatgerAnada);
            passatgers.add(passatgerTornada);
            if (passatgerEsporadic != null) passatgers.add(passatgerEsporadic);
        }

        return passatgers;
    }

    private ArrayList<Ascensor> crearAscensors(int numAscensors, ArrayList<String> tipusAscensors) {
        ArrayList<Ascensor> ascensors = new ArrayList<>();

        Ascensor ascensor1 = new Ascensor(1, getTipusAscensor(tipusAscensors.get(0)));
        ascensors.add(ascensor1);

        if (numAscensors > 1) {
            Ascensor ascensor2 = new Ascensor(2, getTipusAscensor(tipusAscensors.get(1)));
            ascensors.add(ascensor2);


            if (numAscensors > 2) {
                Ascensor ascensor3 = new Ascensor(3, getTipusAscensor(tipusAscensors.get(2)));
                ascensors.add(ascensor3);

                if (numAscensors > 3) {
                    Ascensor ascensor4 = new Ascensor(4, getTipusAscensor(tipusAscensors.get(3)));
                    ascensors.add(ascensor4);

                }
            }
        }
        return ascensors;
    }

    private TipusAscensor getTipusAscensor(String tipus) {
        TipusAscensor tp = null;
        switch (tipus) {
            case "Linear":
                tp = new Lineal();
                break;
            case "Parell":
                tp = new Parell();
                break;
            case "Imparell":
                tp = new Imparell();
                break;
        }
        return tp;
    }

    private Rellotge obtenirHora(int hora, int minuts) {
        Random aleatori = new Random();
        int num = minuts + calculaHora(hora, minuts);
        return new Rellotge(num / 60, num % 60, 0);
    }

    private int calculaHora(int hora, int minuts) {
        Random random = new Random();
        double aleatori = (random.nextGaussian()*20 + (60*hora));
        return (int) aleatori;
    }
}
