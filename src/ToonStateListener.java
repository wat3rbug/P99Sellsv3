import java.awt.event.*;

/**
 * The class used to listen for changes in the toon and whether it can list stuff 
 *for trading in the EC tunnel.
 */

public class ToonStateListener implements ItemListener {
	
	private String toon;
	private IVendorController controller;
	
	public ToonStateListener(String toon, IVendorController controller) {
		this.toon = toon;
		this.controller = controller;
	}
	
	public void itemStateChanged(ItemEvent ie) {
		controller.updateItemDisplay(toon);
	}
}