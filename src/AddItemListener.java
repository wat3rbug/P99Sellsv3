import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;


public class AddItemListener implements ActionListener {
	
	private IVendorController controller;
	
	public AddItemListener(IVendorController controller) {
		this.controller = controller;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		AddItemFrame adder = new AddItemFrame(controller);
	}
}