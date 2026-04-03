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
    public static final String[] toppingNames = {
        "Tran Chau",
        "Banh Plan",
    };

    public static int[] soLuong = {
        10, 10  // số lượng tương ứng
    };

    public static boolean useTopping(String name) {
        for (int i = 0; i < toppingNames.length; i++) {
            if (toppingNames[i].equals(name)) {
                if (soLuong[i] <= 0) return false;
                soLuong[i]--;
                return true;
            }
        }
        return false;
    }
}
