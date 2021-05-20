/**
 * This is a simple data structure class to track whether a toon should
 * be able to trade stuff or not based on whether they are in EC.
 */

public class ToonStatus {
	
	public String toon;
	public boolean disabled;
	
	public ToonStatus(String toon) {
		this.toon = toon;
		disabled = false;
	}
}