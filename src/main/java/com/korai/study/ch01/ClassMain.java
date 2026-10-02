package com.korai.study.ch01;

public class ClassMain {
    public static void main(String[] args) {
        // 1. 변수와 자료형
        int num = 10;
        String name = "가나다";
        final String name1 = "나다라"; //상수

        class Student{
            String name;
            int age;
        }

        Student one = new Student();
        one.name = "abc";
        one.age = 13;

        class Student2{
            String name;
            Object age;
        }

        Student2 two = new Student2();
        two.name = one.name;
        two.age = one.age;





        class Student3<AGE>{
            String name;
            AGE age;
        }
        Student3<String> three = new Student3<>();
        Student3<Integer> four = new Student3<>();

        three.age = "13";
        four.age = 15;

        System.out.println(three.age + four.age);

        int num2 = num;
        Student3<?> five = three;
    }

}
