/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package doantrasua;
import model.BanhPlan;
import model.Drink;
import model.Drinks;
import model.Menu;
import model.Order;
import model.Toppings;
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
//        Menu menu= new Menu();
//        menu.hienThiMenu();
//        Toppings tc= new TranChau();
//        Toppings bp=new BanhPlan();
//        Drinks tstt = new TraSuaTruyenThong();
        Drinks tsscl=new TraSuaSocola();
       Drinks tsscl2=new TraSuaSocola();
//        Drinks tsscl3=new TraSuaSocola();
        Drinks tstx= new TraSuaThaiXanh();
        Order order= new Order();
       // order.addItem(tstt);
        order.addItem(tsscl);
       order.addItem(tsscl2);
//        order.addItem(tsscl3);
        order.addItem(tstx);
      //  order.addItem(tc);
       // order.getPrice();
       // order.hienThiHoaDon();
       // order.addItem(bp);
//          
//          order.addItem(tc);
//          order.addItem(bp);
//          order.getPrice();
//          order.hienThiHoaDon();
//          order.getPrice();
//          order.hienThiHoaDon();
//          order.addItem(bp);
//          order.addItem(tc);
          order.getPrice();
          order.hienThiHoaDon();
    }
}
