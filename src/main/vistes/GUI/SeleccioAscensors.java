package main.vistes.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeleccioAscensors {
    private JButton començar;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;

    protected static String tipus1;
    protected static String tipus2;
    protected static String tipus3;
    protected static String tipus4;

    public JPanel seleccio;
    private JButton enrereButton;

    public SeleccioAscensors() {
        comboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Linear", "Parell", "Imparell" }));
        if (ConfiguracioModel.getNum_ascensors_final() > 1) {
            comboBox2.setModel(new javax.swing.DefaultComboBoxModel<>
                    (new String[] { "Linear", "Parell", "Imparell" }));
            if (ConfiguracioModel.getNum_ascensors_final() > 2) {
                comboBox3.setModel(new javax.swing.DefaultComboBoxModel<>
                        (new String[] { "Linear", "Parell", "Imparell" }));
                if (ConfiguracioModel.getNum_ascensors_final() > 3) {
                    comboBox4.setModel(new javax.swing.DefaultComboBoxModel<>
                            (new String[] { "Linear", "Parell", "Imparell" }));
                } else {
                    comboBox4.setEnabled(false);
                }
            } else {
                comboBox3.setEnabled(false);
                comboBox4.setEnabled(false);
            }
        } else {
            comboBox2.setEnabled(false);
            comboBox3.setEnabled(false);
            comboBox4.setEnabled(false);
        }

        començar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obtenirTipusAscensor();
                JFrame jFrame = new JFrame("Panel de simulació");
                jFrame.setContentPane(new Simulacio().panel_simulacio);
                jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                jFrame.pack();
                jFrame.setVisible(true);
            }
        } );

        enrereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }


    private void obtenirTipusAscensor() {
        tipus1 = String.valueOf(comboBox1.getSelectedItem());
        tipus2 = String.valueOf(comboBox2.getSelectedItem());
        tipus3 = String.valueOf(comboBox3.getSelectedItem());
        tipus4 = String.valueOf(comboBox4.getSelectedItem());
    }
}
