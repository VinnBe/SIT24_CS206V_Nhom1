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
         Drink tstx = new TraSuaThaiXanh();
        tstx =new TranChau(tstx);
//        tstt =new BanhPlan(tstt);
//        Drink tstx= new TraSuaThaiXanh();
//        tstx=new TranChau(tstx);
  Drink tsscl = new TraSuaSocola();
        tsscl =new TranChau(tsscl);
          Drink tstt2 = new TraSuaTruyenThong();
        tstt2 =new BanhPlan(tstt2);
          Drink tstx2 = new TraSuaThaiXanh();
        tstx2 =new TranChau(tstx2);
        Order order= new Order();
        order.addItem(tstt);
        order.addItem(tstx);
        order.addItem(tsscl);
        order.addItem(tstt2);
        order.addItem(tstx2);
        //thay doi 
//        order.addItem(tstx,3);
       order.getPrice();
        order.hienThiHoaDon();
    }
}
