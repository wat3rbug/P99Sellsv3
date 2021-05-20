import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;

/**
 * This class is the V of the MVC design pattern.  It provides the
 * display for the application and sends responses back to the C
 * , or controller, of the application.
 */

public class VendorFrame extends JFrame implements IVendorFrame {
	
	/**
	 * Variables
	 */
	
	private JMenu activeToonsBar;
	private JMenuBar menuBar;
	private JPanel listingPanel;
	private JScrollPane scroller;
	private static Dimension listingDefault = new Dimension(350, 230);
	private IVendorController controller;
	
	/**
	 * This constructor is used to provide the 3 parameters needed for the 
	 * view to display properly.  The list of items, the list of owners, and
	 * the log file reference.  These are used to setup the list, the dropdown
	 * menu and the log file used by the controller.
	 */
		
	public VendorFrame(IVendorController controller) {
		super("P99 Sells");
		this.controller = controller;
		listingPanel = new JPanel();
		scroller = new JScrollPane(listingPanel);
		menuBar = new JMenuBar();		
		setupFileMenu(controller);
		setupToonMenu(controller.getToonList());
		setupAboutMenu();
		setupListings(controller.getItemList(), listingPanel);
		getContentPane().add(scroller);
		setJMenuBar(menuBar);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize((int)listingDefault.getWidth() + 10, (int)listingDefault.getHeight() + 60); // 360, 290
		this.setVisible(true);
	}
	
	public void setupFileMenu(IVendorController controller) {
		JMenu logMenu = new JMenu("File");
		JMenuItem logItem = new JMenuItem("Log file...");
		JMenuItem updateItem = new JMenuItem("Save configuration");
		updateItem.addActionListener(new UpdateListener(controller));
		logItem.addActionListener(new LogFileListener(controller)); 
		logMenu.add(logItem);
		logMenu.add(updateItem);
		menuBar.add(logMenu);
	}
	
	public void setupToonMenu(ArrayList<String> toons) {
		JMenu activeToonsBar = new JMenu("Active Characters");
		for (int i = 0; i < toons.size(); i++) {
			String toon = toons.get(i);
			JCheckBoxMenuItem temp = new JCheckBoxMenuItem(toon);
			temp.setState(true);
			temp.addItemListener(new ToonStateListener(toon, controller));
			activeToonsBar.add(temp);
		}
		menuBar.add(activeToonsBar);
	}
	
	private void setupAboutMenu() {
		JMenu aboutBar = new JMenu("About");
		JMenuItem aboutItem = new JMenuItem("About..."); 
		aboutItem.addActionListener(new AboutListener());
		aboutBar.add(aboutItem);
		menuBar.add(aboutBar);
	}
	
	private void setupListings(ArrayList<VendItem> itemList, JPanel panel) {
		panel.setLayout(new GridLayout(itemList.size(), 1));
		for (int i = 0; i < itemList.size(); i++) {
			VendorCheckBox temp = new VendorCheckBox(itemList.get(i));
			panel.add(temp);
		}
	}
	
	public void filterListings(ArrayList<VendItem> itemList) {
		listingPanel.removeAll();
		listingPanel.setLayout(new GridLayout(itemList.size(), 1));
		for (int i = 0; i < itemList.size(); i++) {
			if (!itemList.get(i).disabled) {
				VendorCheckBox temp = new VendorCheckBox(itemList.get(i));
				listingPanel.add(temp);
			}
		}
		listingPanel.updateUI();
	}
}