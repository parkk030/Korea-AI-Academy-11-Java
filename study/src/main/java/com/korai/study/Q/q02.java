package com.korai.study.Q;

public class q02 {
    public static void main(String[] args) {

        Counter c1 = new Counter();
        Counter c2 = new Counter();
        c1.increment(); c1.increment(); c1.increment();
        c2.increment();
        System.out.println(c1.getCount() + " " + c2.getCount());
        Rectangle r = new Rectangle(4, 5);
        System.out.println(r.area() + " " + r.perimeter() + " " + r.isSquare());
        BankAccount acc = new BankAccount("김준일", 10000);
        acc.deposit(5000);
        System.out.println(acc.withdraw(20000)); // false
        System.out.println(acc.withdraw(3000)); // true
        System.out.println(acc.getBalance()); // 12000
        Student s = new Student("김준일", 90, 35, 80);
        System.out.printf("%d %.1f %b%n", s.total(), s.average(), s.isPass());

        Cart cart = new Cart();
        Cart cart2 = new Cart();
        cart.add("펜", 1500, 3);
        cart.add("노트", 3000, 2);
        cart2.add("노트", 3000, 2);
        cart2.add("노트", 3000, 2);
        System.out.println(cart.getTotal()); // 10500
        System.out.println(cart2.getTotal());
        System.out.println(cart.getTotal(10)); // 9450
    }
}

class Counter{
    private int count=0;
    public void increment(){
        this.count++;
    }
    public void reset(){
        this.count=0;
    }
    public int getCount(){
        return this.count;
    }

}

class Rectangle{
    private int width;
    private int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int area(){
        int area= width*height;
        return area;
    }

    public int perimeter(){
        int per = 2*width + 2*height;
        return per;
    }

    public boolean isSquare(){

        if(width!=height){
            return false;
        }
        return true;
    }

}

class BankAccount{
    private String owner;
    private int balance;

    public  BankAccount(String owner,int balance){
        this.owner = owner;
        this.balance = balance;
    }

    public void deposit(int money){
        this.balance += money;
    }

    public boolean withdraw(int money){
        if(this.balance<money){
            return false;
        }
        this.balance -= money;
        return true;
    }

    public int getBalance() {
        return balance;
    }
}

class Student{
    private String name;
    private int kor;
    private int eng;
    private int math;

    public Student(String name, int kor, int eng, int math) {
        this.name = name;
        this.kor = kor;
        this.eng = eng;
        this.math = math;
    }

    public int total(){
        return kor+eng+math;
    }

    public double average(){
        return total()/3.0;
    }

    public boolean isPass(){
    if(average()>=60 && kor>=40 && eng>=40 && math>=40){
        return true;
    }
    return false;
    }
}

class Cart{
    private String name;
    private int price;
    private int qty;
    private int total=0;


    public void add(String name, int price, int qty){
       total += price*qty;
    }

    public int getTotal(){
        return total;
    }

    public int getTotal(int couponPercent){

        return (total/100)*(100-couponPercent);
    }


}