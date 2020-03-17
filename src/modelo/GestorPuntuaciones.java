package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vista.Puntuacion;

public class GestorPuntuaciones {
	private  ArrayList<Puntuacion> puntuaciones;
	private  JTable puntuacionesTabla;
	private  DefaultTableModel model;
	private  Puntuacion s;

	public GestorPuntuaciones(){
		puntuaciones = new ArrayList<>();
		puntuaciones = obtPuntuaciones();
	}
    public ArrayList<Puntuacion> obtPuntuaciones(){
    	for (int i = 0; i < 5; i++) {
            java.util.Random rand = new java.util.Random();
            s = new Puntuacion("Usuario " + i, rand.nextInt(99999));
            puntuaciones.add(s);
        }

        Collections.sort(puntuaciones, new Comparator<Puntuacion>() {
            public int compare(Puntuacion o1, Puntuacion o2) {
                return Integer.compare(o1.obtPuntuacion(), o2.obtPuntuacion());
            }
        });
        
        //Collections.reverse(puntuaciones);
        //ParserPuntuacionXml.ParserPuntuacionXml();
        return puntuaciones;
    }
 
    public JTable obtPuntuacionesEnTabla(){
    	model = new DefaultTableModel(); 
    	model.addColumn("Usuario");
    	model.addColumn("Puntuacion");
    	puntuaciones.forEach( x -> model.addRow(new Object[]{x.obtNombre(), x.obtPuntuacion()}) );            
       	puntuacionesTabla = new JTable(model);

        Collections.sort(puntuaciones, new Comparator<Puntuacion>() {
            public int compare(Puntuacion o1, Puntuacion o2) {
                return Integer.compare(o1.obtPuntuacion(), o2.obtPuntuacion());
            }
        });
        System.out.println("Prueba leer xml: ");
        return puntuacionesTabla;
        //return puntuacionesTabla;

        //System.out.println("La más alta puntuacion: " + puntuaciones.get(0)() + " con  " + puntuaciones.get(0).getScore());
        //System.out.println("La más alta puntuacion: " + puntuaciones.get(1).getName() + " con " + puntuaciones.get(1).getScore());
        //puntuaciones.forEach( x -> System.out.println("Nombre: " + x.obtNombre() + " con puntuación: " + x.obtPuntuacion()) );
    	
    }
    
    private JTable leerXML() {
    	ParserPuntuacionXml parserPuntuacionXml= new ParserPuntuacionXml();
    	return parserPuntuacionXml.leerPuntuacion();
    	
    }
    private void escribirXML() {
    	
    }
    
}
