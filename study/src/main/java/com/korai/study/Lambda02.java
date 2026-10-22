package com.korai.study;

public class Lambda02 {
    public static void main(String[] args) {
        Calcullater1 calcullater1 = () -> {
            System.out.println("명령하나");
            System.out.println("명령둘");
        };


        Calcullater2 calcullater2 = () -> {
            System.out.println("명령하나");
            System.out.println("명령둘");
            return 0;
        };

        Calcullater2 calcullater02 = () -> 10;


        Calcullater3 calcullater3 = (int num) -> {
            return 0;
        };

        Calcullater3 calcullater03 = (num) -> {
            return 0;
        };

        Calcullater3 calcullater003 = num-> num * 5;

        Calcullater4 calcullater4 = (int num1, int num2) -> {
            return num1+num2;
        };

        Calcullater4 calcullater04 = (num1,num2) -> num1 + num2;

        Calcullater4 calcullater004 = (a , b) -> a + b;

    }
}
@FunctionalInterface
interface Calcullater1{
    void calc();
}

@FunctionalInterface
interface Calcullater2{
    int calc();
}

@FunctionalInterface
interface Calcullater3 {
    int calc(int num);
}

@FunctionalInterface
interface Calcullater4{
    int calc(int num1, int num2);
}
