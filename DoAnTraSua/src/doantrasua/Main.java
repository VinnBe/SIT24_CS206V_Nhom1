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
        tstt =new TranChau(tstt);
        tstt =new BanhPlan(tstt);
        Drink tstx= new TraSuaThaiXanh();
        tstx=new BanhPlan(tstx);
        Order order= new Order();
        order.addItem(tstt, 2);
        order.addItem(tstx,3);
        order.getPrice();
        order.hienThiHoaDon();
    }
    
}
