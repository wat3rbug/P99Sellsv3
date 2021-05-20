import javax.swing.*;
import java.awt.*;

/**
 * A simple class to display the items to trade by use of a checkbox.
 */

public class VendorCheckBox extends JCheckBox {
	
	protected VendItem item;
	
	public VendorCheckBox(VendItem item) {
		super(item.name + " - " + item.price + "pp");
		this.item = item;
		addChangeListener(new VendItemListener(item));
	}
}