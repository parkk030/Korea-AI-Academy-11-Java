package com.korai.study.ch02.access.service;

public class AccessMain4 {
    public static void main(String[] args) {
        School2 s2 = new School2();
        s2.setName("ㄴㅁㄹㄴㅁ");
        System.out.println(s2.getName());
    }
}

class AccessMain5{
    static void run(){
        School2 s2 = new School2();

    }
}

class School2{


    private String name = "김치";

     String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}

