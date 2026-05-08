/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package doantrasua;
import GUI.MainFrame;
import javax.swing.SwingUtilities;
import model.BanhPlan;
import model.Drink;
import model.Drinks;
import model.HatThuyTinh;
import model.Menu;
import model.Order;
import model.ThachCuNang;
import model.Toppings;
import model.TraSuaMatCha;
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
        // TOMDO code application logic here
        //topping
        TranChau tc= new TranChau();
        HatThuyTinh htt= new HatThuyTinh();
        BanhPlan bp=new BanhPlan();
        ThachCuNang tcn= new ThachCuNang();
        
        TraSuaSocola tsscl= new TraSuaSocola();
        tsscl.setSize("M");
        tsscl.themTopping(tc);
        Order order= new Order();
        order.addItem(tsscl);
        order.getPrice();
        order.hienThiHoaDon();
        //SwingUtilities.invokeLater(MainFrame::new);
    }

}
