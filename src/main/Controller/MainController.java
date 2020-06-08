package main.Controller;

import main.Models.Ascensor;
import main.Models.Edifici;
import main.Models.Passatger;
import main.Models.Rellotge;
import main.TipusAscensor.TipusAscensor;
import main.TipusAscensor.ascensors.Imparell;
import main.TipusAscensor.ascensors.Lineal;
import main.TipusAscensor.ascensors.Parell;
import main.vistes.GUI.ConfiguracioModel;
import main.vistes.GUI.SeleccioAscensors;
import main.vistes.GUI.Simulacio;

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

    public void start_simulation(int numPisos, int numAscensors, int numPersones, ArrayList<String> tipusAscensors) {
        crearEdifici(numPisos, numAscensors, numPersones, tipusAscensors);

        ProcesAscensor ascensor1 = new ProcesAscensor();

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
        passatgers.forEach((v) -> {

            System.out.println(v.getPisDesitjat() + " " +
                    v.getHoraEntrada().getHora() + ":" + v.getHoraEntrada().getMinuts());
        });

        Edifici edifici = new Edifici(numPisos, ascensors, passatgers);
        setEdifici(edifici);
    }

    private LinkedList<Passatger> crearPassatgers(int numPersones) {
        LinkedList<Passatger> passatgers = new LinkedList<>();

        for (int i = 0; i < numPersones; ++i) {
            int pisDesitjat = 1 + (int) Math.floor(Math.random()*ConfiguracioModel.getNum_plantes_final());

            Passatger passatgerAnada, passatgerTornada;

            if (i < (numPersones/5)) {
                passatgerAnada = new Passatger(0, pisDesitjat, obtenirHoraAnada(8, 30));
                passatgerTornada = new Passatger(pisDesitjat, 0, obtenirHoraTornada(8 + 3, 30));
            }
            else if ((numPersones/5) < i && i < (numPersones/5) * 2) {
                passatgerAnada = new Passatger(0, pisDesitjat, obtenirHoraAnada(9, 0));
                passatgerTornada = new Passatger(pisDesitjat, 0, obtenirHoraTornada(9 + 3, 0));
            }
            else if ((numPersones/5) * 2 < i && i < (numPersones/5) * 3) {
                passatgerAnada = new Passatger(0, pisDesitjat, obtenirHoraAnada(9, 30));
                passatgerTornada = new Passatger(pisDesitjat, 0, obtenirHoraTornada(9 + 3, 30));
            }

            else if ((numPersones/5) * 3 < i && i < (numPersones/5) * 4) {
                passatgerAnada = new Passatger(0, pisDesitjat, obtenirHoraAnada(10, 0));
                passatgerTornada = new Passatger(pisDesitjat, 0, obtenirHoraTornada(10 + 3, 0));
            }

            else {
                passatgerAnada = new Passatger(0, pisDesitjat, obtenirHoraAnada(10, 30));
                passatgerTornada = new Passatger(pisDesitjat, 0, obtenirHoraTornada(10 + 3, 30));
            }

            passatgers.add(passatgerAnada);
            passatgers.add(passatgerTornada);
        }

        return passatgers;
    }

    private ArrayList<Ascensor> crearAscensors(int numAscensors, ArrayList<String> tipusAscensors) {
        ArrayList<Ascensor> ascensors = new ArrayList<>();
        Ascensor ascensor1 = new Ascensor(getTipusAscensor(tipusAscensors.get(0)));
        ascensors.add(ascensor1);

        if (numAscensors > 1) {
            Ascensor ascensor2 = new Ascensor(getTipusAscensor(tipusAscensors.get(1)));
            ascensors.add(ascensor2);

            if (numAscensors > 2) {
                Ascensor ascensor3 = new Ascensor(getTipusAscensor(tipusAscensors.get(2)));
                ascensors.add(ascensor3);

                if (numAscensors > 3) {
                    Ascensor ascensor4 = new Ascensor(getTipusAscensor(tipusAscensors.get(3)));
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

    private Rellotge obtenirHoraAnada(int hora, int minuts) {
        Random aleatori = new Random();
        int numAleatori = minuts + aleatori.nextInt(45 - minuts);
        return new Rellotge(hora, numAleatori, 0);
    }

    private Rellotge obtenirHoraTornada(int hora, int minuts) {
        Random aleatori = new Random();
        int numAleatoriH = hora + aleatori.nextInt(19 - hora);
        int numAleatoriM = minuts + aleatori.nextInt(45 - minuts);
        return new Rellotge(numAleatoriH, numAleatoriM, 0);
    }
}