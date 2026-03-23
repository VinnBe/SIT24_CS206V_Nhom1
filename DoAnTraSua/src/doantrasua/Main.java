/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package doantrasua;
import model.Menu;
import model.Order;
import model.Drinks;
import model.TraSuaSocola;
import model.TraSuaThaiXanh;
import model.TraSuaTruyenThong;
/**
 *
 * @author vinh-nguyen
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Menu menu= new Menu();
        menu.hienThiMenu();
        Drinks tstt= new TraSuaTruyenThong();
        Drinks tsscl= new TraSuaSocola();
        Drinks tstx= new TraSuaThaiXanh();
        Order order = new Order();
        order.addItem(tstt, 1);
        order.addItem(tsscl, 2);
        order.inDonHang();
    }
    
}
