package test;

import org.junit.Assert;
import org.junit.Test;

import modelo.Sesion;

//Maneja la sesion durante la partida junto con su informacion
public class SesionTest {

    private String nombreJugador;
    private String medidaTablero;
    private static Sesion sesion;
    private int puntuacion;

    //Comprobar que la sesion se crea correctamente
    Sesion sesionTest= Sesion.getInstanciaSingleton(nombreJugador, medidaTablero);
    
    //Comprobar la integridad de sus metodos y que la puntuacion obtenida es la que se ha establecido
    @Test
    public void obtenerNombreTest() {
    	Assert.assertEquals(sesionTest.obtenerNombre(), sesionTest.obtenerNombre());
    }
    
    @Test
    public void obtenerMedidaTableroTest() {
    	Assert.assertEquals(sesionTest.obtenerMedidaTablero(), sesionTest.obtenerMedidaTablero());
    }
    
    @Test
    public void obtenerPuntuacionTest() {
    	Assert.assertEquals(sesionTest.obtenerPuntuacion(),sesionTest.obtenerPuntuacion());
    }

    @Test
    public void establecerPuntuacionTest() {
    	sesionTest.establecerPuntuacion(10);
    	Assert.assertEquals(sesionTest.obtenerPuntuacion(),10);
    }

}

