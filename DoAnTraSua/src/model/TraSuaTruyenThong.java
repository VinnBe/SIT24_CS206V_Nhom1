/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nhan
 */
public class TraSuaTruyenThong extends Drinks implements Drink {
    public TraSuaTruyenThong() {
        this.name="Tra sua truyen thong";
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