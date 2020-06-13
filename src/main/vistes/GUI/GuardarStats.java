package main.vistes.GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

public class GuardarStats {
    private JRadioButton siRadioButton;
    private JRadioButton noRadioButton;
    private JTextField textField1;
    private JButton browseButton;
    private JButton guardarButton;
    private JButton sortirButton;

    public JPanel panel_stats;

    public GuardarStats() {
        siRadioButton.setSelected(true);

        siRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarButton.setEnabled(true);
                browseButton.setEnabled(true);
                noRadioButton.setSelected(false);
            }
        });

        noRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarButton.setEnabled(false);
                browseButton.setEnabled(false);
                siRadioButton.setSelected(false);
            }
        });

        sortirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = (JOptionPane.showConfirmDialog(null, "Desitja sortir del programa?",
                        "Confimar sortida", JOptionPane.YES_NO_OPTION));
                if (confirm == 0) System.exit(0);
            }
        });

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browse();
            }
        });
    }

    private void browse() {
        JFrame parentFrame = new JFrame();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");


        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV", ".csv"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft excel", ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            textField1.setText(fileToSave.getAbsolutePath());
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
        }
    }
}
