package vista;

import javax.swing.JLabel;

public class Cronometro extends Thread implements Runnable {

    JLabel cron;

    public Cronometro(JLabel r2) {
        cron = r2;
    }

    public void run() {
        try {
            for (int j = 0; j < 1000; j++) {
                cron.setText("" + j);
                Thread.sleep(1000);
            }
            cron.setText("Tiempo infinito");
        } catch (InterruptedException ex) {
            System.out.println(Cronometro.class.getName());
        }
    }
}
