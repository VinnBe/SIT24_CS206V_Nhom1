/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nhan
 */
public class Drinks {
    String name;
    double price;
    public Drinks(){}
    @Override
    public String toString(){           //in doi tuong mot cach tuong 
        return this.name + " gia: " +this.price + "VND"; 
    }
}

