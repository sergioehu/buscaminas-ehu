package test;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.JButton;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import vista.Casilla;

@RunWith(Parameterized.class)
public class CasillaTest extends JButton {


    public boolean esMina;
	public boolean visitado;
	public boolean bandera;
	public boolean detectado;
    public int minas_adyacentes;
    public int index;
    Casilla casilla;
    
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                 { 0 }, { 1}, { 20}, { 40 },{ 70 }, { 100}, { 150}, { 200}, { 1000}  
           });
    }
    
    @Parameter // primer valor es cero
    public int entrada;
    
	@Before
    public void initialize() {
    	casilla = new Casilla(entrada);
    	casillaTest();
    }
	
	@Test
    public void casillaTest() {
    	//Comprobar que la casilla inicialmente no es mina
    	Assert.assertFalse(casilla.esMina);
    	//Comprobar que la casilla inicialmente no tiene casillas adyacentes
    	Assert.assertEquals(casilla.minas_adyacentes, 0);
    	//Comprobar que la casilla inicialmente no tiene valor visitado=true
    	Assert.assertFalse(casilla.visitado);
    	//Comprobar que la casilla inicialmente no tiene bandera=false
    	Assert.assertFalse(casilla.bandera);
    	//Comprobar que la casilla inicialmente no tiene valor detectado=false
    	Assert.assertFalse(casilla.detectado);
    	//Comprobar que la casilla inicialmente tiene tamano 25x25
    	Assert.assertEquals(casilla.getPreferredSize(),new Dimension(25,25));
    }
  
}