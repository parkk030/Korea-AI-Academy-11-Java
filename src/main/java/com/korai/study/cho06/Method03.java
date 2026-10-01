package com.korai.study.cho06;

public class Method03 {
    public static void main(String[] args) {
        System.out.println(new Student());

        System.out.println(new Student(11,"안녕하세요","11"));
    }
}

class Student{
    final int code;
    final String name;
    String address;

    Student(){
        code = 0;
        name = null;
        address = null;
    }

    Student(int code ,String name, String address){
       this.code = code;
       this.name = name;
       this.address=address;

    }



    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + code +
                '}';
    }
}
