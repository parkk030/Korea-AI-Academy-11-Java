package com.korai.study.ch05;

//조건문 if else switch
//반복 while for
//분기 break continue return

public class ControlMain {
    public static void main(String[] args) {
        boolean open = true;
        int score = 85;

        if(true) System.out.println("123");
        if(false) System.out.println("345");

        if(open) System.out.println("open");
        else System.out.println("close");

        if(score < 60) System.out.println("F");
        else if (score < 70) System.out.println("D");
        else if (score < 80) System.out.println("C");
        else if (score < 90) System.out.println("B");
        else System.out.println("A");

    }
}
