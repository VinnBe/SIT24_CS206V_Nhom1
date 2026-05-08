/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nhan
 */
public class TraSuaMatCha extends Drinks implements Drink{
    public TraSuaMatCha(){
        this.name="Tra sua socola";
        this.price=30000;
        this.priceL=37000;
    }
    @Override
    public boolean phaChe(){
        if(super.phaChe()){
            if(Inventory.traSua<=0){
                System.out.println("het tra sua");
                return false;
            }
            if(Inventory.matCha<=0){
                System.out.println("Het socola");
                return false;
            }
            else{
                System.out.println("Thanh cong");
                Inventory.da--;
                Inventory.duong--;
                Inventory.traSua--;
                Inventory.matCha--;
                return true;
            }
        }
        else {
            return false;}
        }
}
