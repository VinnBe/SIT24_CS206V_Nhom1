/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nhan
 */
public class Inventory {
    public static final Toppings[] toppingNames;
    public static int[] soLuong = {2, 2};// số lượng tương ứng
    public static int da = 3, duong =3, traSua=3, socola=2;
       static {  // Khởi tạo 1 lần duy nhất khi class được load
        toppingNames = new Toppings[2];
        toppingNames[0] = new TranChau();
        toppingNames[1] = new BanhPlan();
    }
           /**
     * Dùng 1 topping — trả về true nếu còn hàng, false nếu hết
     */
    public static boolean useTopping(String name) {
        for (int i = 0; i < toppingNames.length; i++) {
            if (toppingNames[i].ten().equals(name)) {
                if (soLuong[i] <= 0) {
                    return false; // hết hàng
                }
                soLuong[i]--; // trừ kho
                return true;
            }
        }
        return false; // không tìm thấy topping
    }
    /**
     * Kiểm tra số lượng còn lại (dùng cho test)
     */
    public static int getSoLuong(String name) {
        for (int i = 0; i < toppingNames.length; i++) {
            if (toppingNames[i].ten().equals(name)) {
                return soLuong[i];
            }
        }
        return -1; // không tìm thấy
    }
 
    /**
     * Reset kho về mặc định (dùng cho test)
     */
    public static void reset() {
        soLuong[0] = 2;
        soLuong[1] = 2;
    }


}
