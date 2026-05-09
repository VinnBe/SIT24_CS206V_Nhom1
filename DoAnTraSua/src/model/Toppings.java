/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nhan
 */
public class Toppings implements Drink{            
    double price;
    protected String name;
    protected String donVi;
    double soLuongDung;
    public Toppings(){}
    public double getPrice(){            // lấy giá topping 
        return this.price;
    }
    public String ten(){
        return this.name;                   // lấy tên của topping
    }
    public String toString(){
        return this.name + " : " + this.price + " VND";
    }
}
