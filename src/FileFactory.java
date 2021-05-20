import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.*;
import org.xml.sax.SAXException;

/**
 * This class is used for one time static methods to address the files.  Its anticipated
 * that you will load the configuration file one time on start up, and then save it when 
 * the application exits.  I could encapsulate it in a class, but would only add unnecessary
 * complexity.
 */


public class FileFactory {
	
	/**
	 * This methods gets a NodeList of items to buy or sell from the configuration file
	 * that is provided. Any exceptions are passed to the class / instance that is calling
	 * the method since it is not known what is needed.
	 * @param String the name of the file as a String to get the configuration from.
	 * @return NodeList the XML list of nodes retrieve from the file given to the method.
	 */
	
	public static NodeList getConfigFromFile(String fileName) throws IOException, 
			ParserConfigurationException, SAXException {
				
		File itemConfigFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
		Document doc = dbBuilder.parse(itemConfigFile);
		doc.getDocumentElement().normalize();
		return doc.getElementsByTagName("item");
	}
	
	/**
	 * This methods saves a NodeList of items to buy or sell to the configuration file
	 * that is provided. Any exceptions are passed to the class / instance that is calling
	 * the method since it is not known what is needed.
	 * @param String the name of the file as a String to get the configuration from.
	 * @param ArrayList<VendItem> the list of items as converted to VendItem to save to the 
	 * file given from the method.
	 */
	
	public static void saveConfigToFile(String fileName, ArrayList<VendItem> itemList) 
			throws IOException, ParserConfigurationException, SAXException, TransformerException {
		File itemConfigFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.newDocument();
		Element rootElement = doc.createElement("items");
		doc.appendChild(rootElement);
		for(VendItem item : itemList) {
			Element latestItem = doc.createElement("item");
			if (!item.completed) {
				latestItem.setAttribute("name", item.name);
				latestItem.setAttribute("owner", item.owner);
				latestItem.setAttribute("price", String.valueOf(item.price));
				latestItem.setAttribute("activity", (item.buying ? "buying" : "selling"));
			}
			rootElement.appendChild(latestItem);
		}
		doc.normalize();
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer tFormer = tFactory.newTransformer();
		DOMSource dSource = new DOMSource(doc);
		StreamResult sResult = new StreamResult(itemConfigFile);
		tFormer.transform(dSource, sResult);
	}
}