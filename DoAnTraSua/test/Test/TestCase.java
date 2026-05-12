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
import model.HatThuyTinh;
import model.Inventory;
import model.MatchaDaXay;
import model.Order;
import model.SocolaDaXay;
import model.ThachCuNang;
import model.Toppings;
import model.TraChanh;
import model.TraDau;
import model.TraMangCau;
import model.TraOi;
import model.TraSuaMatCha;
import model.TraSuaSocola;
import model.TraSuaThaiXanh;
import model.TraSuaTruyenThong;
import model.TraVai;
import model.TranChau;
import model.TranChauTrang;
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
        TraSuaSocola tsc = new TraSuaSocola();
        tsc.setSize("M");
        service.addDrink(items, tsc); // 30000
        assertEquals(30000, service.getPrice(items), 0);
    }

    @Test
    public void testThemNhieuMonNuoc() {
        TraSuaTruyenThong tstt = new TraSuaTruyenThong();
        tstt.setSize("M");
        TraSuaThaiXanh tstx = new TraSuaThaiXanh();
        tstx.setSize("M");
        TraSuaSocola tss = new TraSuaSocola();
        tss.setSize("M");
        service.addDrink(items,tstt); // 25000
        service.addDrink(items,tstx);    // 25000
        service.addDrink(items, tss);      // 30000
        assertEquals(80000, service.getPrice(items), 0);
    }
    // TEST dat tra sua
    @Test
    public void testTraSuaMatChaSizeM() {
        TraSuaMatCha ts = new TraSuaMatCha();
        ts.setSize("M");
        service.addDrink(items, ts);
        assertEquals(30000, service.getPrice(items), 0);
    }
        @Test
    public void testTraSuaMatChaSizeL() { 
        TraSuaMatCha ts = new TraSuaMatCha();
        ts.setSize("L");
        service.addDrink(items, ts);
        assertEquals(37000, service.getPrice(items), 0);
    }
        @Test
    public void testTraSuaTruyenThongSizeL() {
        TraSuaTruyenThong ts = new TraSuaTruyenThong();
        ts.setSize("L");
        service.addDrink(items, ts);
        assertEquals(35000, service.getPrice(items), 0);
    }
    @Test
    public void testTraSuaThaiXanhSizeL() {
        TraSuaThaiXanh ts = new TraSuaThaiXanh();
        ts.setSize("L");
        service.addDrink(items, ts);
        assertEquals(35000, service.getPrice(items), 0);
    }
     // 2. TEST THÊM MÓN NƯỚC — TRÀ TRÁI CÂY

        @Test
    public void testTraChanhSizeM() {
        TraChanh tc = new TraChanh();
        tc.setSize("M");
        service.addDrink(items, tc);
        assertEquals(25000, service.getPrice(items), 0);
    }
        @Test
    public void testTraDauSizeM() {
        TraDau td = new TraDau();
        td.setSize("M");
        service.addDrink(items, td);
        assertEquals(30000, service.getPrice(items), 0);
    }
    
    @Test
    public void testTraDauSizeL() {
        TraDau td = new TraDau();
        td.setSize("L");
        service.addDrink(items, td);
        assertEquals(37000, service.getPrice(items), 0);
    }
        @Test
    public void testTraMangCauSizeM() {
        TraMangCau tm = new TraMangCau();
        tm.setSize("M");
        service.addDrink(items, tm);
        assertEquals(30000, service.getPrice(items), 0);
    }
@Test
    public void testTraOiSizeM() {
        TraOi to = new TraOi();
        to.setSize("M");
        service.addDrink(items, to);
        assertEquals(25000, service.getPrice(items), 0);
    }
        @Test
    public void testTraVaiSizeM() {
        TraVai tv = new TraVai();
        tv.setSize("M");
        service.addDrink(items, tv);
        assertEquals(25000, service.getPrice(items), 0);
    }
     // 3. TEST THÊM MÓN NƯỚC — ĐÁ XAY
        @Test
    public void testMatchaDaXaySizeM() {
        MatchaDaXay mdx = new MatchaDaXay();
        mdx.setSize("M");
        service.addDrink(items, mdx);
        assertEquals(30000, service.getPrice(items), 0);
    }
 
    @Test
    public void testMatchaDaXaySizeL() {
        MatchaDaXay mdx = new MatchaDaXay();
        mdx.setSize("L");
        service.addDrink(items, mdx);
        assertEquals(37000, service.getPrice(items), 0);
    }
 
    @Test
    public void testSocolaDaXaySizeM() {
        SocolaDaXay sdx = new SocolaDaXay();
        sdx.setSize("M");
        service.addDrink(items, sdx);
        assertEquals(30000, service.getPrice(items), 0);
    }
    @Test
    public void testSocolaDaXaySizeL() {
        SocolaDaXay sdx = new SocolaDaXay();
        sdx.setSize("L");
        service.addDrink(items, sdx);
        assertEquals(37000, service.getPrice(items), 0);
    }
    @Test
    public void testGiaSizeM() {
        TraSuaSocola ts = new TraSuaSocola();
        ts.setSize("M");
        service.addDrink(items, ts);
        assertEquals(30000, service.getPrice(items), 0);
    }
 
    @Test
    public void testGiaSizeL() {
        TraSuaSocola ts = new TraSuaSocola();
        ts.setSize("L");
        service.addDrink(items, ts);
        service.addDrink(items, ts);
        assertEquals(74000, service.getPrice(items), 0);
    }
  // 5. TEST GIẢM GIÁ
    
 





    @Test
    public void testDonRong() {
        assertEquals(0, service.getPrice(items), 0);
    }

    // =========================================================
    // TEST GIẢM GIÁ
    // =========================================================

    @Test
    public void testGiamGiaDung5Mon() {
        for (int i = 0; i < 5; i++) {
        TraSuaSocola  ts = new TraSuaSocola();
        ts.setSize("M");
        service.addDrink(items,ts); }// 5 x 30000 = 150000
        assertEquals(135000, service.getPrice(items), 0); // -10%
    }

    @Test
    public void testKhongGiamGia4Mon() {
        for (int i = 0; i < 4; i++) {
        
         TraSuaSocola   ts =  new TraSuaSocola();
         ts.setSize("L");
         service.addDrink(items, ts);}// 4 x 30000 = 120000
        assertEquals(148000, service.getPrice(items), 0);
    }
    @Test
    public void testGiamGia6Mon() {
        for (int i = 0; i < 6; i++) {
            TraSuaThaiXanh ts = new TraSuaThaiXanh();
            ts.setSize("M");
            service.addDrink(items, ts); // 6 x 25000 = 150000 -> -10% = 135000
        }
        assertEquals(135000, service.getPrice(items), 0);
    }

   

    @Test
    public void testToppingKhongTinhVaoGiamGia() {
        for (int i = 0; i < 4; i++) {
        TraSuaThaiXanh tx = new TraSuaThaiXanh();
        tx.setSize("M");
        service.addDrink(items,tx); // 100
        service.addTopping(items, new TranChau());  }                             // 5000, không tính vào soLuong
        // chỉ 4 món nước → không giảm giá
        assertEquals(105000, service.getPrice(items), 0);
    }

    // =========================================================
    // TEST TOPPING
    // =========================================================

       @Test
    public void testThemToppingConHang() {
        boolean result = service.addTopping(items, new TranChau());
        assertTrue(result);
        assertEquals(5000, service.getPrice(items), 0);
    }
 
    @Test
    public void testThemToppingHetHang() {
        Inventory.soLuong[0] = 0;
        boolean result = service.addTopping(items, new TranChau());
        assertFalse(result);
        assertEquals(0, service.getPrice(items), 0);
    }


    @Test
    public void testThemToppingVuotKho() {
        Inventory.soLuong[0] = 50;
        service.addTopping(items, new TranChau()); // lần 1 — OK
        service.addTopping(items, new TranChau()); // lần 2 — không được
        assertEquals(5000, service.getPrice(items), 0);
    }

    @Test
    public void testThemHaiLoaiTopping() {
        service.addTopping(items, new TranChau());  // 5000
        service.addTopping(items, new HatThuyTinh());  // 5000
        assertEquals(10000, service.getPrice(items), 0);
    }
        @Test
    public void testThemTatCaLoaiTopping() {
        service.addTopping(items, new TranChau());      // 5000
        service.addTopping(items, new BanhPlan());      // 7000
        service.addTopping(items, new HatThuyTinh());   // 5000
        service.addTopping(items, new ThachCuNang());   // 5000
        service.addTopping(items, new TranChauTrang()); // 5000
        assertEquals(27000, service.getPrice(items), 0);
    }
        @Test
    public void testCommitTopping() {
        Toppings tc= new TranChau();
        int index = Inventory.getIndex(tc.ten());
        Inventory.commit(index,(int)tc.getSoLuongDung()); // 50 -> 0
        assertEquals(0, Inventory.getSoLuong("Trân châu"));
    }
    @Test
    public void testReturnTopping() {
        Toppings tc= new TranChau();
        int index = Inventory.getIndex(tc.ten());
        Inventory.commit(index,(int)tc.getSoLuongDung()); // 50 -> 0
        Inventory.returnTopping(tc.ten());  // 0 -> 50
        assertEquals(50, Inventory.getSoLuong(tc.ten()));
    }
    @Test
    public void testGetSoLuongSauReset() {
        Inventory.soLuong[0] = 0;
        Inventory.reset();
        assertEquals(50, Inventory.getSoLuong("Trân châu"));
        assertEquals(2, Inventory.getSoLuong("Bánh plan"));
        assertEquals(100, Inventory.getSoLuong("Hạt thủy tinh"));
        assertEquals(2, Inventory.getSoLuong("Thạch củ năng"));
        assertEquals(100, Inventory.getSoLuong("Trân châu trắng"));
    }


 


    // =========================================================
    // TEST RESET
    // =========================================================

    @Test
    public void testResetDonHang() {
        TraSuaSocola tss = new TraSuaSocola();
        tss.setSize("M");
        service.addDrink(items,tss);
        service.addTopping(items, new TranChau());
        items.clear(); // reset = clear list từ ngoài
        assertEquals(0, service.getPrice(items), 0);
    }

    @Test
    public void testResetSoLuong() {
        TraSuaSocola tss = new TraSuaSocola();
        tss.setSize("M");
        service.addDrink(items,tss);
        service.addDrink(items, tss);
        items.clear();
        assertEquals(0, service.getSoLuong(items));
    }
    // 13. TEST INVENTORY — reserve / commit / release
    // =========================================================
 
    @Test
    public void testReserveThanhCong() {
        boolean ok = Inventory.reserve(0, 50); // index 0 = TranChau, kho=50
        assertTrue(ok);
        assertEquals(0, Inventory.getAvailable(0));
    }
    @Test
    public void testReserveVuotKho() {
        boolean ok = Inventory.reserve(0, 100); // kho chi co 50
        assertFalse(ok);
    }
        @Test
    public void testCommit() {
        Inventory.reserve(0, 50);
        Inventory.commit(0, 50);
        assertEquals(0, Inventory.getSoLuong("Trân châu"));
        assertEquals(0, Inventory.getAvailable(0));
    }
 
    @Test
    public void testDonHangDayDu5NuocCoTopping() {
        // 5 x TraSuaThaiXanh M (25000) = 125000 + TranChau (5000) = 130000
        // giảm 10% tổng (5 mon nuoc) -> 130000 * 0.9 = 117000
        for (int i = 0; i < 5; i++) {
            TraSuaThaiXanh ts = new TraSuaThaiXanh();
            ts.setSize("M");
            service.addDrink(items, ts);
        }
        service.addTopping(items, new TranChau());
        assertEquals(117000, service.getPrice(items), 0);
    }
    @Test
    public void testDonHangTraiCayVaTraSua() {
        TraChanh tc = new TraChanh();
        tc.setSize("M"); // 25000
        TraSuaSocola tss = new TraSuaSocola();
        tss.setSize("L"); // 37000
        service.addDrink(items, tc);
        service.addDrink(items, tss);
        assertEquals(62000, service.getPrice(items), 0);
    }


}


    
    
  

    



