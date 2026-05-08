/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;


import Service.OrderService;
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
 
    // Chạy trước mỗi test — reset kho và đơn hàng về ban đầu
    @Before
    public void setUp() {
        service = new OrderService();// tao don moi truoc moi test
        Inventory.reset();
    }
 
    // =========================================================
    // TEST THÊM MÓN NƯỚC
    // =========================================================
 
    // Thêm 1 món nước, kiểm tra giá đúng không
    @Test
    public void testThemMotMonNuoc() {
        service.addItem(new TraSuaSocola()); // 30000
        assertEquals(30000, service.getPrice(), 0);
    }
 
    // Thêm nhiều món nước, kiểm tra tổng tiền
    @Test
    public void testThemNhieuMonNuoc() {
        service.addItem(new TraSuaTruyenThong()); // 25000
        service.addItem(new TraSuaThaiXanh());    // 25000
        service.addItem(new TraSuaSocola());      // 30000
        assertEquals(80000, service.getPrice(), 0);
    }
        // Đơn rỗng thì giá = 0
    @Test
    public void testDonRong() {
        assertEquals(0, service.getPrice(), 0);
    }
 
    // =========================================================
    // TEST GIẢM GIÁ
    // =========================================================
 
    // Mua đúng 5 món nước → giảm 10%
    @Test
    public void testGiamGiaDung5Mon() {
        service.addItem(new TraSuaSocola()); // 30000
        service.addItem(new TraSuaSocola()); // 30000
        service.addItem(new TraSuaSocola()); // 30000
        service.addItem(new TraSuaSocola()); // 30000
        service.addItem(new TraSuaSocola()); // 30000
        // tổng 150000 - 10% = 135000
        assertEquals(135000, service.getPrice(), 0);
    }
    // Mua 4 món nước → không giảm giá
    @Test
    public void testKhongGiamGia4Mon() {
        service.addItem(new TraSuaSocola()); // 30000
        service.addItem(new TraSuaSocola()); // 30000
        service.addItem(new TraSuaSocola()); // 30000
        service.addItem(new TraSuaSocola()); // 30000
        // tổng 120000, không giảm
        assertEquals(120000, service.getPrice(), 0);
    }
 
    // Topping không được tính vào số món để giảm giá
    @Test
    public void testToppingKhongTinhVaoGiamGia() {
        service.addItem(new TraSuaSocola()); // 30000
        service.addItem(new TraSuaSocola()); // 30000
        service.addItem(new TraSuaSocola()); // 30000
        service.addItem(new TraSuaSocola()); // 30000
        service.addItem(new TranChau());     // topping — không tính vào soLuong
        // chỉ có 4 món nước → không giảm giá
        // tổng = 120000 + 5000 = 125000
        assertEquals(125000, service.getPrice(), 0);
    }
// =========================================================
    // TEST TOPPING
    // =========================================================
 
    // Thêm topping còn hàng thì thành công
    @Test
    public void testThemToppingConHang() {
        service.addItem(new TranChau()); // kho còn 2
        assertEquals(5000, service.getPrice(), 0);
    }
 
    // Thêm topping hết hàng thì không thêm được
    @Test
    public void testThemToppingHetHang() {
        Inventory.soLuong[0] = 0; // đặt Trân Châu về 0
        service.addItem(new TranChau()); // không được thêm
        assertEquals(0, service.getPrice(), 0);
    }
// Thêm topping 2 lần khi kho chỉ còn 1
    @Test
    public void testThemToppingVuotKho() {
        Inventory.soLuong[0] = 1; // Trân Châu chỉ còn 1
        service.addItem(new TranChau()); // lần 1 — OK
        service.addItem(new TranChau()); // lần 2 — không được
        assertEquals(5000, service.getPrice(), 0); // chỉ tính 1 lần
    }
 
    // Thêm 2 loại topping khác nhau
    @Test
    public void testThemHaiLoaiTopping() {
        service.addItem(new TranChau());  // 5000
        service.addItem(new BanhPlan()); // 7000
        assertEquals(12000, service.getPrice(), 0);
    }
    // =========================================================
    // TEST RESET
    // =========================================================
 
    // Reset xong thì giá về 0
    @Test
    public void testResetDonHang() {
        service.addItem(new TraSuaSocola());
        service.addItem(new TranChau());
        service.reset();
        assertEquals(0, service.getPrice(), 0);
    }
 
    // Reset xong thì soLuong về 0
    @Test
    public void testResetSoLuong() {
        service.addItem(new TraSuaSocola());
        service.addItem(new TraSuaSocola());
        service.reset();
        assertEquals(0, service.getSoLuong());
    }


    
    
  

    

}

