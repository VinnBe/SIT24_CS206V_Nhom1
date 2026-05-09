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
    public Drinks[] drink = new Drinks[4];             // tạo mảng chứa nước
    public Toppings[] topping = new Toppings[2];           // tạo mảng chứa topping
    public Menu(){
        drink[0]=new TraSuaTruyenThong();
        drink[1]=new TraSuaThaiXanh();
        drink[2]=new TraSuaSocola();
        drink[3]=new TraSuaMatCha();
        topping[0]= new TranChau();
        topping[1]=new BanhPlan();
    }
    public void hienThiMenu(){
        System.out.println("Menu nuoc uong siuuu ngonnn!!");
        for ( Drinks x : drink ){
            System.out.println(x.ten() + " || Gia tien: " + x.getPrice() + " VND");
        }
        System.out.println("Topping tu chon");
        for (Toppings tp :topping){
            System.out.println(tp.ten() + " || Gia tien: " + tp.getPrice() + " VND");
       }
    }
}
