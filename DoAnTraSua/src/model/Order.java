/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nhan
 */
public class Order {
    List<OrderItem> item= new ArrayList<>();
    Drink drink;
    double total=0;
    public Order(){
    }
    public void addItem(Drink drink, int soLuong){
        item.add(new OrderItem(drink, soLuong));
    }
    public void hienThiHoaDon(){
        System.out.println("Hoa don cua khach hang");
       for ( OrderItem x:item){
           System.out.println(x.drink + " || so luong: " + x.soLuong);
       }
        System.out.println("Tong tien la: " + this.total + " VND");
        System.out.println("Cam on khach hang da tin tuong <3");
    }
    public double getPrice(){
       for( OrderItem x: item){
           total+=x.getPrice();
       }
       return total;
   }
}
