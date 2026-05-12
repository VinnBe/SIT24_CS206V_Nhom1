# 🧋 Quản Lý Trà Sữa (Java)

Ứng dụng Java giúp quản lý quán trà sữa đơn giản.
- MeoMeoTea là hệ thống quản lý bán trà sữa được xây dựng bằng Java Swing. Ứng dụng cho phép khách hàng xem menu, chọn đồ uống, tùy chỉnh size và topping, thêm vào giỏ hàng và đặt đơn. Hệ thống quản lý giỏ hàng, tính tiền tự động và kiểm tra tồn kho topping trong thời gian thực. 

---

## 📸 Demo
- Link : https://docs.google.com/document/d/1Uay3hLn0XKfFewngtwiDKygp6GPq8F8e/edit?usp=sharing&ouid=114776439835411973075&rtpof=true&sd=true 


## 🚀 Tính năng

* Xem menu đồ uống, toppings
* Tùy chỉnh đồ uống
* Quản lý giỏ hàng
* Đặt hàng và Hóa đơn
* Quản lý kho

## 🛠️ Công nghệ

- Java 17+ 
- Java Swing (javax.swing) 
- Apache NetBeans 21+ 
- NetBeans Ant (build.xml)

## 📦 Cài đặt

```bash
git clone https://github.com/VinnBe/SIT24_CS206V_Nhom1.git
```

---

## ▶️ Cách chạy
Cách 1: Mở bằng NetBeans (khuyến dùng) 
Cách 2: Chạy bằng lệnh (Command Line)
---

## 📁 Cấu trúc

```
src/
 ├── doantrasua
     ├── main
 ├── model/
      ├── BanhPlan.java
      ├── Drink
      ├── Drink
      ├── Drink
      ├── Drink
      ├── Drink
      ├── Drinks
      ├── Menu 
      ├── Order
      ├── TraSuaSocola
      ├── TraSuaThaiXanh
      ├── TraSuaTruyenThong
      ├── BanhPlan
      ├── TranChau
      ├── Inventory
      ├── Toppings
 └── controller/
 ├── service/        
 │    └── OrderService.java
 └── test/          
      └── TestCase.java
```

---

## 👤 Tác giả

* Hoài Nhân
* Thế Vinh
* Sang Hùng
---
---

## Phân công công việc


Nguyễn Thế Vinh :
++ Giao diện (Gui):
- Thiết kế MainFrame, MenuPanel
- Xây dựng CartPanel, ToppingPanel
- Tạo ReceiptDialog (hóa đơn)
- Thiết kế layout & responsive UI
---
---

Nguyễn Hoài Nhân :
++ Model + logic :
- Xây dựng class Drinks, Toppings
- Lập trình 11 loại đồ uống
- Inventory: reserve/commit/release
- OrderService: tính giá, khuyến mãi
- TestCase
---
---  
Lê Sang Hùng :
++ Model + logic :
- Xây dựng class Drinks, Toppings
- Lập trình 11 loại đồ uống
- Inventory: reserve/commit/release
- OrderService: tính giá, khuyến mãi
- TestCase
---
---  
Cả Nhóm :
++ Báo cáo + slide :
- Viết báo cáo đồ án (PDF)
- Chuẩn bị slide thuyết trình
- Tổng hợp tài liệu hướng dẫn


