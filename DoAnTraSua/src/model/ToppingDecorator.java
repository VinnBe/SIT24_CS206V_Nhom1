/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nhan
 */
public abstract class ToppingDecorator implements Drink{            //Lop boc drink goc 
    Drink drink;
    public ToppingDecorator(Drink drink){
        this.drink=drink;
    }
}
