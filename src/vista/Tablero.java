package vista;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import modelo.Buscaminas;
import modelo.GestorPuntuaciones;
import modelo.Puntuacion;
import modelo.MedidasTablero;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

// Contiene la informacion del panel de juego
public class Tablero extends JFrame {

	public JPanel contentPane;
	public GridBagLayout gbl_contentPane;
	public JPanel panelVisual, rejilla, panelContadores, panelContador, panelEmoticon, panelCronometro;
	private JMenuBar menuBarInicio;
	private JMenuItem menuItemSalir, menuItemIniciar, menuItemPuntuaciones, menuItemGuardarResultado;
	private JMenu menuArchivo, menuAyuda;
	private Emoticono emoticon;
	private GestorPuntuaciones gesPun;
	private String nombreUsuario;
	private String medidaTableroNivelSeleccionado;
	public Casilla[][] c;
	public JLabel contadorLabel,cronometroLabel;
	TimerTask timer;
	String tiempo = "";
	private int x, y, anchoTablero, altoTablero;
	private Buscaminas logicaJuegoBuscaminas;
	List<Integer> posicion_minas = new ArrayList<>();
	Icon clicado;
	boolean fin, gano;
	Puntuacion puntuacion = null;
	public int n1[] = { 1, 1, 0, -1, -1, -1, 0, 1 };
	public int n2[] = { 0, 1, 1, 1, 0, -1, -1, -1 };
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
		setBounds(300, 100, 500, 507);
		
		// Centrado en la pantalla
		Dimension dimemsion = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dimemsion.width / 2 - this.getSize().width / 2,
				dimemsion.height / 2 - this.getSize().height / 2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 658, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 409, 35, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_panelVisual = new GridBagConstraints();
		gbc_panelVisual.insets = new Insets(0, 0, 5, 0);
		gbc_panelVisual.fill = GridBagConstraints.BOTH;
		gbc_panelVisual.gridx = 0;
		gbc_panelVisual.gridy = 0;
		contentPane.add(obtPanelMenu(), gbc_panelVisual);

	}
	

	// Panel visual donde estará  la barra de menu y el juego
	private JPanel obtPanelMenu() {
		if (panelVisual == null) {
			panelVisual = new JPanel();
			
			GridBagLayout layoutMenu = new GridBagLayout();
			layoutMenu.columnWidths = new int[] { 500, 370, 0, 0 };
			layoutMenu.rowHeights = new int[] { 0, 49, 35, 0 };
			
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.gridheight=3;
			constraints.fill = GridBagConstraints.VERTICAL;
			constraints.gridx = 0;
			constraints.gridy = 0;
			panelVisual.add(obtenerBarInicio(),constraints);
			panelVisual.setLayout(layoutMenu);
			constraints.gridx = 0;
			constraints.gridy = 1;
			panelVisual.add(obtenerBarContadores(),constraints);
			String title = "Buscaminas-singletonsgroup";
			Border border = BorderFactory.createTitledBorder(title);
			panelVisual.setBorder(border);
		}
		return panelVisual;
	}

	// Iniciar la barra de menu
	private JMenuBar obtenerBarInicio() {
		if (menuBarInicio == null) {
			menuBarInicio = new JMenuBar();
			menuArchivo = new JMenu("Archivo");
			menuAyuda = new JMenu("Ayuda");
			menuItemSalir = new JMenuItem("Salir");
			menuItemIniciar = new JMenuItem("Iniciar...");
			menuItemGuardarResultado = new JMenuItem("GuardarResultado");
			menuItemPuntuaciones = new JMenuItem("Puntuaciones");
			menuItemIniciar.addActionListener(e -> presentarTablero());
			menuItemPuntuaciones.addActionListener(e -> mostrarPuntuaciones());
			menuItemGuardarResultado.addActionListener(e -> grabarPuntuacion(nombreUsuario, 0));
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
	
	//Panel de paneles: Contador, Emoticon, Cronometro
	private JPanel obtenerBarContadores() {
		if (panelContadores == null) {
			panelContadores = new JPanel();
			GridBagLayout gbl_contentPanelContadores = new GridBagLayout();
			gbl_contentPanelContadores.columnWidths = new int[] { 350, 0 };
			gbl_contentPanelContadores.rowHeights = new int[] { 0, 0, 0, 0 };
			gbl_contentPanelContadores.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
			gbl_contentPanelContadores.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
			panelContadores.setLayout(gbl_contentPanelContadores);

			//Panel del Contador
			panelContador = new JPanel();
			contadorLabel = new JLabel("0/0");
			panelContador.setName("contador");
			panelContador.add(contadorLabel);
			GridBagConstraints gbc_constraints = new GridBagConstraints();
			gbc_constraints.insets = new Insets(0, 0, 0, 0);
			gbc_constraints.fill = GridBagConstraints.CENTER;
			gbc_constraints.gridx = 1;
			gbc_constraints.gridy = 0;
			panelContadores.add(panelContador,gbc_constraints);
			
			//Panel del Emoticon
			panelEmoticon = new JPanel();
			panelEmoticon.setName("emoticon");
			emoticon = new Emoticono(); 
			panelEmoticon.add(emoticon.emoticon_sonrisa(true));
			gbc_constraints.insets = new Insets(0, 0, 0, 0);
			gbc_constraints.fill = GridBagConstraints.CENTER;
			gbc_constraints.gridx = 2;
			gbc_constraints.gridy = 0;
			panelContadores.add(panelEmoticon,gbc_constraints);
			
			//Panel del Cronometro
			panelCronometro = new JPanel();
			cronometroLabel = new JLabel("0");
			panelCronometro.setName("cronometro");
			panelCronometro.add(cronometroLabel);
			gbc_constraints.insets = new Insets(0, 0, 0, 0);
			gbc_constraints.fill = GridBagConstraints.CENTER;
			gbc_constraints.gridx = 3;
			gbc_constraints.gridy = 0;
			panelContadores.add(panelCronometro,gbc_constraints);
		}
		return panelContadores;
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

		this.elegirTamanoTablero();
		x = altoTablero;
		y = anchoTablero;
		try {
			ImageIcon icon1 = new ImageIcon(getClass().getResource(String.format("/imagenes/boton_clicado.png")));
			Icon icono1 = new ImageIcon(
					icon1.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT));
			clicado = icon1;
		} catch (Exception e) {
			System.out.println(e);
		} 
		logicaJuegoBuscaminas = new Buscaminas(x,y,clicado,tiempo,n1,n2);
		logicaJuegoBuscaminas.grabarNombreEnSesion(nombreUsuario,medidaTableroNivelSeleccionado);
		generarTablero();
	}

	//Generar tablero
	void generarTablero() {
		if (rejilla!=null){
			contentPane.remove(rejilla);
			rejilla=null;
			contentPane.updateUI();
		}
		rejilla = new JPanel();
		if (x == 12) {
			setSize(555, 670);
		} else if (x == 10) {
			setSize(505, 620);
		} else if (x == 7) {
			setSize(505, 620);
		}

		logicaJuegoBuscaminas.reiniciar(x, y, clicado);
		contadorLabel.setText(logicaJuegoBuscaminas.contador.actualizarContador(0));
		panelEmoticon.removeAll();
		panelEmoticon.add(logicaJuegoBuscaminas.emoticono.emoticon_sonrisa(true));

		try {
			actualizarContadorMinas();
			actualizarCronometro();
			actualizarEmoticon();
		}catch(Exception e) {}

		rejilla.setLayout(new GridLayout(x, y));
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				rejilla.add(logicaJuegoBuscaminas.obtCasilla(i, j));
			}
		}

		//Incluir la rejilla con las casillas en el contenedor JPanel 
		GridBagConstraints gbc_panelCasillas = new GridBagConstraints();
		gbc_panelCasillas.insets = new Insets(0, 0, -35, 0);
		gbc_panelCasillas.gridx = 0;
		gbc_panelCasillas.gridy = 1;
		gbc_panelCasillas.fill = GridBagConstraints.BOTH;
		contentPane.add(rejilla, gbc_panelCasillas);
	}
	

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
		
		//Almacenar en sesion nombre de usuario y nivel dedificultad elegido (medida tablero)
		nombreUsuario = JOptionPane.showInputDialog("Escribe tu nombre");		
		medidaTableroNivelSeleccionado=selecionado.toString();
	}
	
	//Actualizar el contador de minas en el Panel 
	public void actualizarContadorMinas() throws InterruptedException {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
	    		contadorLabel.setText(logicaJuegoBuscaminas.contador.actualizarContador(0));
			}
		}, 0, 1000);
	}

	//Actualizar el cronómetro en el Panel 
	public void actualizarCronometro() throws InterruptedException {
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
	    		cronometroLabel.setText(logicaJuegoBuscaminas.cronometro.cron.toString());
			}
		}, 0, 1000);
	}
	
	//Actualizar el emoticon en el Panel 
		public void actualizarEmoticon() throws InterruptedException {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
		    		if (logicaJuegoBuscaminas.fin) {
		    			panelEmoticon.removeAll();
		    			panelEmoticon.add(logicaJuegoBuscaminas.emoticono.emoticon_sonrisa(false));
		    			contentPane.updateUI();
		    		}
		    		
				}
			}, 0, 1000);
		}

}