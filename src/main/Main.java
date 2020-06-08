package main;

import main.vistes.GUI.ConfiguracioModel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Simulador Ascensors DES");
        jFrame.setContentPane(new ConfiguracioModel().panel_configuracio);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
