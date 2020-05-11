package vista;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

//Representa cada casilla del juego
public class Casilla extends JButton {

    public boolean esMina;
	public boolean visitado;
	public boolean bandera;
	public boolean detectado;
    public int minas_adyacentes;
    public int index;

    public Casilla(int in) {
        esMina = false;
        minas_adyacentes = 0;
        index = in;
        visitado = false;
        bandera = false;
        detectado = false;
        this.setPreferredSize(new Dimension(25, 25));
    }
    
    public void cambiarimagen(String ruta) {
        ImageIcon icon1 = new ImageIcon(getClass().getResource(ruta));
        Icon icono1 = new ImageIcon(icon1.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT));
        setText(null);
        setIcon(icono1);
    }

}