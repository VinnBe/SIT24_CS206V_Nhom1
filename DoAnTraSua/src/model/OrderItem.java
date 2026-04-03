/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nhan
 */
public class OrderItem {
    Drink drink;
//    int soLuong;
    public OrderItem(Drink drink){
        this.drink=drink;
      
    }
    public double getPrice(){
        return drink.getPrice();
    }
}
