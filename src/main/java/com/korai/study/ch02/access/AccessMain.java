package com.korai.study.ch02.access;

class 선생 {

    private String name;

    void 이름설정하기(String name){
        this.name = name;
    }

    void setName(String name){
        this.name = name;
    }
    String getName(){
        return name;
    }
}


public class AccessMain
{
    static class 학생 {

        String name;
        private int age;
    }


    public static void main(String[] args) {


        학생 s1 = new 학생();
        s1.name = "이름0";
        s1.age = 22;
        System.out.println(s1.age);


    }

    static void run1(){
        학생 s1 = new 학생();
        s1.name = "이름0";
        s1.age = 22;

        선생 s2 = new 선생();
        s2.이름설정하기("가나");
        System.out.println(s2.getName());
    }


}


