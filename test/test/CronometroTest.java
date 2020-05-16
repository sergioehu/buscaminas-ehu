package test;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import modelo.Cronometro;

@RunWith(Parameterized.class)
//Control del contador de tiempo
public class CronometroTest extends Thread implements Runnable {
	
	public String cron;

    // crea los datos de test
    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] { { "1"}, { "" }, { "0" } };
        return Arrays.asList(data);
    } 
	
    //Comprobar que el valor inicial del cronómetro es 0
	@Test
	public void initialize() {
	  CronometroTest cronometroTest = new CronometroTest("0");
	  Assert.assertEquals(cronometroTest.obtTiempoTest(), 0);
	  //Comprobar que el cronometro funciona
	  cronometroTest.run();
	}

	public CronometroTest(String r2) {
        cron = r2;
    }
	@Test
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

    public int obtTiempoTest() {
    	System.out.println(Integer.parseInt(cron));
    	return Integer.parseInt(cron);
    }
}
