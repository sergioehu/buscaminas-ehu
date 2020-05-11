package modelo;

//Maneja la sesion durante la partida junto con su informacion
public class Sesion {

    private String nombreJugador;
    private String medidaTablero;
    private static Sesion sesion;
    private int puntuacion;
        
    private	Sesion(String nomJugador,String medTablero) {
    	nombreJugador = nomJugador;
    	medidaTablero = medTablero;
    }
    public static Sesion getInstanciaSingleton(String nomJugador,String medTablero)
    {
    	if (sesion == null) {
    		sesion = new Sesion (nomJugador,medTablero);
    	}
    	else
    	{
    		System.out.println("No se pudo crear el objeto "+ sesion + " porque ya existe un objeto de la clase sesion");
    	}
		return sesion;
    }
    
    public String obtenerNombre() {
    	return nombreJugador;
    }
    
    public String obtenerMedidaTablero() {
    	return medidaTablero;
    }
    
    public int obtenerPuntuacion() {
    	return puntuacion;
    }
    
    public void establecerPuntuacion(int puntos) {
    	puntuacion=puntos;
    }

}

