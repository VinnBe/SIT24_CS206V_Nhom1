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
   Drinks drink;
   int soLuong;
   public OrderItem(Drinks drink, int soLuong){
       this.drink=drink;
       this.soLuong=soLuong;
   
   }
   
   public double getTotal(){
       return drink.price*soLuong;
  }
}
