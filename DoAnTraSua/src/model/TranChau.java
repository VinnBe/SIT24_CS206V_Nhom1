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
    public TranChau(){
    }
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
    @Override
     public String ten(){
        return this.name;
    }
    public double gia(){
        return this.price;
    }
    public double getPrice(){
        return this.price + drink.gia() ;
    }
    public String toString(){
        return drink.ten() + ": " + drink.getPrice() + " VND"   + " + " + this.ten() + ": " + this.price +" VND" ;
    }
}
