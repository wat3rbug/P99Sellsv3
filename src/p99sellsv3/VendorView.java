/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p99sellsv3;

import javax.swing.*;
import java.awt.GridLayout;
import java.io.*;

/**
 *
 * @author douglas
 */
public class VendorView extends JFrame {
    
    private VendorController controller;
    private final int WIDTH = 200;
    private final int HEIGHT = 250;
    
    private String buyerFile = null;
    private String sellerFile = null;
    private String logFile = null;
    private JPanel buyerArea;
    private JPanel sellerArea;
    
    public VendorView(VendorController vc) {
        super("Vendor Monitor");
        controller = vc;
        buyerArea = new JPanel();
        sellerArea = new JPanel();
        JScrollPane buyerScroll = new JScrollPane(buyerArea);
        JScrollPane sellerScroll = new JScrollPane(sellerArea);
        buyerScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sellerScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        buyerScroll.setSize(WIDTH, HEIGHT);
        sellerScroll.setSize(WIDTH, HEIGHT);
        buyerScroll.setBorder(BorderFactory.createTitledBorder("Buying"));
        sellerScroll.setBorder(BorderFactory.createTitledBorder("Selling"));
        add(buyerScroll);
        add(sellerScroll);
        setLayout(new GridLayout(2,1));
        
        // add menu system
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Files");
        JMenuItem buyerFileItem = new JMenuItem("Buyer...");
        JMenuItem sellerFileItem = new JMenuItem("Seller...");
        JMenuItem logFileItem = new JMenuItem("Log...");
        
        fileMenu.add(buyerFileItem);
        buyerFileItem.addActionListener(new Chooser("Buying Items"));
        fileMenu.add(sellerFileItem);
        sellerFileItem.addActionListener(new Chooser("Selling Items"));
        fileMenu.addSeparator();
        fileMenu.add(logFileItem);
        logFileItem.addActionListener(new CharacterChooser());
        
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(250,500);
        setVisible(true);
    }
}
