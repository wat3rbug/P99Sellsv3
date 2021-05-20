import java.io.*;

/**
 * This class is the start of the application.  It currently only contains the main()
 * method to start execution and a few static class agnostic methods.  The purpose of
 * this application is to provide a means to using a configuration file to monitor
 * the EC Tunnel to buy and sell items using the logs of EQ and to visual notify the
 * user that something has appeared in the logs, which is in the list of items to buy
 * or sell.  It also allows for remote monitoring, which is commonly the case.  A lot
 * of time can be spent trying to sell items in the EC Tunnel, which is why it is
 * sometimes called 'tunnelquest'.  Hence the need for me to sell stuff while not
 * actively paying attention to it.  Furthermore, you can say which toons are available
 * to sell stuff.  So if you 'deselect' an owner, their stuff is removed from the list
 * of items to buy or sell.
 */

public class P99Sellsv3 {
	
	/**
	 * Variables
	 */
	
	
//	private final static int BUY_PORT = 14000;
//	private final static int SELL_PORT = 14001;
	
	/**
     * Entry point for the application.
	 * @param args The arguments passed to the application.  Not used.
	 */
	
	public static void main(String[] args) {
		IVendorController controller = new VendorController();
		controller.run();
	}
	
    /**
	 * This method returns the number of lines for the file provided,
	 * as a starting point so that future parses will start at new lines,
	 * instead of lines that may have been created days / months /or weeks ago.
	 * @param filename The file object to be parsed.  Typically this is done, to the
	 *        log file of the toon which is on the server actively trading in the
	 *        EC tunnel.
	 * @return The count of the last line in the log file.
	 */
	
	public static int getBaseLineCount(File filename) {
		int result = 0;
		try {
			try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
				while (reader.readLine() != null) result++;
			}
		} catch (IOException ioe) {
			System.out.println("Unable to open / read file");
			System.exit(0);
		}
		return result;
	}
	

}