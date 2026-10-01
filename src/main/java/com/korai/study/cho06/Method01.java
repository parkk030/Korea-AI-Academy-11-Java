package com.korai.study.cho06;

public class Method01 {
    public static void main(String[] args) {
        Method0101 a = new Method0101();
        a.run();
        Method0102.run();

    }
}

class Method0101{
    void run(){
        System.out.println("1");
    }

}

class Method0102{
    static void run(){
        System.out.println("2");
    }
}
