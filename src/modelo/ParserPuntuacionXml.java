package modelo;

import java.io.File; 
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
 
public class ParserPuntuacionXml 
{
	
	ParserPuntuacionXml()
	{
		
	}
    public void anadirPuntuacion(String name, String puntuacionValor )
    {
        final String xmlFilePath = "puntuaciones.xml";
         
        // Usar este método para convertir contenido XML en string a un Objeto Document XML
        Document xmlDocument = convertXMLFileToXMLDocument( xmlFilePath );
        
        Element puntuacion = xmlDocument.createElement("puntuacion"); 
        int newId;
        int longitudPuntuacion;
        
        //Conseguir el último id de "puntuacion" para crear el nuevo id
        try
        {
        	longitudPuntuacion= xmlDocument.getDocumentElement().getElementsByTagName("puntuacion").getLength()-1;
        	System.out.println(longitudPuntuacion);
        	if (longitudPuntuacion>0)
        	{   
        		newId=Integer.parseInt(xmlDocument.getDocumentElement().
        		getElementsByTagName("puntuacion").item(longitudPuntuacion).getAttributes().item(0).getNodeValue());
        	}
        	else
        	{
        		newId=0;
        	}
        }
        catch (Exception e)
        {
        	newId=0;
        	System.out.println("Error "+e);
        }
        newId++;
        puntuacion.setAttribute("id", Integer.toString(newId)); 
        
        // Construilr los elementos con etiquetas Nombre y Puntuacion        
        Element nombre = xmlDocument.createElement("nombre");
        Element valor = xmlDocument.createElement("valor");
        nombre.setTextContent(name);
        
        //recoger el nuevoNombre de usuario y el valor de su puntuación
        valor.setTextContent(puntuacionValor);
        puntuacion.appendChild(nombre);
        puntuacion.appendChild(valor);
        xmlDocument.getDocumentElement().appendChild(puntuacion);
         
        //Write to file or print XML
        writeXmlDocumentToXmlFile(xmlDocument, "puntuaciones.xml");
    }
    public JTable leerPuntuacion()
    {
    	//lee archivo XML con lista de usuarios y puntuaciones y lo devuelve los datos encapsulados en un objeto JTable
    	final String xmlFilePath = "puntuaciones.xml";
    	ArrayList<Puntuacion> puntuaciones = new ArrayList<>();
    	JTable puntuacionTabla;
    	DefaultTableModel model=new DefaultTableModel();
        model.addColumn("Usuario");
    	model.addColumn("Puntuacion");
        
        Document xmlDocument = convertXMLFileToXMLDocument( xmlFilePath );
        
        try 
        {
	        Element root = xmlDocument.getDocumentElement();
	        NodeList nodeListaPuntuaciones = root.getElementsByTagName("puntuacion");

	        for (int i = 0; i < nodeListaPuntuaciones.getLength(); i++)
	        {
	            Node node = nodeListaPuntuaciones.item(i);
	            if (node.getNodeType() == Node.ELEMENT_NODE ) 
	            {
	            	//Por cada etiqueta "Puntuacion" hay una etiqueta nombre y otra puntuacion
	            	Puntuacion puntuacion=new Puntuacion(node.getChildNodes().item(1).getTextContent().toString(),Integer.parseInt(node.getChildNodes().item(3).getTextContent().toString()));
	            	puntuaciones.add(puntuacion);
	            }
	        }
	        //Ordenar las puntuaciones por valor de puntuación
	        Collections.sort(puntuaciones, new Comparator<Puntuacion>() {
	            public int compare(Puntuacion o1, Puntuacion o2) {
	                return Integer.compare(o1.obtPuntuacion(), o2.obtPuntuacion());
	            }
	        });
	        Collections.reverse(puntuaciones);
	        
	        //Estructurar las puntuaciones en un tabla 
	    	puntuaciones.forEach( x -> model.addRow(new Object[]{x.obtNombre(), x.obtPuntuacion()}) );            
	        puntuacionTabla = new JTable(model);
	        return puntuacionTabla;
        }
        catch(Exception e)
        {
        	System.out.println("ERROR en leerPuntuacion: "+ e );
        	return new JTable(model);
        }
    }

 
    private static Document convertXMLFileToXMLDocument(String filePath) 
    {
    	//Parseador que produce objetos del DOM para contenido XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         
        //API para obtener una instancia Documento DOM
        DocumentBuilder builder = null;
        try
        {
            //Crear DocumentBuilder wcon configuración por Defecto
            builder = factory.newDocumentBuilder();
             
            //Parsear el contenido a un objeto Document
            Document xmlDocument = builder.parse(new File(filePath));
             
            return xmlDocument;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return null;
        }
       
    }
     
    private static void writeXmlDocumentToXmlFile(Document xmlDocument, String fileName)
    {
        TransformerFactory tf = TransformerFactory.newInstance();
        tf.setAttribute("indent-number", 4);

        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                          
            //Mostrar XML o Logs o Console
            StringWriter writer = new StringWriter();
            
            transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));
            String xmlString = writer.getBuffer().toString();   
            System.out.println(xmlString);          
             
            //Escribir XML a un archivo
            FileOutputStream outStream = new FileOutputStream(new File(fileName)); 
            transformer.transform(new DOMSource(xmlDocument), new StreamResult(outStream));
        } 
        catch (TransformerException e) 
        {
            e.printStackTrace();
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}