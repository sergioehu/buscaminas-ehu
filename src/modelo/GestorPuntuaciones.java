package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GestorPuntuaciones {
	private  ArrayList<Puntuacion> puntuaciones;
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