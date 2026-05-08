/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import java.util.ArrayList;
import java.util.List;
import model.Drink;
import model.Drinks;
import model.Inventory;
import model.Toppings;

/**
 *
 * @author hung
 */
public class OrderService {
    private List<Drink> item = new ArrayList<>();
    private int soLuong = 0; // chỉ đếm số món nước

    // Thêm đồ uống
    public void addItem(Drinks drink) {
        item.add(drink);
        this.soLuong++;
    }

    // Thêm topping — kiểm tra kho trước
    public void addItem(Toppings tp) {
        if (Inventory.useTopping(tp.ten())) {
            item.add(tp);
        } else {
            System.out.println("Khong du nguyen lieu : " + tp.ten());
        }
    }

    // Tính tổng tiền, giảm 10% nếu từ 5 món nước trở lên
    public double getPrice() {
        double sum = 0;
        for (Drink x : item) {
            sum += x.getPrice();
        }
        if (this.soLuong >= 5) {
            sum = sum - 0.1 * sum;
        }
        return sum;
    }
     // In hóa đơn
    public void hienThiHoaDon() {
        System.out.println("Hoa don cua khach hang");
        for (Drink x : item) {
            System.out.println(x);
        }
        System.out.println("Tong tien la: " + getPrice() + " VND");
        System.out.println("Cam on khach hang da tin tuong <3");
    }

    // Getter dùng cho test
    public List<Drink> getItem() {
        return item;
    }

    public int getSoLuong() {
        return soLuong;
    }

    // Reset đơn hàng
    public void reset() {
        item.clear();
        soLuong = 0;
    }
    
}
