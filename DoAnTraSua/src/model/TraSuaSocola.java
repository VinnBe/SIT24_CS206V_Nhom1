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
    public double getPrice(){
        return this.price ;
    }
    public String getName(){
         return this.name  ; 
    }
    public String toString(){
        return this.name;
    }
}
