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
    List<Drink> item= new ArrayList<>();        //mảng chứa các vật phẩm
    Drinks drink;
    int soLuong=0;
    double total=0;
    public Order(){}
    public void addItem(Drinks drink){             // them nuoc uong vao       
        item.add((drink));
        this.soLuong+=1;
    }
    public void addItem(Toppings tp){             //  goi topping khong 
       if(Inventory.useTopping(tp.ten())){
        item.add(tp);
       }
       else{
           System.out.println("Khong du nguyen lieu : "+ tp.ten());
       }
    }   
    
    public void hienThiHoaDon(){
        System.out.println("Hoa don cua khach hang");
        for ( Drink x: item){                   
           System.out.println(x);
        }
        System.out.println("Tong tien la: " + this.total + " VND");
        System.out.println("Cam on khach hang da tin tuong <3");
        }
    public double getPrice(){        // lấy tổng giá tiền
       for( Drink x: item){
           this.total+=x.getPrice();
       }
        if(this.soLuong >=5){
            this.total = this.total - (10.0/100.0)*this.total;
            
       }
       return this.total;
   }
}
