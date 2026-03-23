/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nhan
 */
public class BanhPlan extends ToppingDecorator {
    double price = 7000;
    String name="Banh Plan";
    public BanhPlan(Drink drink){
        super(drink);
    }
    public double getPrice(){
        return drink.getPrice() + this.price;
    }
            public String toString(){
         return drink.toString() +" + " + this.name ; 
        }
}
