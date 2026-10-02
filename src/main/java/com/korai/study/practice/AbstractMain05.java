package com.korai.study.practice;

import java.util.ArrayList;
import java.util.List;

public class AbstractMain05 {
    public static void main(String[] args) {
    Phone sm = new SmartPhone();
    sm.call();


   // FeaturePhone fp = new FeaturePhone();
   // fp.print1();
    //fp.print2();

    }

}

class Phone{
    String phoneNumber;

    Phone(){
        System.out.println("Phone 생성자 호출");
    }
    void call(){
        System.out.println("전화를 건다");
    }
}

class SmartPhone extends Phone {

    SmartPhone(){
        System.out.println("SmartPhone 생성자 호출");
    }

    @Override
    void call(){
        System.out.println("띠링");
    }
}

class FeaturePhone extends Phone{
    String phoneNumber;

    FeaturePhone(){
        System.out.println("SmartPhone 생성자 호출");
        phoneNumber = "010-1234-5678";
        super.phoneNumber="010.1111-1111";
        super.call();
    }

    void print1(){
        System.out.println(phoneNumber);
    }

    void print2(){
        System.out.println(super.phoneNumber);
    }

}
