package main.Statistics;

import main.Controller.MainController;
import main.Models.Passatger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenerateCSV {

    ArrayList<Passatger> dades;

    public GenerateCSV() {
        dades = new ArrayList<>(MainController.getEdifici().getPersonesList());
    }

    public void generateCSV(String path) {

        File file = new File(path);


        try {
            //crear un FileWriter amb l'arxiu d'abans
            FileWriter csvFile = new FileWriter(file);

            csvFile.append("NumPassatger");
            csvFile.append(",");
            csvFile.append("Pis inicial");
            csvFile.append(",");
            csvFile.append("Pis final");
            csvFile.append(",");
            csvFile.append("Hora entra edifici");
            csvFile.append(",");
            csvFile.append("Temps espera");
            csvFile.append(",");
            csvFile.append("Temps viatge");
            csvFile.append(",");
            csvFile.append("Ascensor utilitzat");
            csvFile.append("\n");

            int i = 0;
            for (Passatger passatger : dades) {
                List<String> dadesPassatger = generarLlista(passatger, i);

                csvFile.append(String.join(",", dadesPassatger));
                csvFile.append("\n");

                ++i;
            }
            csvFile.flush();
            csvFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> generarLlista(Passatger passatger, int numPassatger) {
        List<String> llista = new ArrayList<>();
        llista.add(String.valueOf(numPassatger));
        llista.add(String.valueOf(passatger.getPisActual()));
        llista.add(String.valueOf(passatger.getPisDesitjat()));
        llista.add(passatger.getHoraEntrada().toString());
        llista.add(String.valueOf(passatger.getTempsEspera()));
        llista.add(String.valueOf(passatger.getTempsViatge()));
        llista.add(String.valueOf(passatger.getNumAscensor()));
        return llista;
    }
}
