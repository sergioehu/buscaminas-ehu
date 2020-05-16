package test;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import modelo.Contador;


import java.util.Arrays;
import java.util.Collection;

public class ContadorTest {
	
	
	int minasContadas;
	Contador ct;
	int minasTotales;
	
	public ContadorTest() {
		minasTotales=9;
		ct = new Contador(minasTotales);
		int numMinasInicial = ct.obtConteo();

		//Comrpobar el contador de minas no contiene valores negativos
		Assert.assertNotEquals(numMinasInicial, -1);
		Assert.assertNotEquals(numMinasInicial, -2);
		Assert.assertNotEquals(numMinasInicial, -3);
		Assert.assertTrue(numMinasInicial>-1);
		
	}
	
    // crea los datos de test
    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] { { 1 , 2 }, { 5, 3 }, { 121, 4 } };
        return Arrays.asList(data);
    }
	
	//Comprobar el contador suma de uno en uno
	@Test
	public void actualizarContadorTest()
	{
		ct = new Contador(9);
		ct.actualizarContador(1);
		Assert.assertNotEquals(ct.obtConteo(), -2);
		Assert.assertNotEquals(ct.obtConteo(), 2);
		Assert.assertNotEquals(ct.obtConteo(), 0);

	}

	//Comprobar que obtConteo tiene valor inicial cero, y cuenta correctamente
	@Test
	public void obtConteoTest() {
		ct = new Contador(9);
		int i=1;
		ct.actualizarContador(i);
		Assert.assertEquals(ct.obtConteo(), i);
	}
	 
	//Comprobar funciona el contador incrementando
	@Test
	public void testContadorIncrementar() {
		ct = new Contador(9);
		Assert.assertEquals(ct.obtConteo(), 0);
		ct.actualizarContador(1);
		Assert.assertEquals(ct.obtConteo(), 1);
	}

	//Comprobar funciona el contador decrementando
	@Test
	public void testContadorDecrementar() {
		ct = new Contador(9);
		ct.actualizarContador(1);
		ct.actualizarContador(-1);
		Assert.assertEquals(ct.obtConteo(), 0);
	}
	
}

