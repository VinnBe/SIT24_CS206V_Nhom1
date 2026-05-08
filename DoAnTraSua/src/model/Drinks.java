/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nhan
 */
public class Drinks implements Drink {
     String name;
     double price;

    public Drinks() {}
    public double getPrice(){
        return this.price;
    }
    public  String ten(){
        return this.name;
    }
    public boolean phaChe() {

        // kiểm tra nguyên liệu

        if (Inventory.da <= 0) {
            System.out.println("Hết đá");
            return false;
        }
        if (Inventory.duong <= 0) {
            System.out.println("Hết đường");
            return false;
        }
        return true;
    }
    public void themTopping(Toppings tp){
      if (Inventory.useTopping(tp.ten())) {     // Nếu topping còn thì sẽ trả về true 
            this.name +=" + "+  tp.ten();
            this.price+=tp.getPrice();
    } 
    else {
        // Không đủ topping thì ko thêm
        System.out.println("Không đủ topping " + tp.ten() + " trong kho!");
     }
    };
       public String toString(){
             return this.name + ": "  + this.price + " VND";
    }
       //
       public Drinks copy() {
    Drinks d = new Drinks();
    d.name  = this.name;
    d.price = this.price;
    return d;
    }
}
