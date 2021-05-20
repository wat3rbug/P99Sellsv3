import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;

/**
 * This class is used to register log events to the VendorFrame.
 */

public class LogFileListener implements ActionListener {
	
	private IVendorController controller;
	
	public LogFileListener(IVendorController controller) {
		this.controller = controller;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser("/home/douglas/.wine/dosdevices/c:/Program Files/Sony/Everquest/Logs/");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
		chooser.setFileFilter(filter);

		chooser.showOpenDialog(null);
		controller.logFileUpdate(chooser.getSelectedFile());
	}
}
