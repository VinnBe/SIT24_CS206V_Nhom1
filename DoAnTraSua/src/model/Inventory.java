    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package model;

    import java.util.HashMap;
    import java.util.Map;

    /**
     *
     * @author nhan
     */
    public class Inventory {
        static int[] reserved = new int[100]; // hoặc đúng size topping
       // public static int[] reserved2 = {0, 0, 0, 0, 0};
        static final Toppings[] toppingNames;
        public static int[] soLuong = {50, 2, 100, 2, 100};// số lượng tương ứng
        public static int da = 3, duong =3, traSua=3, socola=2, matCha=2;
           static {  // Khởi tạo 1 lần duy nhất khi class được load
            toppingNames = new Toppings[5];
            toppingNames[0] = new TranChau();
            toppingNames[1] = new BanhPlan();
            toppingNames[2] = new HatThuyTinh();
            toppingNames[3] = new ThachCuNang();
            toppingNames[4] = new TranChauTrang();
        }
        public static Toppings[] getToppings(){        // hàm lấy danh sách topping
            return Inventory.toppingNames;
        }
        public static boolean useTopping(String name) {  // hàm kiểm tra topping còn đủ không
            for (int i = 0; i < toppingNames.length; i++) {
                if (toppingNames[i].ten().equals(name)) {
                    if (soLuong[i] < toppingNames[i].soLuongDung) {
                        return false; // hết hàng
                    }
                    return true;
                }
            }
            return false; // không tìm thấy topping
        }
        public static void consumeTopping(String name) {   //hàm trừ kho khi thanh toán
            for (int i = 0; i < toppingNames.length; i++) {
                if (toppingNames[i].ten().equals(name)) {
                    soLuong[i] -= toppingNames[i].soLuongDung;
                    return;
                }
            }
        }
        public static void returnTopping(String name) {     // hàm lấy danh
            for (int i = 0; i < toppingNames.length; i++) {
                if (toppingNames[i].ten().equals(name)) {
                    soLuong[i]+=toppingNames[i].soLuongDung;
                    return;
                }
            }
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
        public static int getAvailable(int i) {   //hàm lấy nguyên liệu còn lại sau khi thêm vào giỏ
            return soLuong[i] - reserved[i];
        }
        public static boolean reserve(int i, int qty) {    // hàm kiểm tra yêu cầu thêm món có đựo thỏa không
            if (getAvailable(i) < qty) return false;
            reserved[i] += qty;
            return true;
        }
        public static void release(int i, int qty) {  // hủy đặt trước
            reserved[i] -= qty;
            if (reserved[i] < 0) reserved[i] = 0;
        }
        public static void commit(int i, int qty) {   // khi thannh toan thanh cong -nguyen lieu trong kho, - nguyen lieu dat truoc
            soLuong[i] -= qty;
            reserved[i] -= qty;
         if (reserved[i] < 0) reserved[i] = 0;
    }
        /**
         * Reset kho về mặc định (dùng cho test)
         */
        public static int getIndex(String name) {
            for (int i = 0; i < toppingNames.length; i++) {
             if (toppingNames[i].ten().equals(name)) return i;
                }
            return -1;
}
        public static void reset() {
            soLuong[0] = 50 ;
            soLuong[1] = 2;
            soLuong[2]=1000;
            soLuong[3]=2;
            soLuong[4]=1000;

        }


    }
