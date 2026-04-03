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
    String name="Banh Plan";;
    public BanhPlan(Drink drink){
        super(drink);
         if (!Inventory.useTopping("Banh Plan")) {
             try{
             throw new RuntimeException("Het nguyen lieu");
             }
             catch(RuntimeException e){
             System.out.println("Het banh plan");
            }
        }
    }
    public String getName(){
        return this.name;
    }
    public double getPrice(){
        return  this.price+ drink.getPrice();
    }
     public String toString(){
        return drink.getName()+ " + " + this.getName();
    }
}
