package modelo;

import java.util.ArrayList; 
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//Realiza el proceso de grabacion de puntuaciones en el XML a traves de la clase ParserPuntuacionXml
public class GestorPuntuaciones {
	private  List<Puntuacion> puntuaciones;
	private  JTable puntuacionesTabla;
	private  DefaultTableModel model;

	public GestorPuntuaciones(){
		puntuaciones = new ArrayList<>();
	}
    
    public JTable obtPuntuacionesDesdeXMLEnTabla() {
    	ParserPuntuacionXml parserPuntuacionXml= new ParserPuntuacionXml();
    	return parserPuntuacionXml.leerPuntuacion();
    	
    }

    public void anadirPuntuacionDeUsuarioEnXML(String nombre, int resultado ) {
         //TODO
    	ParserPuntuacionXml parserPuntuacionXml= new ParserPuntuacionXml();
    	parserPuntuacionXml.anadirPuntuacion(nombre, String.valueOf(resultado));
    }
    
}