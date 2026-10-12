package com.korai.study.ch02.access.service;

import jdk.swing.interop.SwingInterOpUtils;




public class AccessMain2 {

    int age = 10;

    class School{
        String name;

    }

    public static void main(String[] args) {


        AccessMain2 a1 = new AccessMain2();
        School s1 = a1.new School();
        s1.name = "가나다";

        System.out.println(s1);
        System.out.println(s1.name);

    }

    public static void run(){

        AccessMain2 a1 = new AccessMain2();
        School s1 = a1.new School();
        a1.age = 110;

        s1.name = "가나다";

    }
}

    class AccessMain3 {
        static void run(){
            AccessMain2 am2 = new AccessMain2();
            AccessMain2.School s1 = am2.new School();
        }

    }

