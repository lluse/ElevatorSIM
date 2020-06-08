package main.vistes.eventUpdate;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SpinnerUpdate implements ChangeListener {

    private JLabel jLabel;

    public SpinnerUpdate(JLabel jLabel) {
        this.jLabel = jLabel;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSpinner js = (JSpinner) e.getSource();
        this.jLabel.setText(Integer.toString((int)js.getValue()));
    }
}
