package controlador;

import java.awt.EventQueue;

import modelo.Contador;
import modelo.Puntuacion;
import vista.Tablero;
import vista.Casilla;

/**
 * Incia la aplicacion.
 */
public class ProgramaBuscaminas {
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tablero frame = new Tablero();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		});
	
	}

}