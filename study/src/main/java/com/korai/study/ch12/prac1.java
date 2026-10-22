package com.korai.study.ch12;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.CheckedOutputStream;

public class prac1 {
    public static void main(String[] args) {
        q2.run();

        Item[] arr = new Item[3];
        arr[0] = new Item();
        arr[0].v = 1;



    }
}

class Item {
    int v;


}


class Book {
    private String title;
    private int price;

    public Book(String title){
        this(title,10000);
    }

    public Book(String title, int price) {
        this.title = title;
        this.price = price;
    }


}


class q1 {
    static void run(){

        ArrayList<Integer> student = new ArrayList<>();
        student.addAll(List.of(85,92,78,96,88));

        int sum=0;
        double average=0.0;
        int maxnum=0;
        for(int i = 0; i < student.size(); i++){
            sum += student.get(i);
            if(maxnum<student.get(i)){
                maxnum = student.get(i);
            }
        }
        average = (double)sum/student.size();

        System.out.println("전체 점수: "+student);
        System.out.println("평균 = " +average);
        System.out.println("최고 점수 : " + maxnum);

    }
}


class q2{
    static void run(){
        ArrayList<String> student = new ArrayList<>();
        student.addAll(List.of("Java", "Python", "Java", "C++", "Python", "Go"));
        System.out.println(student);

        for(int i = 0; i < student.size(); i++){
            String a = student.get(i);
            for(int j = i+1; j < student.size(); j++){
                if(a.contains(student.get(j))){
                    student.remove(a);
                    i = i-1;
                }
            }

        }
        System.out.println(student);

    }
}

/*class q02{
    static void run(){
        ArrayList<String> student = new ArrayList<>();
        ArrayList<String> student2 = new ArrayList<>();
        student.addAll(List.of("Java", "Python", "Java", "C++", "Python", "Go"));
        System.out.println(student);

        for(int i = 0; i < student.size(); i++){ //continue
            if(!student2.contains(student.get(i))){
                student2.add(student.get(i));
            }
        }
        System.out.println(student2);

    }
}
*/

class q3{
    static void run(){
        ArrayList<Integer> number = new ArrayList<>();
        ArrayList<Integer> number2 = new ArrayList<>();
        for(int i = 1; i <= 20; i++) {
            number.add(i);
            if(i % 3 == 0){
                number2.addFirst(i);
            }
        }
        System.out.println(number2);
        }
}

class q4{
    static void run(){
        ArrayList<Integer> listA = new ArrayList<>(List.of(5,2,8));
        ArrayList<Integer> listB = new ArrayList<>(List.of(3,9,1));
        listA.addAll(listB);
        System.out.println(listA);
        int a=0;
        for(int i = 0; i < listA.size(); i++){
            for(int j = i+1; j < listA.size(); j++){
                if(listA.get(i)>listA.get(j)){
                    a = listA.get(i);
                    listA.set(i,listA.get(j));
                    listA.set(j,a);
                }
            }
        }
        System.out.println(listA);
    }
}


class q5{
    static void run(){
        ArrayList<String> fruit = new ArrayList<>(List.of("사과","바나나","포도","딸기"));
       // if(fruit.contains("포도")){
        //    fruit.set()
       // }

        for(int i = 0; i < fruit.size(); i++){
            if(fruit.get(i).contains("포도")){
                fruit.set(i,"망고");
            }
            if(fruit.get(i).contains("딸기")){
                fruit.remove(i);
            }
        }
        fruit.addFirst("체리");

        System.out.println(fruit);

    }
}