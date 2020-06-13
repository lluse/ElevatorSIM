package main.vistes.GUI;

import main.vistes.eventUpdate.SliderUpdate;
import main.vistes.eventUpdate.SpinnerUpdate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfiguracioModel {

    private static final int MIN_PLANTES = 2;
    private static final int MAX_PLANTES = 7;
    private static final int MIN_ASCENSORS = 1;
    private static final int MAX_ASCENSORS = 4;
    private static final int MIN_PERSONES = 100;
    private static final int MAX_PERSONES = 2000;
    private static final int MAX_PERSONES_PER_ASCENSOR = 11;

    private static final int MIN_HORA_INI = 8;
    private static final int MAX_HORA_FI = 18;

    private static final int MIN_MINUTS = 0;
    private static final int MAX_MINTUS = 59;

    private static int num_ascensors_final;
    private static int num_persones_final;
    private static int num_plantes_final;
    private static int hora_entrada;
    private static int hora_sortida;
    private static int minuts;

    protected JSlider num_plantes_slider;
    protected JSlider num_ascensors_slider;
    private JLabel num_plantes_label;
    protected JSpinner num_persones_spinner;
    private JLabel num_persones_label;
    private JLabel num_ascensors_label;
    private JButton iniciarSimulacióButton;

    public JPanel panel_configuracio;
    private JSpinner horaIni_spinner;
    private JSpinner minIni_spinner;
    private JSpinner horaFi_spinner;
    private JSpinner minFi_spinner;

    public ConfiguracioModel() {

        num_plantes_label.setText(String.valueOf((int)MAX_PLANTES/2));

        num_plantes_slider.setValue((int)MAX_PLANTES/2);
        num_plantes_slider.setMaximum(MAX_PLANTES);
        num_plantes_slider.setMinimum(MIN_PLANTES);
        num_plantes_slider.addChangeListener(new SliderUpdate(num_plantes_label));

        num_ascensors_label.setText(String.valueOf((int)MAX_ASCENSORS/2));

        num_ascensors_slider.setValue(MAX_ASCENSORS/2);
        num_ascensors_slider.setMaximum(MAX_ASCENSORS);
        num_ascensors_slider.setMinimum(MIN_ASCENSORS);
        num_ascensors_slider.addChangeListener(new SliderUpdate(num_ascensors_label));

        num_persones_label.setText(String.valueOf((int) MAX_PERSONES/2));

        num_persones_spinner.setValue((int)MAX_PERSONES/2);
        SpinnerModel spinnerModelPersones = new SpinnerNumberModel((int)MAX_PERSONES/2,
                MIN_PERSONES, MAX_PERSONES, 10);
        num_persones_spinner.setModel(spinnerModelPersones);
        num_persones_spinner.addChangeListener(new SpinnerUpdate(num_persones_label));

        horaIni_spinner.setValue((int) MIN_HORA_INI);
        SpinnerModel spinnerModelHoraIni = new SpinnerNumberModel((int)MIN_HORA_INI,
                MIN_HORA_INI, MAX_HORA_FI, 1);
        horaIni_spinner.setModel(spinnerModelHoraIni);

        horaFi_spinner.setValue((int) MAX_HORA_FI);
        SpinnerModel spinnerModelHoraFi = new SpinnerNumberModel((MAX_HORA_FI + MIN_HORA_INI)/2,
                (int) MIN_HORA_INI + 1, MAX_HORA_FI, 1);
        horaFi_spinner.setModel(spinnerModelHoraFi);

        minIni_spinner.setValue(0);
        minFi_spinner.setValue(30);
        SpinnerModel spinnerMinuts = new SpinnerNumberModel(0, MIN_MINUTS, MAX_MINTUS, 5);
        minIni_spinner.setModel(spinnerMinuts);
        minFi_spinner.setModel(spinnerMinuts);

        iniciarSimulacióButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                num_ascensors_final = num_ascensors_slider.getValue();
                num_persones_final = Integer.parseInt(num_persones_label.getText());
                num_plantes_final = num_plantes_slider.getValue();
                hora_entrada = (int) horaIni_spinner.getValue();
                hora_sortida = (int) horaFi_spinner.getValue();
                minuts = (int) minIni_spinner.getValue();

                try {
                    if (!horesCorrectes((int) horaFi_spinner.getValue(), (int) horaIni_spinner.getValue(),
                            (int) minIni_spinner.getValue(), (int) minFi_spinner.getValue()))  {
                        throw new IllegalArgumentException("Les hores estan malament");
                    } else {
                        JFrame jFrame = new JFrame("Panel de simulació");
                        jFrame.setContentPane(new SeleccioAscensors().seleccio);
                        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                        jFrame.pack();
                        jFrame.setVisible(true);
                    }
                } catch (IllegalArgumentException i) {
                    JOptionPane.showMessageDialog(null, "Hi ha d'haver un interval de dos " +
                            "hores mínim entre la hora inicial i la final");
                }
            }
        });
    }

    public static int getNum_ascensors_final() {
        return num_ascensors_final;
    }

    public static int getNum_persones_final() {
        return num_persones_final;
    }

    public static int getNum_plantes_final() {
        return num_plantes_final;
    }

    public static int getHora_entrada() {
        return hora_entrada;
    }

    public static int getHora_sortida() {
        return hora_sortida;
    }

    public static int getMinuts() {
        return minuts;
    }

    private boolean horesCorrectes(int horaMax, int horaMin, int minMax, int minMin) {
        if (horaMin + 2 <= horaMax) return true;
        else return false;
    }
}
