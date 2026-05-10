/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author nhan
 */
public class Drinks implements Drink {
     String name;
     double priceL,priceM, price;
     String size;
     int mucDa=100;
     int mucDuong=100;
     ArrayList<Toppings> dsTopping = new ArrayList<>();
    public Drinks() {}
    public double getPriceL(){
        return this.priceL;
    }
    public double getPriceM(){
        return this.priceM;
    }
    public double getPrice(){
        return this.price;
    }
    public int getMucDa(){
        return this.mucDa;
    }
    public int getMucDuong(){
        return this.mucDuong;
    }
    public ArrayList<Toppings> getDSTopping(){
        return this.dsTopping;
    }
    public  String ten(){
        return this.name;
    }
    public void setMucDa(int mucDa){
        this.mucDa=mucDa;
    }
    public void setMucDuong(int mucDuong){
        this.mucDuong=mucDuong;
    }
     public void setSize(String size){
        if(size.equals("M")){
            this.size ="M";
            this.price=this.priceM;
        }
        else if(size.equals("L")){
            this.size="L";
            this.price=this.priceL;
        }
     }
    public void themTopping(Toppings tp){
      if (Inventory.useTopping(tp.ten())) {     // Nếu topping còn thì sẽ trả về true 
            this.name +=" + "+  tp.ten();
            this.price+=tp.getPrice();
            dsTopping.add(tp);
    } 
    else {
        // Không đủ topping thì ko thêm
        System.out.println("Không đủ topping " + tp.ten() + " trong kho!");
     }
    };
       public String toString(){
             return this.name + ": "  + this.price + " VND" + " | Size: " + this.size;
    }
       //
   public Drinks copy() {
    Drinks d = new Drinks();
    d.name   = this.name;
    d.price  = this.price;
    d.priceM = this.priceM;
    d.priceL = this.priceL;
    d.size   = this.size;
    return d;
}
}
