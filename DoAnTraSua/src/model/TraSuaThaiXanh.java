/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nhan
 */
public class TraSuaThaiXanh extends Drinks implements Drink{
    public TraSuaThaiXanh(){
        this.name="Tra sua thai xanh";
        this.price= 25000;
    }      
    public boolean phaChe(){
        if(super.phaChe()){
            if(Inventory.traSua<=0){
                System.out.println("het tra sua");
                return false;
            }
            else{
                System.out.println("Thanh cong");
                 Inventory.da--;
                Inventory.duong--;
                Inventory.traSua--;
                return true;
            }
        }
        else {
            return false;}
        }
}
