import java.awt.event.*;
import javax.swing.*;

/**
 * This class is used for displaying information about the application.
 * It has the major versions and what they had.
 */

public class AboutListener implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String about = "P99Sells version 3 - GUI, network, multiple vendors,\n"
			         + "                     buy and sell items\n" 
			         + "P99Sells version 2 - network, buy and sell items\n"
					 + "P99Sells version 1 - buy items\n";	
		JOptionPane.showMessageDialog(null, about, "Version Information", 
			JOptionPane.INFORMATION_MESSAGE);
	}
}