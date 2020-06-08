package main.vistes.eventUpdate;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderUpdate implements ChangeListener {

    private JLabel label;

    public SliderUpdate(JLabel label) {this.label = label;}

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider js = (JSlider) e.getSource();
        this.label.setText(Integer.toString(js.getValue()));
    }
}
