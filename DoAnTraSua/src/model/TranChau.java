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
    }
    @Override
    public double getPrice(){
        return drink.getPrice() + this.price;
    }
    public String toString(){
       return drink.toString() +" + "+ this.name;
    }
}
