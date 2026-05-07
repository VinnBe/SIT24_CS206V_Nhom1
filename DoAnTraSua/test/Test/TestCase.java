/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;


import model.BanhPlan;
import model.Drink;
import model.Drinks;
import model.Order;
import model.Toppings;
import model.TraSuaSocola;
import model.TraSuaThaiXanh;
import model.TraSuaTruyenThong;
import model.TranChau;
import org.junit.Test;
import static org.junit.Assert.*;


/**SS
 *
 * @author hung
 */
public class TestCase {
    

        // Test tạo object trà sữa
    @Test
    public void testCreateMilkTea(){// test xem co tao dc object ts ko

        TraSuaSocola ts = new TraSuaSocola();
        TraSuaTruyenThong ts1= new TraSuaTruyenThong();
        assertNotNull(ts1);
        assertNotNull(ts);
    }
    @Test
    public void testPriceMilkTea(){

    Drink socola = new TraSuaSocola();
    Drink truyenthong = new TraSuaTruyenThong();
    Drink thaixanh = new TraSuaThaiXanh();
    
    assertEquals(25000,thaixanh.getPrice(),0.001);// test price thaixanh 
    assertEquals(25000,truyenthong.getPrice(),0.001);// test price ts tt
    assertEquals(30000, socola.getPrice(), 0.001);// (value mong doi, value thuc te,sai so cho phep"trave kiur double))
}   
    @Test
    public void testPriceToppings(){
        Toppings tc = new TranChau();
        Toppings bpl = new BanhPlan();
        
      assertEquals(5000, tc.getPrice(), 0.001);
      assertEquals(7000,bpl.getPrice(),0.001);
    
    }
     @Test
    public void testAddTopping(){

        Drinks thaixanh = new TraSuaThaiXanh();

        thaixanh.themTopping(new TranChau());
        thaixanh.themTopping(new BanhPlan());
        

        assertEquals(37000, thaixanh.getPrice(), 0.001);// tesst gia sau khi add them
    
    }
     @Test
    public void testOrderTotal(){

        Order order = new Order();

        order.addItem(new TraSuaSocola());
        order.addItem(new TraSuaTruyenThong());
        order.addItem(new TranChau());

        assertEquals(60000, order.getPrice(), 0.001);
    }
     // Test discount khi >= 5 món
    @Test
    public void testDiscount(){

        Order order = new Order();

      //  for(int i = 0; i < 5; i++){
        //    order.addItem(new TraSuaTruyenThong());
      //  }
       order.addItem(new TraSuaTruyenThong());
       order.addItem(new TraSuaThaiXanh());
       order.addItem(new TraSuaSocola());
       order.addItem(new TranChau());
       order.addItem(new BanhPlan());
        assertEquals(92000, order.getPrice(), 0.001);
    }
    

}

