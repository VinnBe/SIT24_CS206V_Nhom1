/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nhan
 */
public class Menu {
    Drink[] drink = new Drink[3];
    Drink[] topping = new Drink[2];
    public Menu(){
        drink[0]=new TraSuaTruyenThong();
        drink[1]=new TraSuaThaiXanh();
        drink[2]=new TraSuaSocola();
        topping[0]= new TranChau();
        topping[1]=new BanhPlan();
    }
    public void hienThiMenu(){
        System.out.println("Menu nuoc uong siuuu ngonnn!!");
        for ( Drink x : drink ){
            System.out.println(x.ten() + " || Gia tien: " + x.gia() + " VND");
        }
        System.out.println("Topping tu chon");
        for (Drink tp :topping){
            System.out.println(tp.ten() + " || Gia tien: " + tp.gia() + " VND");
       }
    }
}
