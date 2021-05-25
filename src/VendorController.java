import javax.swing.*;
import java.util.*;
import java.io.*;
import org.w3c.dom.*;
import java.time.format.*;
import java.time.*;
import java.net.*;
import org.xml.sax.SAXException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.ParserConfigurationException;

/**
 * This class is the heart of the application as it controls what the view does
 * and if / how it responds.  It loads the model used in MVC and is the C of 
 * MVC.  It also loads the view, the V in MVC.
 */

public class VendorController implements IVendorController {
	
	private IVendorFrame frame;
	private final static int SEC = 1000;
	private final static int BUY_PORT = 14000;
	private final static int SELL_PORT = 14001;
	protected final static boolean RUNNING = true;
	private final static String FILE_NAME = "config.xml";
	private boolean running = true;
	private ArrayList<VendItem> itemList;
	private ArrayList<String> toons;
	private File logFile;
	
	/**
	 * Constructor
	 */
	
	public VendorController() {
		
		// get config and make itemlist

		try {
			NodeList nList = FileFactory.getConfigFromFile(FILE_NAME);
			itemList = new ArrayList<>();
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				VendItem temp = new VendItem();
				String raw = nNode.getAttributes().getNamedItem("owner").getNodeValue();
				temp.owner = raw.substring(0,1).toUpperCase() + raw.substring(1).toLowerCase();
				temp.name = nNode.getAttributes().getNamedItem("name").getNodeValue();
				temp.price = Integer.parseInt(nNode.getAttributes().getNamedItem("price").getNodeValue());
				temp.buying = (nNode.getAttributes().getNamedItem("activity")
					.getNodeValue()).matches("buying");
				itemList.add(temp);
			}
			Collections.sort(itemList);
			
			// make toonlist
			
			toons = getToonList();
		
			// make vendor frame
			
			frame = new VendorFrame(this);
		
		} catch (SAXException saxe) {
			System.out.println("Unable to parse configuration file");
			System.exit(0);
		} catch (ParserConfigurationException pce) {
			System.out.println("Unable to parse configuration file");
			System.exit(0);
		} catch (IOException ioe) {
			System.out.println("Unable to open configuration file");
			System.exit(0);
		}	
	}
	
	public ArrayList<VendItem> getItemList() {
		
		// check to see if item list was already built and return it if that is case
		
		if (itemList != null && itemList.size() > 0) {
			return itemList;
		}
		try {
			NodeList nList = FileFactory.getConfigFromFile(FILE_NAME);
			itemList = new ArrayList<>();
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				VendItem temp = new VendItem();
				String raw = nNode.getAttributes().getNamedItem("owner").getNodeValue();
				temp.owner = raw.substring(0,1).toUpperCase() + raw.substring(1).toLowerCase();
				temp.name = nNode.getAttributes().getNamedItem("name").getNodeValue();
				temp.price = Integer.parseInt(nNode.getAttributes().getNamedItem("price").getNodeValue());
				temp.buying = nNode.getAttributes().getNamedItem("activity")
					.getNodeValue() == "selling";
				itemList.add(temp);
			}
			Collections.sort(itemList);
		} catch (SAXException saxe) {
			System.out.println("Unable to parse configuration file");
			System.exit(0);
		} catch (ParserConfigurationException pce) {
			System.out.println("Unable to parse configuration file");
			System.exit(0);
		} catch (IOException ioe) {
			System.out.println("Unable to open configuration file");
			System.exit(0);
		}	
		return itemList;
	}
	
	/**
	 * Method for getting an array of owners based on all the items that are listed.
	 * @return The list of owners that are selling items.
	 */
		
	public ArrayList<String> getToonList() {
		if (toons != null && toons.size() > 0) {
			return toons;
		} 
		if (itemList == null || itemList.size() ==  0) {
			itemList = getItemList();
		} 
		toons = new ArrayList<>();
		for (int i = 0; i < itemList.size(); i++) {
			VendItem tempItem = itemList.get(i);
			if (!matches(tempItem.owner, toons)) {
				String temp = tempItem.owner;
				toons.add(temp);
			} 
		}
		return toons;
	}
	
	public File getLogFile() {
		return logFile;
	}
	
	/**
	 * Method for checking to see if owner is listed anywhere in the arraylist
	 * for toons.
	 * @param owner is the name of the owner you are looking for in the array.
	 * @param toons is the current list of toons.
	 * @return True if the owner is already listed in the array of owners.
	 */
	
	private static boolean matches(String owner, ArrayList<String> toons) {
		for (int i = 0; i < toons.size(); i++) {
			if (owner.toLowerCase().matches(toons.get(i).toLowerCase())) return true;
		}
		return false;
	}
	
	/**
	 * 
	 */
	
	public void run() {
	    System.out.println("waiting on log file selection...");
		while (logFile == null) {
			try {
				Thread.sleep(SEC);
			} catch(InterruptedException ie) {
				// do nothing since youre only trying not to spam CPU with nops
			}
		}
		// get the line count for start

		int orig_count = 0;
		try {
			orig_count = P99Sellsv3.getBaseLineCount(logFile);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("started parsing log file...");
		// read the log file now and parse at new lines
		while (running == RUNNING) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(logFile));
				int count = 0;
				while (count < orig_count) {
					reader.readLine();
					count++;
				}
				String temp = null;
				while ((temp = reader.readLine()) != null) {
					for (int i = 0; i < itemList.size(); i++) {
						VendItem item = itemList.get(i);
						if (!item.disabled && !item.completed && temp.contains(item.name)
						&& temp.contains("WTB") && !item.buying) {
							alertBuyer(item, temp);
						}
						if (!item.disabled && !item.completed && temp.contains(item.name)
						&& temp.contains("WTS") && item.buying) {
							alertSeller(item, temp);
						}
					}
                                        count++;
				}
                                orig_count = count;
			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
				System.exit(0);
			} catch (IOException ioe) {
				ioe.printStackTrace();
				System.exit(0);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
			try {
				Thread.sleep(5 * SEC);
			} catch (InterruptedException ie) {
				// do nothing
			}
		}
	}
	
	public void updateItemDisplay(String currentToon) {
		for (int i = 0; i < itemList.size(); i++) {
			VendItem tempItem = itemList.get(i);
			if (tempItem.owner.matches(currentToon)) {					
				tempItem.disabled = !tempItem.disabled;
			}
		}
		frame.filterListings(itemList);
	}
	
	public void logFileUpdate(File logFile) {
		this.logFile = logFile;
	}
	
	public void updateConfigurationFile() {
		try {
			FileFactory.saveConfigToFile(FILE_NAME, itemList);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			System.exit(0);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * This method takes the information provided and performs an
	 * alert to the user of the application visually as well as
	 * performing a network broadcast of it so that other machines
	 * can receive the alert.
	 * @param item The item to be bought, also provides the price.
	 * @param logLine The line in the log for the alert, provides
	 *        the user along with the time.
	 */
		
	public void alertBuyer(VendItem item, String logLine) {
		String temp = getTime() + " " + getBuyer(logLine) + " - " 
			+ item.name + " to " + item.owner + " for " + item.price + "p.";
		System.out.println(temp);
		sendIt(temp, BUY_PORT);
		JOptionPane.showMessageDialog((JFrame)frame, temp, "We have a Buyer for " + item.owner + "!",
			JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * This method takes the information provided and performs an
	 * alert to the user of the application visually as well as
	 * performing a network broadcast of it so that other machines
	 * can receive the alert.
	 * @param item The item to be sold, also provides the price.
	 * @param logLine The line in the log for the alert, provides
	 *        the user along with the time.
	 */
		
	public void alertSeller(VendItem item, String logLine) {
		String temp = getTime() + " " + getSeller(logLine) + " - " 
			+ item.name + " to " + item.owner + " for " + item.price + "p.";
		System.out.println(temp);
		sendIt(temp, SELL_PORT);
		JOptionPane.showMessageDialog((JFrame)frame, temp, "We have a Seller for " + item.owner + "!",
			JOptionPane.INFORMATION_MESSAGE);
	}
	
	private String getSeller(String logLine) {
		String temp = logLine.substring(logLine.indexOf("]") + 2);
		String[] temps = temp.split(" ");
		return temps[0];
	}
	
	private String getBuyer(String logLine) {
		return getSeller(logLine);
	}

	public static String getTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public static void sendIt(String message, int port) {
		String hostname = "255.255.255.255";
		try {
			InetAddress address = InetAddress.getByName(hostname);
			DatagramPacket packet = new DatagramPacket(message.getBytes(),
				message.length(), address, port);
			try (DatagramSocket socket = new DatagramSocket()) {
				socket.setBroadcast(true);
				socket.send(packet);
				socket.close();
			}
		} catch (SocketException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}
}
