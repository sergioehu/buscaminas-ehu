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
import vista.Emoticono;

//Clase buscaminas encargada de la funcionalidad principal 
public class Buscaminas {

	List<Integer> posicion_minas = new ArrayList<>();
	int can_minas=9;
	public boolean fin, gano;
	private Casilla[][] c;
	public Emoticono emoticono;
	public Cronometro cronometro;
	public Contador contador;
	Sesion sesion;
	int minas=0;
	
	//Constructor de la clase
	public Buscaminas(int x, int y, Icon clicado,String tiempo, int[]n1, int[]n2) { 
		emoticono=new Emoticono();
		cronometro = new Cronometro(tiempo);
		contador = new Contador(can_minas);
		sesion=null; 
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
								contador.actualizarContador(minas-1);
							} else {
								c[y2][y3].cambiarimagen("/imagenes/bandera.png");
								c[y2][y3].setEnabled(false);
								c[y2][y3].bandera = true;
								contador.actualizarContador(minas+1);
								
							}
							if (gana(x, y, c, contador.obtConteo())) {
								gano = true;
								sesion.establecerPuntuacion(1000-cronometro.obtTiempo());
								grabarPuntuacion();
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
							fin_juego(x, y, c);
						} else {
							motor(y2, y3, x, y, c, n1, n2);
							c[y2][y3].detectado = true;
							if (gana(x, y, c, contador.obtConteo())) {
								gano = true;
								JOptionPane.showMessageDialog(null, "Victoria");
							}
						}

					}
				});
			}
		}
	}

	public void grabarNombreEnSesion(String nombreUsuario,String medTablero) {
		sesion=null;
		sesion = Sesion.getInstanciaSingleton(nombreUsuario, medTablero);
	}
	// Grabar puntuacion
	private void grabarPuntuacion() {
		GestorPuntuaciones gesPun = new GestorPuntuaciones();
		gesPun.anadirPuntuacionDeUsuarioEnXML(sesion.obtenerNombre(), sesion.obtenerPuntuacion());
		sesion=null;
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
        crearadyacentes(x,y);
        fin = false;
        gano = false;
    }
    
    private void crearadyacentes(int x, int y){
    	System.out.println("x: "+x+" y: "+y);
    	for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				if (c[i][j].esMina) {
					
                    ///   Adyacentes	
					///	
			    	///   3    5   8
			    	///   2  mina  7
			    	///   1    4   6
			    	
			    	///  1
					if (i-1>-1&j-1>-1)						
					
						c[i-1][j-1].minas_adyacentes=c[i-1][j-1].minas_adyacentes+1; 
			    	///  2
					if (j-1>-1)
						
						c[i][j-1].minas_adyacentes=c[i][j-1].minas_adyacentes+1;
			    	///  3	
					if (i+1<x&j-1>-1)
						
						c[i+1][j-1].minas_adyacentes=c[i+1][j-1].minas_adyacentes+1; 
			    	///  4
					if (i+1<x)
						
						c[i+1][j].minas_adyacentes=c[i+1][j].minas_adyacentes+1;
			    	///  5
					if (i+1<x&j+1<y)
						
						c[i+1][j+1].minas_adyacentes=c[i+1][j+1].minas_adyacentes+1;
			    	///  6
					if (j+1<y)
						
						c[i][j+1].minas_adyacentes=c[i][j+1].minas_adyacentes+1;
			    	///  7
					if (i-1>-1&j+1<y) {
						c[i-1][j+1].minas_adyacentes=c[i-1][j+1].minas_adyacentes+1;
					}
			    	///  8					
					if (i-1>-1)
						
						c[i-1][j].minas_adyacentes=c[i-1][j].minas_adyacentes+1;
				} 
				
			}
		}
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
	public Emoticono obtEmoticono() {
		return emoticono;
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
			fin = true;
			sesion=null;
		}
	}
	
    //Juego acaba ganando
	public boolean gana(int x, int y, Casilla[][] c,int minas) {
		if (minas==0) {
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
				cambiar(fila, columna,c);
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

