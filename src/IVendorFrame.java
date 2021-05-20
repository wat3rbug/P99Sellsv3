import java.util.*;
import java.io.*;

/**
 * This interface is for the VendorFrame class to enforce a contract of
 * what functions it will do, based on the MVC design pattern. As the view
 * of the application, it should have no knowledge about the controller and
 * its needs, it just visually presents the changes requested.
 */

public interface IVendorFrame {
	
	public void setupToonMenu(ArrayList<String> toons);
	
	public void setupFileMenu(IVendorController controller);
	
	public void filterListings(ArrayList<VendItem> itemList);
}