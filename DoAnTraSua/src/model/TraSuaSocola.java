/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nhan
 */
public class TraSuaSocola implements Drink {
    String name ;
    double price;
    public TraSuaSocola(){
        this.name="Tra sua socola";
        this.price=30000;
    }
      @Override
    public String ten(){
         return this.name  ; 
    }
    public double gia(){
        return this.price;
    }  
    public double getPrice(){
        return this.price ;
    }
    public String toString(){
                  return this.name + ": "  + this.price + " VND";
    }
}
