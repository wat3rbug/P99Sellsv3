/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p99sellsv3;

import java.awt.event.*;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.*;
/**
 *
 * @author douglas
 */
public class Chooser implements ActionListener {
    
    private String title = null;
    private File fileName = null;
    protected File baseDirectory =  FileSystemView.getFileSystemView()
                .getHomeDirectory();
    
    public Chooser(String title) {
        if (title == null) this.title = "Open";
        else this.title = title;
    }
    
     @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser(baseDirectory);
        jfc.setDialogTitle(title);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new 
                FileNameExtensionFilter("Text files", "txt");
        jfc.addChoosableFileFilter(filter);
        int returnVal = jfc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            fileName = jfc.getSelectedFile();
            System.out.println(fileName.getAbsolutePath());
        }
    }
    
    public String getFile() {
        return fileName.getAbsolutePath();
    }   
}
