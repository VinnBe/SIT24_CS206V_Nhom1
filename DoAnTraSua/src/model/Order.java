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
    List<OrderItem> item = new ArrayList<>();
    double total = 0;
    public void addItem(Drinks drink, int soLuong){         // list gom nhieu ly voi so luong khac nhau
        item.add(new OrderItem(drink, soLuong));            // them tung loai nuoc vao 1 list de tinh toong
    }
    public void inDonHang(){            //in don hang
        System.out.println("Don hang");
        for (OrderItem x : item){
            System.out.println(x.drink + " || so luong " + x.soLuong);
        }
        System.out.println("Tong so tien la: " + this.total() + " VND");
    }
    public double total(){          // tinh tong tien cua tat ca ly         
        for (OrderItem x : item ){
            total+=x.getTotal();
        }
        return total;
    }
}
