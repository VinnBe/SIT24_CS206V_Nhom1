/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package btvenha3;

import java.util.Scanner;



/**
 *
 * @author hung
 */
public class BTvenha3 {

    /**
     * @param args the command line arguments
     */
    //Bai 1
    public static boolean checknumber(int n){
        int soLanchia=0;
        for(int i = 1;i <= n; i ++){
            if (n %i ==0){
                soLanchia +=1;
                
            }
        }
            if (soLanchia==2){
                return (true) ;
            }
            else{
                
                return (false);
            }
            
        
        
        
        
        
    }
    public static void In_1000_so_nguyen_to_dau_tien(){
        int count = 0;
        int number = 2;
        while(count<1000){
            if(checknumber(number)){
                System.out.println(number);
                count++;
            }
            number++;
        }
        
    }
    
    //Bai 2
    
    public static void NhapThongTin(){
        Scanner Thongtin = new Scanner(System.in);
        
        System.out.print("Nhap ten : ");
        String name = Thongtin.nextLine();
        System.out.print("Nhap tuoi : ");
        int age = Thongtin.nextInt();
        System.out.print("Nhap chieu cao : " );
        double height = Thongtin.nextDouble();
        System.out.print("Nhap can nang : ");
        double weight = Thongtin.nextDouble();
        
    }
    //bai 3
    public static void chuvi_dientich_hinhvuong(){
        int squareLeight = 5 ;
        int perimeter = (squareLeight*4);
        int area =(squareLeight*squareLeight);
        System.out.println("Chu vi hinh vuong la : "+ perimeter);
        System.out.println("Dien tich hinh vuong la : "+ area);
    }
    //Bai 4
    public static void chuvi_dientich_hinhvuong1(){
        Scanner Thongtin = new Scanner(System.in);
        System.out.print("Nhap chieu dai cua canh hinh vuong : ");
        int squareLeight = Thongtin.nextInt();
        int perimeter = (squareLeight*4);
        int area =(squareLeight*squareLeight);
        System.out.println("Chu vi hinh vuong la : "+ perimeter);
        System.out.println("Dien tich hinh vuong la : "+ area);
        
    }
    // Bai 5 
    public static void chuvi_dientich_hinhchunhat(){
        int width = 1;
        int length = 2;
        int perimeter=(length + width)*2 ;
        int area = length * width;
        System.out.println("Chu vi hinh chu nhat la : "+ perimeter);
        System.out.println("Dien tich hinh chu nhat la "+ area);
    }
    //Bai 6
    public static void chuvi_dientich_hinhchunhat1(){
        Scanner Thongtin = new Scanner(System.in);
        System.out.print("Nhap chieu rong hinh chu nhat :");
        int width = Thongtin.nextInt();
        System.out.print("Nhap chieu dai hinh chu nhat : ");
        int length = Thongtin.nextInt();
        int perimeter=(length + width)*2 ;
        int area = length * width;
        System.out.println("Chu vi hinh chu nhat la : "+ perimeter);
        System.out.println("Dien tich hinh chu nhat la "+ area);
    }
    // Bai 7 
    public static void chuvi_dientich_hinhtron(){
        double radius = 5;
        double pi = Math.PI;
        double perimeter = 2* radius * pi ;
        double area = (radius * radius)*pi;
        System.out.printf("Chu vi hinh tron la %.2f\n", perimeter );
        System.out.printf("Dien tich hinh tron la %.2f\n " ,area);
        
        
    }
    //Bai 8 
    public static void chuvi_dientich_hinhtron1(){
        Scanner Thongtin = new Scanner(System.in);
        System.out.print("Nhap ban kinh cua hinh tron : ");
        double radius = Thongtin.nextDouble();
        double pi =Math.PI;
        double perimeter = 2 * radius *pi ;
        double area = (radius * radius)*pi ;
        System.out.printf("Chu vi hinh tron la %.2f\n",perimeter);
        System.out.printf("Dien tich hinh tron la %.2f\n",area);
        
    }
    //Bai 9 
    public static void dientich_tamgiac(){
        int a = 7 ;
        int h = 5;
        double s = (double)(a*h)/2;
        System.out.printf("Dien tich hinh tam giac la %.2f\n :",s);
    }
    //Bai 10 
    public static void dientich_tamgiac1(){
        Scanner Thongtin = new Scanner(System.in);
        System.out.print("Nhap chieu dai canh day :");
        double a = Thongtin.nextDouble();
        System.out.print("Nhap chieu cao :");
        double h = Thongtin.nextDouble();
        double s =(a*h)/2;
        System.out.printf("Dien tich hinh tam giac la %.2f\n ",s);
    }  
    // Bai 11 
    public static void chuyendoi_nhietdo(){
        double fahrenheit = 50;
        double celsius = (double)5/9*(fahrenheit - 32);
        System.out.printf("Chuyen doi do F sang do C : %.1f\n  ", celsius);
        
    }
    
    // Bai 12 
    public static void chuyendoi_nhietdo1(){
        Scanner Thongtin = new Scanner(System.in);
        System.out.print("Nhap do fehreneit :");
        double fahrenheit = Thongtin.nextDouble();
        double celsius = (double)5/9*(fahrenheit-32);
        System.out.printf("Chuyen doi do F sang do C : %.1f\n",celsius);
        
        
    }
    // Bai 13
    public static void chuyendoi_nhietdo2(){
        double celsius = 20;
        double fahrenheit = (double)(celsius * 9/5)+32;
        System.out.printf("Chuyen doi tu do C sang do F %.1f\n",fahrenheit);
    }
    
        
    // Bai 14 
    public static void chuyendoi_nhietdo3(){
        Scanner Thongtin = new Scanner(System.in);
        System.out.print("Nhap nhiet do celsius: ");
        double celsius = Thongtin.nextDouble();
        double fahrenheit = (double)(celsius*9/5)+32;
        System.out.printf("Chuyen doi tu do C sang do F la %.1f\n",fahrenheit);
            
        }
    // Bai 15
    public static void luythua_coso(){
        int base = 2;
        int exponent = 3;
        int luythuacoso =(int)Math.pow(base,exponent);
        System.out.println("Luy thua co so la:"+ luythuacoso);
    }
    // Bai 16 
    public static void luythua_coso1(){
        Scanner Thongtin = new Scanner(System.in);
        System.out.print("Nhap gia tri base:");
        int base = Thongtin.nextInt();
        System.out.print("Nhap gia tri exponent:");
        int exponent = Thongtin.nextInt();
        int luythuacoso = (int)Math.pow(base,exponent);
        System.out.println("Luy thua co so la "+ luythuacoso);
    }    
    //Bai 17 
    public static void in_ra_so_lon_nhat(){
        int one = 1;
        int two = 5;
        int three = 19;
        int max;
        if ( one >= two && one >= three){
            max = one;
        }
        else if ( two >= one && two >= three){
            max= two;
        }
        else {
            max = three;
        }
        System.out.println("So lan nhat la :"+ max);
    }
    // Bai 18 
    public static void in_ra_so_lon_nhat1(){
        Scanner Thongtin = new Scanner(System.in);
        System.out.print("Nhap vao gia tri one :");
        int one = Thongtin.nextInt();
        System.out.print("Nhap vao gia tri two :");
        int two = Thongtin.nextInt();
        System.out.print("Nhap vao gia tri three :");
        int three = Thongtin.nextInt();
        int max;
        if (one>= two && one >= three){
            max = one ;
        }
        else if( two >= one && two >= three){
            max = two;
        }
        else {
            max = three;
        }
        System.out.println("Gia tri lon nhat la : "+ max);
    }   
    //Bai 19 
    public static void kiem_tra_tam_giac(){
        int x = 3;
        int y = 4;
        int z = 5;
        if (x*x == y*y+ z*z || y*y == x*x + z*z|| z*z == x*x + y*y){
            System.out.println("Tam giac vuong");
        }
        else if (x==y && y== z){
            System.out.println("Tam giac deu ");
        }
        else if (x==z && x<y && x == z){
            System.out.println("Tam giac can");
        }
        else  if (x > y && y<z){
        System.out.println("Tam giac thuong");
        }
    }
    // Bai 20
    public static void kiem_tra_tam_giac1(){
        Scanner Thongtin = new Scanner( System.in);
        System.out.print("Nhap gia tri x : ");
        int x = Thongtin.nextInt();
        System.out.print("Nhap gia tri y : ");
        int y = Thongtin.nextInt();
        System.out.print("Nhap gia tri z : ");
        int z = Thongtin.nextInt();
        if (x*x == y*y+ z*z || y*y == x*x + z*z|| z*z == x*x + y*y){
            System.out.println("Tam giac vuong");
        }
        else if (x==y && y== z){
            System.out.println("Tam giac deu ");
        }
        else if (x==z && x<y && x == z){
            System.out.println("Tam giac can");
        }
        else  if (x > y && y<z){
        System.out.println("Tam giac thuong");
        }
    }    
        
    
    
       
            
        
    
        
                
        
         
        
    
        
    
        
        
        
        
        
            
        
        
    
   
        
    
        
    
    
    public static void main(String[] args){
        
        // TODO code application logic here
   
  //checknumber(10000);
  //In_1000_so_nguyen_to_dau_tien();
  //NhapThongTin();
  //chuvi_dientich_hinhvuong();
  //chuvi_dientich_hinhvuong1();
  //chuvi_dientich_hinhchunhat();
  //chuvi_dientich_hinhchunhat1();
  //chuvi_dientich_hinhtron();
  //chuvi_dientich_hinhtron1();
  //dientich_tamgiac();
  //dientich_tamgiac1();
  //chuyendoi_nhietdo();
  //chuyendoi_nhietdo1();
  //chuyendoi_nhietdo2();
  //chuyendoi_nhietdo3();
  //luythua_coso();
  //luythua_coso1();
  //in_ra_so_lon_nhat();
  //in_ra_so_lon_nhat1();
  //kiem_tra_tam_giac();
  kiem_tra_tam_giac1();
  
    }
}
    
        
        



