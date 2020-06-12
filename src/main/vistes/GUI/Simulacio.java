package main.vistes.GUI;

import main.Controller.MainController;
import main.Factory.SimuladorFactory;
import main.Models.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Simulacio {

    public static Simulacio instance = getInstance();

    private JPanel ascensor1;
    private JPanel ascensor2;
    private JPanel ascensor3;
    private JPanel ascensor4;

    private JLabel ascensor1Image;
    private JLabel ascensor3Image;
    private JLabel ascensor2Image;
    private JLabel ascensor4Image;

    public JPanel panel_simulacio;
    private JLabel tipus1;
    private JLabel tipus2;
    private JLabel tipus3;
    private JLabel tipus4;
    private JPanel infoPanel;
    private JButton executar;
    public JLabel lblRellotge;

    SimuladorFactory sim = new SimuladorFactory();
    public static volatile Rellotge rellotgeDinamic;

    public static Simulacio getInstance() {
        if (instance == null) return new Simulacio();
        else return instance;
    }

    public Simulacio() {
        afegirImatgesAscensor();
        afegirTipusAscensor();
        executar.setIcon(new ImageIcon(Simulacio.class.getResource("../imatges/icons_play.png")));
        rellotgeDinamic = new Rellotge(11,29,55);
        lblRellotge.setText(rellotgeDinamic.toString());
        executar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start_simulation();
            }
        });
    }

    private void start_simulation() {
        //rellotgeDinamic.getHl().start();
        executar.setEnabled(false);
        iniciarRellotgeActual();

        try {
            MainController.getInstance().start_simulation(ConfiguracioModel.getNum_plantes_final(),
                    ConfiguracioModel.getNum_ascensors_final(), ConfiguracioModel.getNum_persones_final(),
                    afegirTipusAscensorArraylist());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> afegirTipusAscensorArraylist() {
        ArrayList<String> tipus = new ArrayList<>();
        tipus.add(SeleccioAscensors.tipus1);
        if (!SeleccioAscensors.tipus2.contentEquals("null")) tipus.add(SeleccioAscensors.tipus2);
        if (!SeleccioAscensors.tipus3.contentEquals("null")) tipus.add(SeleccioAscensors.tipus3);
        if (!SeleccioAscensors.tipus4.contentEquals("null")) tipus.add(SeleccioAscensors.tipus4);
        return tipus;
    }

    private void iniciarRellotgeActual() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5);
                        lblRellotge.setText(rellotgeDinamic.incrementar());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }

    private void afegirImatgesAscensor() {
        ascensor1Image.setIcon(new ImageIcon(Simulacio.class.getResource("../imatges/ascensor.png")));
        ascensor1Image.setText("");
        ascensor2Image.setIcon(new ImageIcon(Simulacio.class.getResource("../imatges/ascensor.png")));
        ascensor2Image.setText("");
        ascensor3Image.setIcon(new ImageIcon(Simulacio.class.getResource("../imatges/ascensor.png")));
        ascensor3Image.setText("");
        ascensor4Image.setIcon(new ImageIcon(Simulacio.class.getResource("../imatges/ascensor.png")));
        ascensor4Image.setText("");
    }

    private void afegirTipusAscensor() {
        tipus1.setText("Tipus: " + SeleccioAscensors.tipus1);
        if (!SeleccioAscensors.tipus2.contentEquals("null")) tipus2.setText("Tipus: " + SeleccioAscensors.tipus2);
        else tipus2.setText("Ascensor Tancat");
        if (!SeleccioAscensors.tipus3.contentEquals("null")) tipus3.setText("Tipus: " + SeleccioAscensors.tipus3);
        else tipus3.setText("Ascensor Tancat");
        if (!SeleccioAscensors.tipus4.contentEquals("null")) tipus4.setText("Tipus: " + SeleccioAscensors.tipus4);
        else tipus4.setText("Ascensor Tancat");
    }

    public Rellotge getRellotgeActual() {
        return rellotgeDinamic;
    }

    public void setRellotgeActual(Rellotge rellotgeDinamic) {
        this.rellotgeDinamic = rellotgeDinamic;
    }
}
