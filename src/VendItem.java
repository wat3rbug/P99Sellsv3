/**
 * This class tracks items that are to be traded.  The name of the item
 * along with the current price, and the toon that has it for trade.  It
 * is a basic data structure.
 */

public class VendItem implements Comparable<VendItem> {
	
	public String name;
	public String owner;
	public int price;
	public boolean buying = false;
	public boolean completed = false;
	public boolean visible = true;
	public boolean disabled = false;
	
	/**
	 * Gives a String representation of the data structure.  Used mostly for
	 * troubleshooting purposes.
	 * @return String which has the essential parts of the data structure listed.
	 */
		
	public String toString() {
		return "name: " + name + "\towner: " + owner + "\tprice: " + price 
			+ "pp\tactivity: " + String.valueOf(buying) + "\tcompleted: " 
			+ String.valueOf(completed);
	}
	
	/**
	 * Gives an integer representation of whether the other VendItem has the same name.  
	 * @param VendItem the item that willbe compared.  For our purposes only the name
	 *        of the item is compared, as we do not care about its price or the owner.
	 * @return int value on whether the name of the item matches or not.  For our purposes,
	 *         0 matches all other values do not match.
	 */
	
	public int compareTo(VendItem item) {
		int result = name.compareTo(item.name);
		return result;
	}
}