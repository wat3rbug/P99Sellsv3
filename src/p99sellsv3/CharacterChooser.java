/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p99sellsv3;

import java.io.*;
/**
 *
 * @author douglas
 */
public class CharacterChooser extends Chooser {
    
    public CharacterChooser() {
        super("Character Log");
        baseDirectory = new File("/home/douglas/.wine/dosdevices/c:/Program "
                + "Files/Sony/Everquest/Logs/");

    }
    
}
