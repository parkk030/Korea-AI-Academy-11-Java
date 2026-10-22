package com.korai.study.Q;

import lombok.Data;

import java.util.ArrayList;

public class q03 {
    public static void main(String[] args) {
        /*Circle a = new Circle(2);
        Circle b = new Circle(3);
        System.out.printf("%.2f%n", a.area()); // 12.56
        System.out.printf("%.2f%n", Circle.larger(a, b).area()); // 28.26

        Member m1 = new Member("김준일");
        Member m2 = new Member("김준이");
        Member m3 = new Member("김준삼");
        m2.introduce(); // 2번 회원 김준이입니다.
        System.out.println(Member.getTotalCount()); // 3

        Person p = new Person("김준일", 2000);
        System.out.println(Person.isLeapYear(2024)); // true
        System.out.println(p.getAge(2026)); // 26
        System.out.println(p.bornInLeapYear()); // true

        Product x = new Product("키보드", 50000);
        Product y = new Product("마우스", 30000);
        System.out.println(x.discountedPrice(20)); // 40000
        System.out.println(Product.withVat(30000)); // 33000
        System.out.println(Product.cheaper(x, y).name);

*/

        Player player1 = new Player("A");
        Player player2 = new Player("B");
        Player player3 = new Player("C");

        player1.pointUp(30);
        player2.pointUp(50);
        player3.pointUp(20);
        player1.pointUp(25);

        Player[] players = {player1,player2,player3};
        Player winnered = Player.winner(players);
        System.out.println(winnered.getName()+" "+winnered.getPoint());

     /*   Player[] players2 = {
                new Player("A"),
                new Player("B"),
                new Player("C")

        };*/

        ArrayList<Score> score= new ArrayList<>();
        Score score1 = new Score();
        Score score2 = new Score();
        score1.set("김준일", 90, 80);
        score2.set("김준이", 70, 60);
        System.out.println(score1.getName() + " " + score1.avg());
        System.out.println(score2.getName() + " " + score2.avg());

    }
}

class Circle{
    private double radius;
    static final double won = 3.14;
    public Circle(double radius) {
        this.radius = radius;

    }

    public double area(){

        return radius*radius*won;
    }

    static Circle larger(Circle a, Circle b){
        if(a.radius<b.radius){
            return b;
        }
        else {
            return a;
        }
    }

}

class Member{
    private String name;
    private static int totalCount =0;
    private int memberId ;

    public Member(String name) {
        this.name = name;
        totalCount++;
        this.memberId = totalCount;
    }

    public void introduce(){
        System.out.println(this.memberId+"번 회원 "+this.name+"입니다.");
    }

    public static int getTotalCount(){
        return totalCount;
    }
}

class Person{
    private String name;
    private int birthYear;

    public Person(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public static boolean isLeapYear(int year){
        if(year%4==0){
            return true;
        }
        else {
            return false;
        }
    }

    public int getAge(int year){
    return year - birthYear ;
    }

    public boolean bornInLeapYear(){
    return isLeapYear(birthYear);
    }

}

class Product{
    public String name;
    private int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int discountedPrice(int discount){
        return price/100*(100-discount);
    }

    public static int withVat(int anyNum){
        return anyNum+(anyNum/10);
    }

    public static Product cheaper(Product a, Product b){
        if(a.price>b.price){
            return b;
        }
        else{
            return a;
        }
    }

}

class Player{
    private String name;
    private int point;

    public Player(String name) {
        this.name = name;
        this.point=0;
    }

    public String getName() {
        return name;
    }

    public int getPoint(){
        return point;
    }

    public void pointUp(int point){
        this.point += point;
    }

    public static Player winner(Player players[]){
        Player win = players[0];

        for(int i = 0; i < players.length - 1; i++){
            if(win.getPoint() < players[i + 1].getPoint()){
                win = players[i + 1];
            }
        }
        return win;
    }

}


@Data
class Score {
    private String name;
    private int kor, eng;
    static int count = 0;

    public void set(String n, int k, int e) {
        name = n; kor = k; eng = e;
        count++;
    }
    public double avg() {
        return (kor + eng) / 2.0;
    }



}
