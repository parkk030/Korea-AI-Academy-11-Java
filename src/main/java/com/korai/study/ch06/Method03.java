package com.korai.study.ch06;

public class Method03 {
    public static void main(String[] args) {
        System.out.println(new Student());

        System.out.println(new Student(11,"안녕하세요","11"));
    }
}

class Student{
    final int age;
    final String name;
    String address;

    Student(){
        age = 0;
        name = null;
        address = null;
    }

    Student(int age ,String name, String address){
       this.age = age;
       this.name = name;
       this.address=address;

    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
