package main.Models;

import main.vistes.GUI.Simulacio;

public class RellotgeDinamic {

    private int hora, minuts, segons;
    Thread hl;

    public RellotgeDinamic(int hora, int minuts, int segons) {
        this.hora = hora;
        this.minuts = minuts;
        this.segons = segons;
        //hl = new Thread(this);
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuts() {
        return minuts;
    }

    public void setMinuts(int minuts) {
        this.minuts = minuts;
    }

    public int getSegons() {
        return segons;
    }

    public void setSegons(int segons) {
        this.segons = segons;
    }

    public Thread getHl() {
        return hl;
    }

    public String incrementar() {
        segons++;
        if (segons == 60) {
            segons = 0;
            minuts++;
        } if (minuts == 60) {
            minuts = 0;
            hora = (hora+1)%24;
        }
        return toString();
    }

    @Override
    public String toString() {
        String hora = (this.hora < 10) ? "0" + this.hora : String.valueOf(this.hora);
        String minuts = (this.minuts < 10) ? "0" + this.minuts : String.valueOf(this.minuts);
        String segons = (this.segons < 10) ? "0" + this.segons : String.valueOf(this.segons);
        return hora + ":" + minuts + ":" + segons;
    }
/*
    @Override
    public void run() {
        Thread ct = Thread.currentThread();
        while (ct == hl) {
            incrementar();
            System.out.println(Simulacio.getInstance().lblRellotge.getText());
            Simulacio.getInstance().lblRellotge.setText(toString());
            //System.out.println(toString());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

 */
}
