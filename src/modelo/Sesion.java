package modelo;

public class Sesion {

    public String nombreJugador;
    public String medidaTablero;
    public int puntuacion;
        
    public Sesion(String nomJugador,String medTablero) {
    	nombreJugador = nomJugador;
    	medidaTablero = medTablero;
    }
    public Sesion(String nomJugador,String medTablero, int puntos) {
    	nombreJugador = nomJugador;
    	medidaTablero = medTablero;
    	puntuacion = puntos;
    }
    
    public String obtenerNombre() {
    	return nombreJugador;
    }
    
    public String obtenerMedidaTablero() {
    	return medidaTablero;
    }
    
    public String obtenerPuntuacion() {
    	return medidaTablero;
    }
    
    public void establecerPuntuacion(int puntos) {
    	puntuacion=puntos;
    }

}

