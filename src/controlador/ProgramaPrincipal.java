package controlador;

import java.awt.EventQueue;


import vista.Tablero;
import vista.Casilla;
import vista.Contador;
import vista.Puntuacion;

/**
 * Launch the application.
 */
public class ProgramaPrincipal {
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