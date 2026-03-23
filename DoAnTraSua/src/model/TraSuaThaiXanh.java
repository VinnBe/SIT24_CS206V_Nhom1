/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nhan
 */
public class TraSuaThaiXanh implements Drink{
     String name ;
    double price;
    public TraSuaThaiXanh(){
        this.name="Tra sua thai xanh";
        this.price= 25000;
    }
    public double getPrice(){
        return this.price ;
    }
    public String toString(){
        return this.name ; 
        }
}
