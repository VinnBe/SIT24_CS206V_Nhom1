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

    // Thêm đồ uống vào list từ ngoài truyền vào
    public boolean addDrink(List<Drink> items, Drinks drink) {
        items.add(drink);
        return true;
    }

    // Thêm topping — kiểm tra kho trước
       public boolean addTopping(List<Drink> items, Toppings tp) {
        if (Inventory.useTopping(tp.ten())) {
            int index =Inventory.getIndex(tp.ten());
            Inventory.commit(index,(int) tp.getSoLuongDung()); // trừ kho thật sự
            items.add(tp);
            return true;

        }
        System.out.println("Khong du nguyen lieu: " + tp.ten());
        return false;
    }
        // Hoàn trả topping khi huỷ đơn
    public void removeTopping(List<Drink> items, Toppings tp) {
        if (items.remove(tp)) {
            Inventory.returnTopping(tp.ten());
        }
    }
    // Đếm số món nước (không tính topping)
    public int getSoLuong(List<Drink> items) {
        int count = 0;
        for (Drink x : items) {
            if (x instanceof Drinks) count++;
        }
        return count;
    }

    // Tính tổng tiền, giảm 10% nếu từ 5 món nước trở lên
    public double getPrice(List<Drink> items) {
        double sum = 0;
        for (Drink x : items) {
            sum += x.getPrice();
        }
        if (getSoLuong(items) >= 5) {
            sum = sum - 0.1 * sum;
        }
        return sum;
    }

    // In hóa đơn
    public void hienThiHoaDon(List<Drink> items) {
        System.out.println("Hoa don cua khach hang");
        for (Drink x : items) {
            System.out.println(x);
        }
        System.out.println("Tong tien la: " + getPrice(items) + " VND");
        System.out.println("Cam on khach hang da tin tuong <3");
    }
}

