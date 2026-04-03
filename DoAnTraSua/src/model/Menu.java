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
    String[] topping = new String[2];
    public Menu(){
        Drink tstt =new TraSuaTruyenThong();
        Drink tstx =new TraSuaThaiXanh();
        Drink tsscl =new TraSuaSocola();
        drink[0]=tstt;
        drink[1]=tstx;
        drink[2]=tsscl;
        topping[0]="Tran Chau || 5000";
        topping[1]="Banh Plan || 7000";
    }
    public void hienThiMenu(){
        System.out.println("Menu nuoc uong siuuu ngonnn!!");
        for ( Drink x : drink ){
            System.out.println(x.getName() + " || Gia tien: " + x.getPrice() + " VND");
        }
        System.out.println("Topping tu chon");
        for (String tp :topping){
            System.out.println(tp);
       }
    }
}
