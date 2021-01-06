/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p99sellsv3;

/**
 *
 * @author douglas
 */
public class P99Sellsv3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BuyerList buyer = new BuyerList();
        BuyerList seller = new BuyerList();
        ToonLog toon = new ToonLog();
        VendorController vc = new VendorController(buyer, seller, toon);
        VendorView frame = new VendorView(vc);
        Thread runner = new Thread(vc);
        runner.start();
    }
    
}
