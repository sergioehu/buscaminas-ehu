package vista;

import java.awt.Color;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;

//Mostrar emoticono smile, sonrisa cuando juega o gana, triste cuando pierde la partida
public class Emoticono {
	JButton emoticon;
	public Emoticono() {
		emoticon = new JButton();
	}
	
	public JButton emoticon_sonrisa(boolean x) {
		String pathSonrisaq;
		if (x) {
			pathSonrisaq = "/imagenes/cara_si.png";
		} else {
			pathSonrisaq = "/imagenes/cara_no.png";
		}
		try {
			ImageIcon icon1 = new ImageIcon(getClass().getResource(pathSonrisaq));
			Icon icono1 = new ImageIcon(icon1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
			emoticon.setText(null);
			emoticon.setIcon(icono1);
			emoticon.setBackground(new Color(0,0,0,0));
			Border emptyBorder = BorderFactory.createEmptyBorder();
			emoticon.setBorder(emptyBorder);
			
		} catch (Exception e) {
			System.out.print(e);
		}
		return emoticon;
	}
}
