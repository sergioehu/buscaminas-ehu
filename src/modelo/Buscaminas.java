package modelo;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import vista.Casilla;

public class Buscaminas {

	List<Integer> posicion_minas = new ArrayList<>();
	int can_minas=10;
	boolean fin, gano;
	private Casilla[][] c;
	Cronometro cronometro;
	
	//Constructor de la clase
	public Buscaminas(int x, int y, Icon clicado, JLabel minas, JLabel tiempo, int[]n1, int[]n2) { 
		cronometro = new Cronometro(tiempo);
		c = new Casilla[x][y];		
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				c[i][j] = new Casilla(i);
				final int y2 = i;
				final int y3 = j;

				c[i][j].addMouseListener(new java.awt.event.MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (c[y2][y3].detectado || gano || fin) {
							return;
						}

						if (e.getButton() == MouseEvent.BUTTON3) {
							if (c[y2][y3].bandera) {
								c[y2][y3].setIcon(clicado);
								c[y2][y3].setEnabled(true);
								c[y2][y3].bandera = false;
								minas.setText((Integer.parseInt(minas.getText()) + 1) + "");
							} else {
								c[y2][y3].cambiarimagen("/imagenes/bandera.png");
								c[y2][y3].setEnabled(false);
								c[y2][y3].bandera = true;
								minas.setText((Integer.parseInt(minas.getText()) - 1) + "");
							}
							if (gana(x, y, c, minas)) {
								gano = true;
								JOptionPane.showMessageDialog(null, "Victoria");
							}
						}
					}

					@Override
					public void mousePressed(MouseEvent e) {
						System.out.print(e.getButton());
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

				});

				c[i][j].addActionListener(new java.awt.event.ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (gano || fin) {
							return;
						}
						if (!gano) {
							if (!cronometro.isAlive()) {
								cronometro = new Cronometro(tiempo);
								cronometro.start();
							}
						}
						if (c[y2][y3].esMina) {
							c[y2][y3].cambiarimagen("/imagenes/mina123.png");
							c[y2][y3].setBorderPainted(false);
							c[y2][y3].setContentAreaFilled(false);
							//emoticon_sonrisa(false);
							cronometro.stop();
							fin_juego(x, y, c);
						} else {
							motor(y2, y3, x, y, c, n1, n2);
							c[y2][y3].detectado = true;
							if (gana(x, y, c, minas)) {
								gano = true;
								JOptionPane.showMessageDialog(null, "Victoria");
							}
						}

					}
				});
			}
		}
	}

	public Casilla obtCasilla(int x,int y)
	{
		return c[x][y];
	}
	//reiniciar partida
    public void reiniciar(int x, int y, Icon clicado) {
    	
	    //Inicializar las casillas, bucles modernos Java 8 con stream
		IntStream.range(0, x).forEach(posFila->{
											IntStream.range(0, y).forEach(posColumna->{
																					c[posFila][posColumna].setVisible(true);
																					c[posFila][posColumna].setBorderPainted(true);
																					c[posFila][posColumna].setContentAreaFilled(true);
																					c[posFila][posColumna].esMina=false;
																					c[posFila][posColumna].setIcon(clicado);
																					c[posFila][posColumna].setEnabled(true);
																					c[posFila][posColumna].visitado = false;
																					c[posFila][posColumna].bandera = false;
																					c[posFila][posColumna].detectado = false;
																				     }); 
												});
	    /*	//Antigua forma de hacer bucles
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
	        */
    	
    	
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

