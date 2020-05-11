package modelo;

//Control del contador de tiempo
public class Cronometro extends Thread implements Runnable {

    public String cron;

    public Cronometro(String r2) {
        cron = r2;
    }

    public void run() {
        try {
            for (int j = 0; j < 1000; j++) {
                cron="" + j;
                Thread.sleep(1000);
            }
            cron="Tiempo infinito";
        } catch (InterruptedException ex) {
            System.out.println(Cronometro.class.getName());
        }
    }
    public int obtTiempo() {
    	System.out.println(Integer.parseInt(cron));
    	return Integer.parseInt(cron);
    }
}
