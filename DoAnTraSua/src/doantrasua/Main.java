/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package doantrasua;
import model.BanhPlan;
import model.Drink;
import model.Menu;
import model.Order;
import model.TraSuaSocola;
import model.TraSuaThaiXanh;
import model.TraSuaTruyenThong;
import model.TranChau;
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
        Drink tstt = new TraSuaTruyenThong();
        Drink tstx = new TraSuaThaiXanh();
        tstx =new TranChau(tstx);
        Order order= new Order();
        order.addItem(tstt);
        order.addItem(tstx);
        order.getPrice();
        order.hienThiHoaDon();
    }
}
