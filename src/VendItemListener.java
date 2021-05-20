import javax.swing.event.*;

/**
 * This class is used to signal when an item has been completed.  It does not
 * get removed from the application during the course of its run, but upon
 * saving, if the item is complete, it will be removed from the list to 'save'
 * items.
 */

public class VendItemListener implements ChangeListener {

	private VendItem item;
	
	public VendItemListener(VendItem item) {
		super();
		this.item = item;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		item.completed = !item.completed;
	}
}