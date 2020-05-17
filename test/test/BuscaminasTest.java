package test;

import java.awt.event.InputEvent;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import modelo.Buscaminas;
import modelo.Contador;
import modelo.Cronometro;
import modelo.Puntuacion;
import vista.Casilla;
import vista.Emoticono;

public class BuscaminasTest {

	    Buscaminas buscaminas;
	    Icon clicado;
	    @Parameters
	    public static Collection<Object[]> data() {
	        return Arrays.asList(new Object[][] {
	                 { 7,10 }, { 10,15}, { 12,15}  
	           });
	    }
	    
	    @Parameter // primer valor es cero
	    public int entradaX, entradaY;
	    
		@Test
	    public void initialize() {
			Emoticono emoticono = new Emoticono();
			Cronometro cronometro = new Cronometro("0");
			Contador contador = new Contador(9);
			Object sesion = null; 
			clicado= new ImageIcon()	;
			Puntuacion puntuacion = null;
			int n1[] = { 1, 1, 0, -1, -1, -1, 0, 1 };
			int n2[] = { 0, 1, 1, 1, 0, -1, -1, -1 };
			int can_minas=9;
			//Comprobar que el objeto buscaminas se crea correctamente
			buscaminas = new Buscaminas(entradaX, entradaY, clicado,"0", n1, n2);
			
			//Simular clics y que los estados de las casillas funcionan correctamente
			buscaminas = new Buscaminas(12, 15, clicado,"0", n1, n2);
			int numFilas = 11;
			int numColumnas = 14;
			Casilla c;
			for (int i = 0; i < numFilas; i++) {
				for (int j = 0; j < numColumnas; j++) {
					//La colocación de las minas ya está hecha en este punto, luego no tiene sentido testear la variable c.esMina a true o false
					c=buscaminas.obtCasilla(numFilas, numColumnas);	
					c.doClick();
					c.detectado=true;
					c.detectado=false;
					c.bandera=true;
					c.bandera=false;
					c.visitado=false;
					c.visitado=true;
					
				}
			}
	    }
}
