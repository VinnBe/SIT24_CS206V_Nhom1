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
    Drinks[] drinks; //tao mang menu gom cac nuoc uong
    public Menu(){
        this.drinks= new Drinks[3];
        Drinks tstt= new TraSuaTruyenThong();
        Drinks tsscl = new TraSuaSocola();
        Drinks tstx = new TraSuaThaiXanh();
        drinks[0]= tstt;
        drinks[1]= tsscl;
        drinks[2]= tstx;
    }
    public void hienThiMenu(){   //   hiển thị meunu 
        System.out.println("Menu do uong sieu ngon!!");
        for (Drinks x: drinks){
            System.out.println(x);
        }
    }
}