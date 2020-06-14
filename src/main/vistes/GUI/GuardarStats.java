package main.vistes.GUI;

import main.Statistics.GenerateCSV;
import main.Statistics.GenerateXLSX;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GuardarStats {
    private JRadioButton siRadioButton;
    private JRadioButton noRadioButton;
    private JTextField textField1;
    private JButton browseButton;
    private JButton guardarButton;
    private JButton sortirButton;

    public JPanel panel_stats;

    private String path;

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

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (path != null) {
                    if (path.endsWith(".csv")) {
                        GenerateCSV csv = new GenerateCSV();
                        csv.generateCSV(path);
                        JOptionPane.showMessageDialog(null,"S'ha guardat el fitxer correctament",
                                "Guardar fitxer", JOptionPane.OK_OPTION);
                    } else if (path.endsWith(".xlsx")) {
                        GenerateXLSX xlsx = new GenerateXLSX();
                        try {
                            xlsx.generarExcel(path);
                            JOptionPane.showMessageDialog(null,"S'ha guardat el fitxer correctament",
                                    "Guardar fitxer", JOptionPane.OK_OPTION);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (InvalidFormatException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void browse() {
        JFrame parentFrame = new JFrame();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.getName().endsWith(".csv")) return true;
                else if (f.getName().endsWith(".xlsx")) return true;
                else if (f.getName().endsWith(".xls")) return true;
                return false;
            }

            @Override
            public String getDescription() {
                return "Microsoft excel";
            }
        });

        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filter = fileChooser.getFileFilter().toString();

            path = fileToSave.getAbsolutePath();
            if (!(path.endsWith(".csv") || path.endsWith(".xlsx") || path.endsWith(".xls")))
                path += ".csv";

            textField1.setText(path);
            System.out.println("Save as file: " + path);
        }
    }
}
