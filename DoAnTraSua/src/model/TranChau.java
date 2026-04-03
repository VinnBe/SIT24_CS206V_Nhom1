/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nhan
 */
public class TranChau extends ToppingDecorator{
    double price=5000;
    String name = "Tran Chau";
    public TranChau(Drink drink){
        super(drink);
          if (!Inventory.useTopping("Tran Chau")) {
              try{
             throw new RuntimeException("Het nguyen lieu");
             }
             catch(RuntimeException tt){
             System.out.println("Het tran chau");
            }
        }
    }
    public String getName(){
        return this.name;
    }
    public double getPrice(){
        return this.price + drink.getPrice();
    }
    public String toString(){
        return drink.getName() + " + " + this.getName();
    }
}
