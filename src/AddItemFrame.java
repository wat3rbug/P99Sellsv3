import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AddItemFrame extends JFrame implements IAddItemFrame {
	
	private IVendorController controller;
	private JTextField nameField;
	private JTextField ownerField;
	private JTextField priceField;
	private JRadioButton buyer;
	private JRadioButton seller;
	
	public AddItemFrame(IVendorController controller) {
		this.controller = controller;
		this.setTitle("Add Item");
		JPanel namePanel = new JPanel();
		JLabel nameLbl = new JLabel("Name", SwingConstants.RIGHT);
		nameField = new JTextField(15);
		namePanel.add(nameLbl);
		namePanel.add(nameField);
		
		JPanel ownerPanel = new JPanel();
		JLabel ownerLbl = new JLabel("Owner", SwingConstants.RIGHT);
		ownerField = new JTextField(12);
		ownerPanel.add(ownerLbl);
		ownerPanel.add(ownerField);
		
		JPanel activityPanel = new JPanel();
		JLabel activityLbl = new JLabel("Activity", SwingConstants.RIGHT);
		ButtonGroup activityDecide = new ButtonGroup();
		JPanel decider = new JPanel();
		buyer =new JRadioButton("Buying", true);
		seller = new JRadioButton("Selling");
		decider.setLayout(new GridLayout(2, 1));
		decider.add(buyer);
		decider.add(seller);
		activityDecide.add(buyer);
		activityDecide.add(seller);
		activityPanel.add(activityLbl);
		activityPanel.add(decider);
		
		JPanel pricePanel = new JPanel();
		JLabel priceLbl = new JLabel("Price", SwingConstants.RIGHT);
		priceField = new JTextField(6);
		pricePanel.add(priceLbl);
		pricePanel.add(priceField);
		
		JPanel decidePanel = new JPanel();
		JButton okBtn = new JButton("Ok");
		okBtn.addActionListener(new AcceptListener(controller));
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new CancelListener());
		decidePanel.add(okBtn);
		decidePanel.add(cancelBtn);
		
		setLayout(new GridLayout(5, 1));
		add(namePanel);
		add(ownerPanel);
		add(activityPanel);
		add(pricePanel);
		add(decidePanel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(new Dimension(280, 270));
		setVisible(true);
	}
	
	public class AcceptListener implements ActionListener {
		
		private IVendorController controller;
		
		public AcceptListener(IVendorController controller) {
			this.controller = controller;
		}
		
		public void actionPerformed(ActionEvent ae) {
			VendItem item = new VendItem();
			item.name = nameField.getText();
			item.owner = ownerField.getText();
			item.price = Integer.parseInt(priceField.getText());
			item.buying = buyer.isSelected();
			controller.addItemToList(item);
			AddItemFrame.this.dispose();
		}
	}
	
	public class CancelListener implements ActionListener {
		
		public void actionPerformed(ActionEvent ae) {
			AddItemFrame.this.dispose();
		}
	}
}