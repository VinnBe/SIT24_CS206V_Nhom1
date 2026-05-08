/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;


import Service.OrderService;
import java.util.ArrayList;
import java.util.List;
import model.BanhPlan;
import model.Drink;
import model.Drinks;
import model.Inventory;
import model.Order;
import model.Toppings;
import model.TraSuaSocola;
import model.TraSuaThaiXanh;
import model.TraSuaTruyenThong;
import model.TranChau;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;


/**SS
 *
 * @author hung
 */
public class TestCase {
    



    private OrderService service;
    private List<Drink> items; // list truyền từ ngoài vào

    @Before
    public void setUp() {
        service = new OrderService();
        items = new ArrayList<>(); // reset list trước mỗi test
        Inventory.reset();
    }

    // =========================================================
    // TEST THÊM MÓN NƯỚC
    // =========================================================

    @Test
    public void testThemMotMonNuoc() {
        service.addDrink(items, new TraSuaSocola()); // 30000
        assertEquals(30000, service.getPrice(items), 0);
    }

    @Test
    public void testThemNhieuMonNuoc() {
        service.addDrink(items, new TraSuaTruyenThong()); // 25000
        service.addDrink(items, new TraSuaThaiXanh());    // 25000
        service.addDrink(items, new TraSuaSocola());      // 30000
        assertEquals(80000, service.getPrice(items), 0);
    }

    @Test
    public void testDonRong() {
        assertEquals(0, service.getPrice(items), 0);
    }

    // =========================================================
    // TEST GIẢM GIÁ
    // =========================================================

    @Test
    public void testGiamGiaDung5Mon() {
        for (int i = 0; i < 5; i++) service.addDrink(items, new TraSuaSocola()); // 5 x 30000 = 150000
        assertEquals(135000, service.getPrice(items), 0); // -10%
    }

    @Test
    public void testKhongGiamGia4Mon() {
        for (int i = 0; i < 4; i++) service.addDrink(items, new TraSuaSocola()); // 4 x 30000 = 120000
        assertEquals(120000, service.getPrice(items), 0);
    }

    @Test
    public void testToppingKhongTinhVaoGiamGia() {
        for (int i = 0; i < 4; i++) service.addDrink(items, new TraSuaSocola()); // 120000
        service.addTopping(items, new TranChau());                               // 5000, không tính vào soLuong
        // chỉ 4 món nước → không giảm giá
        assertEquals(125000, service.getPrice(items), 0);
    }

    // =========================================================
    // TEST TOPPING
    // =========================================================

    @Test
    public void testThemToppingConHang() {
        service.addTopping(items, new TranChau()); // kho còn hàng
        assertEquals(5000, service.getPrice(items), 0);
    }

    @Test
    public void testThemToppingHetHang() {
        Inventory.soLuong[0] = 0;
        service.addTopping(items, new TranChau()); // không được thêm
        assertEquals(0, service.getPrice(items), 0);
    }

    @Test
    public void testThemToppingVuotKho() {
        Inventory.soLuong[0] = 1;
        service.addTopping(items, new TranChau()); // lần 1 — OK
        service.addTopping(items, new TranChau()); // lần 2 — không được
        assertEquals(5000, service.getPrice(items), 0);
    }

    @Test
    public void testThemHaiLoaiTopping() {
        service.addTopping(items, new TranChau());  // 5000
        service.addTopping(items, new BanhPlan());  // 7000
        assertEquals(12000, service.getPrice(items), 0);
    }

    // =========================================================
    // TEST RESET
    // =========================================================

    @Test
    public void testResetDonHang() {
        service.addDrink(items, new TraSuaSocola());
        service.addTopping(items, new TranChau());
        items.clear(); // reset = clear list từ ngoài
        assertEquals(0, service.getPrice(items), 0);
    }

    @Test
    public void testResetSoLuong() {
        service.addDrink(items, new TraSuaSocola());
        service.addDrink(items, new TraSuaSocola());
        items.clear();
        assertEquals(0, service.getSoLuong(items));
    }
}


    
    
  

    



