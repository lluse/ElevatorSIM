package main.Statistics;

import main.Controller.MainController;
import main.Main;
import main.Models.Ascensor;
import main.Models.Passatger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import main.TipusAscensor.ascensors.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.text.ParagraphView;


public class GenerateXLSX {

    ArrayList<Passatger> dadesPassatger;
    ArrayList<Ascensor> dadesAscensors;
    private int numPisos;
    private int numAscensors;
    private int horaEntrada;
    private int horaSortida;
    private int minuts;



    public GenerateXLSX() {
        dadesPassatger = new ArrayList<>(MainController.getEdifici().getPersonesList());
        dadesAscensors = new ArrayList<>(MainController.getEdifici().getAscensor1List());
        numPisos = MainController.getEdifici().getNumpisos();
        numAscensors = MainController.getEdifici().getAscensor1List().size();
    }

    public void generarExcel(String path) throws IOException, InvalidFormatException {
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Pagina ascensor
        sheetAscensors(workbook);

        //Pagina estudiants
        sheetEstudiants(workbook);



        FileOutputStream fileOut = new FileOutputStream(path);
        workbook.write(fileOut);
        fileOut.close();
    }

    private void sheetAscensors(XSSFWorkbook workbook) {
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Dades ascensor");

        //Insert data
        Map<Integer, Object[]> data = new TreeMap<>();
        data.put(1, new Object[] {"Id ascensor", "Tipus Ascensor", "Mitja Temps espera", "Mitja temps viatge"});

        Integer i = 2;
        for (Ascensor ascensor : dadesAscensors) {
            data.put(i, new Object[] {
                    ascensor.getId(), ascensor.getTipus(), ascensor.getTempsEspera().mitjanaTempsEnEspera(),
                    ascensor.getTempsEspera().mitjanaTempsEnViatge()
            });
            ++i;
        }

        Set<Integer> keySet = data.keySet();
        int rownum = 0;
        for (int key : keySet) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object object : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (object instanceof Integer) cell.setCellValue((int) object);
                if (object instanceof Lineal) cell.setCellValue("Ascensor lineal");
                else if (object instanceof Parell) cell.setCellValue("Ascensor parell");
                else if (object instanceof Imparell) cell.setCellValue("Ascensor imparell");
                if (object instanceof String) cell.setCellValue((String) object);
                if (object instanceof Float) cell.setCellValue((Float) object);
            }
        }
    }

    private void sheetEstudiants(XSSFWorkbook workbook) {
        //Create a black sheet
        XSSFSheet sheet = workbook.createSheet("Dades estudiants");
        //Insert data
        Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
        data.put(1, new Object[] {"NumPassatger", "Hora Entrada", "Pis Actual", "Pis Desitjat",
                "Temps Espera", "Num Ascensor"});
        Integer i = 2;
        for (Passatger passatger : dadesPassatger) {
            data.put(i, new Object[] {
                    i - 2, passatger.getHoraEntrada().toString(), String.valueOf(passatger.getPisActual()),
                    String.valueOf(passatger.getPisDesitjat()), String.valueOf(passatger.getTempsEspera()),
                    String.valueOf(passatger.getNumAscensor())
            });
            ++i;
        }
        Set<Integer> keySet = data.keySet();
        int rownum = 0;
        for (int key : keySet) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object object : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (object instanceof Integer) cell.setCellValue((int) object);
                if (object instanceof String) cell.setCellValue((String) object);
            }
        }
    }
}
