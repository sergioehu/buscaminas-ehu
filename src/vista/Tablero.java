package vista;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import modelo.Buscaminas;
import modelo.Cronometro;
import modelo.GestorPuntuaciones;
import modelo.Puntuacion;
import modelo.Sesion;
import modelo.MedidasTablero;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

public class Tablero extends JFrame {

	public JPanel contentPane;
	public JPanel panelVisual, rejilla;
	private JMenuBar menuBarInicio;
	private JMenuItem menuItemSalir, menuItemIniciar, menuItemPuntuaciones, menuItemGuardarResultado;
	private JMenu menuArchivo, menuAyuda;
	//private MedidaTablero medTablero;
	private GestorPuntuaciones gesPun;
	private Casilla[][] c;
	JButton emoticon = new JButton();
	JLabel tiempo = new JLabel();
	JLabel minas = new JLabel("10");
	private String medida_7x10, medida_10x15, medida_12x25;
	private int x, y, anchoTablero, altoTablero;
	private Buscaminas logicaJuegoBuscaminas;
	ArrayList<Integer> posicion_minas = new ArrayList<>();
	Icon clicado;
	boolean fin, gano;
	Puntuacion puntuacion = null;
	Cronometro cronometro = new Cronometro(tiempo);
	int n1[] = { 1, 1, 0, -1, -1, -1, 0, 1 };
	int n2[] = { 0, 1, 1, 1, 0, -1, -1, -1 };
	int n3[] = { 1, 0, -1, -1, 0, 0, 1, 1 };
	int n4[] = { 0, 1, 0, 0, -1, -1, 0, 0 };
	int can_minas = 10;

	/**
	 * Crear el Frame.
	 */
	public Tablero() {
		initialize();
	}

	private void initialize() {
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(300, 100, 674, 507);
		// Center the screen
		Dimension dimemsion = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dimemsion.width / 2 - this.getSize().width / 2,
				dimemsion.height / 2 - this.getSize().height / 2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 658, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 409, 35, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_panelVisual = new GridBagConstraints();
		gbc_panelVisual.insets = new Insets(0, 0, 5, 0);
		gbc_panelVisual.fill = GridBagConstraints.BOTH;
		gbc_panelVisual.gridx = 0;
		gbc_panelVisual.gridy = 1;
		contentPane.add(getPanelVisual(), gbc_panelVisual);
	}

	// Panel visual donde estará el la barra de menu y el juego
	private JPanel getPanelVisual() {
		if (panelVisual == null) {
			panelVisual = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelVisual.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panelVisual.add(obtenerBarInico());
		}
		return panelVisual;
	}

	// Iniciar la barra de menu
	private JMenuBar obtenerBarInico() {
		if (menuBarInicio == null) {
			menuBarInicio = new JMenuBar();
			menuBarInicio.setSize(new Dimension(100, 200));
			menuArchivo = new JMenu("Archivo");
			menuAyuda = new JMenu("Ayuda");
			menuItemSalir = new JMenuItem("Salir");
			menuItemIniciar = new JMenuItem("Iniciar...");
			menuItemGuardarResultado = new JMenuItem("GuardarResultado");
			menuItemPuntuaciones = new JMenuItem("Puntuaciones");
			menuItemIniciar.addActionListener(e -> presentarTablero());
			menuItemPuntuaciones.addActionListener(e -> mostrarPuntuaciones());
			menuItemGuardarResultado.addActionListener(e -> grabarPuntuacion("Jon", 300));
			menuItemSalir.addActionListener(e -> this.dispose());
			menuArchivo.add(menuItemIniciar);
			menuArchivo.add(menuItemPuntuaciones);
			menuArchivo.add(menuItemGuardarResultado);
			menuArchivo.add(menuItemSalir);
			menuBarInicio.add(menuArchivo);
			menuBarInicio.add(menuAyuda);

		}
		return menuBarInicio;
	}

	// Mostrar puntuaciones
	private void mostrarPuntuaciones() {
		gesPun = new GestorPuntuaciones();
		JOptionPane.showMessageDialog(panelVisual, gesPun.obtPuntuacionesDesdeXMLEnTabla());
	}

	// Grabar puntuacion
	private void grabarPuntuacion(String nombre, int puntuacionValor) {
		gesPun = new GestorPuntuaciones();
		gesPun.anadirPuntuacionDeUsuarioEnXML(nombre, puntuacionValor);
	}

	// Mostrar el tablero
	private void presentarTablero() {

		// emoticon.setSize(40, 40);
		// emoticon.setLocation(180, 35);
		// emoticon_sonrisa(true);
		logicaJuegoBuscaminas = new Buscaminas();
		//medTablero = new MedidaTablero();
		this.elegirTamanoTablero();
		x = altoTablero;
		y = anchoTablero;
		// Crear Casillas
		c = new Casilla[x][y];
		try {
			ImageIcon icon1 = new ImageIcon(getClass().getResource(String.format("/imagenes/boton_clicado.png")));
			Icon icono1 = new ImageIcon(
					icon1.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT));
			clicado = icon1;
		} catch (Exception e) {
			System.out.println(e);
		}
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
							if (logicaJuegoBuscaminas.gana(x, y, c, minas)) {
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
							emoticon_sonrisa(false);
							cronometro.stop();
							logicaJuegoBuscaminas.fin_juego(x, y, c);
						} else {
							logicaJuegoBuscaminas.motor(y2, y3, x, y, c, n1, n2);
							c[y2][y3].detectado = true;
							if (logicaJuegoBuscaminas.gana(x, y, c, minas)) {
								gano = true;
								JOptionPane.showMessageDialog(null, "Victoria");
							}
						}

					}
				});
			}

		}
		generarTablero();
		panelVisual.add(rejilla, panelVisual);
	}

	// generar tablero
	void generarTablero() {

		rejilla = new JPanel();
		if (x == 12) {
			setSize(555, 670);
			rejilla.setSize(525, 525);
			// barra.setBounds(0, 0, 555, 25);
			// carita.setLocation(250, 35);
			// tiempo.setLocation(440, 35);
			// minas.setLocation(50, 35);
		} else if (x == 10) {
			setSize(505, 620);
			rejilla.setSize(475, 475);
			// barra.setBounds(0, 0, 505, 25);
			// carita.setLocation(225, 35);
			// tiempo.setLocation(400, 35);
			// minas.setLocation(50, 35);
		} else if (x == 7) {
			setSize(405, 520);
			rejilla.setSize(375, 375);
			// barra.setBounds(0, 0, 405, 25);
			// carita.setLocation(180, 35);
			// tiempo.setLocation(310, 35);
			// minas.setLocation(20, 35);
		}

		logicaJuegoBuscaminas.reiniciar(x, y, c, clicado);
		emoticon_sonrisa(true);
		// logicaJuegoBuscaminas.crearminas();
		// logicaJuegoBuscaminas.gana();
		// logicaJuegoBuscaminas.fin_juego();
		// logicaJuegoBuscaminas.cambiar();
		// logicaJuegoBuscaminas.motor();

		// reiniciar();
		rejilla.setLayout(new GridLayout(x, y));
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				rejilla.add(c[i][j]);
			}
		}

	}

	// establecer medida tablero
	/*private class MedidaTablero {

		private MedidaTablero() {
			medida_7x10 = "7x10 (Nivel 1)";
			medida_10x15 = "10x15 (Nivel 2)";
			medida_12x25 = "12x25 (Nivel 3)";
		}

		private void elegirTamanoTablero() {
			Object[] opciones = { medida_7x10, medida_10x15, medida_12x25 };
			Object selecionado = JOptionPane.showInputDialog(null, "Elige tamano de tablero", "Tablero",
					JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

			anchoTablero = Integer.parseInt(selecionado.toString().split(" ")[0].split("x")[0]);
			altoTablero = Integer.parseInt(selecionado.toString().split(" ")[0].split("x")[1]);
			String nombreUsuario = JOptionPane.showInputDialog("Escribe tu nombre");
			Sesion sesion = Sesion.obtInstanciaSingleton(nombreUsuario, selecionado.toString());

		}
	}*/
	
	
	// establecer medida tablero utilizando el enum
	private void elegirTamanoTablero() {
		
		MedidasTablero medida_1 = MedidasTablero.medida1;
		MedidasTablero medida_2 = MedidasTablero.medida2;
		MedidasTablero medida_3 = MedidasTablero.medida3;
		
		Object[] opciones = {medida_1.getMedidas() + medida_1.getNombre(), medida_2.getMedidas() + medida_2.getNombre(), medida_3.getMedidas() + medida_3.getNombre()};
		
		Object selecionado = JOptionPane.showInputDialog(null, "Elige tamano de tablero", "Tablero",
				JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

		anchoTablero = Integer.parseInt(selecionado.toString().split(" ")[0].split("x")[0]);
		altoTablero = Integer.parseInt(selecionado.toString().split(" ")[0].split("x")[1]);
		String nombreUsuario = JOptionPane.showInputDialog("Escribe tu nombre");
		Sesion sesion = Sesion.getInstanciaSingleton(nombreUsuario, selecionado.toString());

	}
	
	
	
	
	
	// mostrar emoticono smile
	void emoticon_sonrisa(boolean x) {
		String pathSonrisaq;
		if (x) {
			pathSonrisaq = "/imagenes/cara_si.jpg";
		} else {
			pathSonrisaq = "/imagenes/cara_no.jpg";
		}
		try {
			ImageIcon icon1 = new ImageIcon(getClass().getResource(pathSonrisaq));
			Icon icono1 = new ImageIcon(
					icon1.getImage().getScaledInstance(emoticon.getWidth(), emoticon.getHeight(), Image.SCALE_DEFAULT));

			emoticon.setText(null);
			emoticon.setIcon(icono1);
		} catch (Exception e) {
			System.out.print(e);
		}
	}

}