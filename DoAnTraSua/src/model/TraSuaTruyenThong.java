/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nhan
 */
public class TraSuaTruyenThong implements Drink {
     String name ;
    double price;
    public TraSuaTruyenThong() {
        this.name="Tra sua truyen thong";
        this.price= 25000;
    }
    public double getPrice(){
        return this.price ;
    }
    public String getName(){
           return this.name ; 
    }
    public String toString(){
        return this.name;
    }
}