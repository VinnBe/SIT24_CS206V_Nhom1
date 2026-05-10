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
    public List<Drink> item= new ArrayList<>();        //mảng chứa các vật phẩm
    Drinks drink;
    int soLuong=0;
    
    public List<Drink> getItems() {
    return item;
    }
    public Order(){}
    public void addItem(Drink drink){             // them nuoc uong vao       
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
        System.out.println("Tong tien la: " + getPrice() + " VND");
        System.out.println("Cam on khach hang da tin tuong <3");
        }
    public int getSoLuong(){
        return this.soLuong;
    }
    public void setSoLuong(int n){
            this.soLuong=n;
    }
    public double getPrice(){        // lấy tổng giá tiền
       double sum = 0;
        for( Drink x: item){
           sum +=x.getPrice();
       }
        if(this.soLuong >=5){
            sum = sum - 0.1 * sum;
            
       }
       return sum;
   }
}
