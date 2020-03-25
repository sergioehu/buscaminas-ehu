package modelo;

import java.util.ArrayList; 

import javax.swing.Icon;
import javax.swing.JLabel;

import vista.Casilla;

public class Buscaminas {

	ArrayList<Integer> posicion_minas = new ArrayList<>();
	int can_minas=10;
	Cronometro cronometro;
	boolean fin, gano;
	
	
	//Constructor de la cLase
	public Buscaminas() { 
		
	}

	//reiniciar partida
    public void reiniciar(int x, int y, Casilla[][] c, Icon clicado) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                c[i][j].setVisible(true);
                c[i][j].setBorderPainted(true);
                c[i][j].setContentAreaFilled(true);
                c[i][j].esMina=false;
                c[i][j].setIcon(clicado);
                c[i][j].setEnabled(true);
                c[i][j].visitado = false;
                c[i][j].bandera = false;
                c[i][j].detectado = false;
            }
        }
        crearminas(x,y,c);
        //cronometro.stop();

        //minas.setText("" + can_minas);
        fin = false;
        gano = false;
    }
    
    
    private void crearminas(int x, int y, Casilla[][] c) {
    	posicion_minas.clear();
        int s;
        for (int i = 0; i < can_minas; i++) {
            s = (int) (Math.random() * (x * y));
            if (posicion_minas.contains(s)) {
                i--;
            } else {
            	posicion_minas.add(s);
                c[s / x][s % y].esMina = true;
            }
        }
    }


	//Cambiar imagen de la casilla
	public void cambiar(int q, int w, Casilla[][] c) {
		c[q][w].cambiarimagen("/imagenes/n_" + c[q][w].minas_adyacentes + ".png");
		c[q][w].setBorderPainted(false);
		c[q][w].setContentAreaFilled(false);
		c[q][w].enable(false);
		c[q][w].detectado = true;
	}
	
	//Juego acaba perdiendo
	public void fin_juego(int x, int y, Casilla[][] c) {
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				if (c[i][j].detectado) {
					continue;
				}
				if (c[i][j].esMina) {
					if (c[i][j].bandera) {
						c[i][j].cambiarimagen("/imagenes/mina_2.png");
					} else {
						c[i][j].cambiarimagen("/imagenes/mina123.png");
					}
					c[i][j].setBorderPainted(false);
					c[i][j].setContentAreaFilled(false);
					continue;
				}
				if (c[i][j].minas_adyacentes > 0) {
					cambiar(i, j,c);
					continue;
				}
				c[i][j].setVisible(false);
				c[i][j].setEnabled(false);
			}
		}
		if (!fin) {
			// if (act_sonido.isSelected()) {
			// son.pierde();
			// }
			fin = true;
		}
	}
	
    //Juego acaba ganando
	public boolean gana(int x, int y, Casilla[][] c,JLabel minas) {
		if (!minas.getText().equals("0")) {
			return false;
		}
		if (gano) {
			return false;
		}
		for (int i = 0; i < x; i++) {
			for (int q = 0; q < y; q++) {
				if (c[i][q].detectado || !c[i][q].isVisible() || c[i][q].bandera) {
					continue;
				}
				return false;
			}
		}
		// if (act_sonido.isSelected()) {
		// son.gana();
		// }

		// c.stop();
		try {
			// puntuacion.agregar(n, Integer.parseInt(tiempo.getText()));
		} catch (Exception ex) {
		}
		return true;
	}
	
	public void motor(int fila, int columna,int x, int y, Casilla[][] c,int n1[],int n2[]) {
		if (!(fila > -1 && fila < x && columna > -1 && columna < y) || c[fila][columna].visitado) {
			return;
		}
		if (c[fila][columna].esMina) {
			return;
		}
		if (c[fila][columna].minas_adyacentes > 0) {
			//cambiar(fila, columna);
			return;
		}
		
		c[fila][columna].setVisible(false);
		c[fila][columna].visitado = true;
		c[fila][columna].detectado = true;
		for (int i = 0; i < 8; i++) {
			motor(fila + n1[i], columna + n2[i],x,y, c,n1,n2 );
		}
	}

	
	
}

