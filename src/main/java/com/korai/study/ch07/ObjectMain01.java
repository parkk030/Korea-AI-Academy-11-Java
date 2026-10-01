package com.korai.study.ch07;

public class ObjectMain01 {
    public static void main(String[] args) {

        final int num= 10;
        System.out.println(num);
        Student s1 = new Student(20260001,"가나다라");
        School school = new School("zocdfaf");
    }
}

class School{
    String name;

    School(String name){
        this.name=name;
    }


}


class Student{
   final int code; //필수
   final String name; //필수
   String address; //선택

   //NoArgumentsConstructor //인자들이없는 생성자
   Student(){
       code = 0;
       name = null;
   }
   //RequiredArgumentsConstructor //필수인자만 받는 생성자
   Student(int code, String name){
    this.code = code;
    this.name = name ;
    }
    // AllArgumentsConstructor //모든 인자를 받는 생성자
    Student(int code, String name, String address){
        this.code = code;
        this.name = name ;
        this.address = address;

    }
}
