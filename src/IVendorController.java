import java.io.*;
import java.util.*;

/**
 * This interface is for the Controller class to enforce a contract of
 * what functions it will do, based on the MVC design pattern.
 */

public interface IVendorController {
	
	public void run();
	
	public void alertBuyer(VendItem item, String logLine);
	
	public void alertSeller(VendItem item, String logLine);
	
	public File getLogFile();
	
	public ArrayList<String> getToonList();
	
	public ArrayList<VendItem> getItemList();
	
	public void updateConfigurationFile();
	
	public void logFileUpdate(File logFile);
	
	public void updateItemDisplay(String toon);
}