import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;

/**
 * This class is used to register configuration events to the VendorFrame.
 */

public class UpdateListener implements ActionListener {
	
	IVendorController controller;
	
	public UpdateListener(IVendorController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.updateConfigurationFile();
	}
}