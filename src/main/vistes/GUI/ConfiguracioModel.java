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

    private static int num_ascensors_final;
    private static int num_persones_final;
    private static int num_plantes_final;

    private JTextArea númeroDePlantesATextArea;
    protected JSlider num_plantes_slider;
    private JTextArea númeroDAscensorsATextArea;
    protected JSlider num_ascensors_slider;
    private JLabel num_plantes_label;
    protected JTextArea númeroDePersonesTotalsTextArea;
    protected JSpinner num_persones_spinner;
    private JLabel num_persones_label;
    private JLabel num_ascensors_label;
    private JButton iniciarSimulacióButton;

    public JPanel panel_configuracio;

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
        SpinnerModel spinnerModel = new SpinnerNumberModel((int)MAX_PERSONES/2,
                MIN_PERSONES, MAX_PERSONES, 10);
        num_persones_spinner.setModel(spinnerModel);
        num_persones_spinner.addChangeListener(new SpinnerUpdate(num_persones_label));

        iniciarSimulacióButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                num_ascensors_final = num_ascensors_slider.getValue();
                num_persones_final = Integer.parseInt(num_persones_label.getText());
                num_plantes_final = num_plantes_slider.getValue();

                JFrame jFrame = new JFrame("Panel de simulació");
                jFrame.setContentPane(new SeleccioAscensors().seleccio);
                jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                jFrame.pack();
                jFrame.setVisible(true);
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
}
