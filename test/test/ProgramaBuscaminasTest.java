package test;
import java.io.IOException; 
import java.io.InputStream;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import controlador.ProgramaBuscaminas;
import vista.Tablero;

class ProgramaBuscaminasTest {

	
	@Test
	public void testMain() throws IOException {
	    System.out.println("main");
	    String[] args = null;
	    final InputStream original = System.in;
	    Tablero frame = new Tablero();
	    //Test de objeto no es null
		Assert.assertNotNull(frame);
	    frame.setVisible(true);
	    ProgramaBuscaminas.main(args);
	    System.setIn(original);
        boolean resultVisible = frame.isVisible(); 
        //Test de frame está visible
	    Assert.assertTrue(resultVisible);
	}

}
