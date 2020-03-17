package modelo;

import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
    public static void añadirPuntuacion(String name, String puntuacionValor )
    {
        final String xmlFilePath = "puntuaciones.xml";
         
        //Use method to convert XML string content to XML Document object
        Document xmlDocument = convertXMLFileToXMLDocument( xmlFilePath );
        
        Element puntuacion = xmlDocument.createElement("puntuacion"); 
        int newId;
        int longitudPuntuacion;
        
        //Conseguir el último id de puntuacion
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
    	final String xmlFilePath = "puntuaciones.xml";
    	JTable puntuacionTabla;
    	
    	DefaultTableModel model=new DefaultTableModel();;
        
        //Use method to convert XML string content to XML Document object
        Document xmlDocument = convertXMLFileToXMLDocument( xmlFilePath );
        
        try 
        {
	        Element root = xmlDocument.getDocumentElement();
	
	        NodeList nodeListaPuntuaciones = root.getChildNodes();
	        System.out.println( "EL ROOT:" );
	        System.out.println( root.getChildNodes().getLength() );

	
	        String[] st = null;
	        List<String> texts = new ArrayList<String>();
	        for (int i = 0; i < nodeListaPuntuaciones.getLength(); i++)
	        {
	        	texts=new ArrayList<String>();
	            Node node = nodeListaPuntuaciones.item(i);
	            if (node.getNodeType() == Node.ELEMENT_NODE ) 
	            {
	            	
	                texts.add( node.getTextContent() );
	                st = texts.toArray( new String[]{} );
	                System.out.println(st.toString());
	    	        model.addRow(st);
	
	            }
	        }
	        

	        
	       
	        puntuacionTabla = new JTable(model);
	        
	        System.out.println( puntuacionTabla );
	        return puntuacionTabla;
        }
        catch(Exception e)
        {
        	System.out.println( e );
        	
        	return new JTable(model);
        }
        

        
    	
    }

 
    private static Document convertXMLFileToXMLDocument(String filePath) 
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         
        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();
             
            //Parse the content to Document object
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
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
             
            // Uncomment if you do not require XML declaration
            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
             
            //Print XML or Logs or Console
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));
            String xmlString = writer.getBuffer().toString();   
            System.out.println(xmlString);          
             
            //Write XML to file
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