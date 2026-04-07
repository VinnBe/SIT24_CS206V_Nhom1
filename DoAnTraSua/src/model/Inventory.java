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
    public static final String[] toppingNames = { "Tran Chau","Banh Plan"}; // mang chứa tên nguyên liệu 
    public static int[] soLuong = {2, 3 }; // số lượng tương ứng
    public static boolean useTopping(String name) {
        for (int i = 0; i < toppingNames.length; i++) {  
            if (toppingNames[i].equals(name)) { // tìm tên nguyên liệu tương ứng
                if (soLuong[i] <= 0){           // kiểm tra nếu số lượng của nguyên liệu tương ứng >=0 thì trả ra false
                    return false;
                }
                soLuong[i]--;                   // nếu >=0 thì số lượng -1 và trả ra 
                return true;
            }
        }
        return false;                      
    }
}
