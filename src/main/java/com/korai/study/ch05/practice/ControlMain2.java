package com.korai.study.ch05.practice;

public class ControlMain2 {
    public static void main(String[] args) {
        String star = "*";
/*
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < i+1; j++){
                System.out.print("*");
            }
            System.out.println();
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 5-i; j > 0; j--){
                System.out.print("*");
            }
            System.out.println();

        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5-i; j++) {
                System.out.print(" ");
            }

            for (int j = 0; j < i+1 ; j++) {
                System.out.print("*");
            }
                ;
            System.out.println();
        }



        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < i+1; j++) {
                System.out.print(" ");
            }

            for (int j = 0; j < 5-i ; j++) {
                System.out.print("*");
            }
            ;
            System.out.println();
        }


        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5-i; j++) {
                System.out.print(" ");
            }

            for (int j = 0; j < 2*i+1 ; j++) {
                System.out.print("*");
            }
            ;
            System.out.println();
        }

*/
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j < 5-i; j++) {
                System.out.print(" ");
            }

            for (int j = 0; j < 2*i+1 ; j++) {
                System.out.print("*");
            }
            ;
            System.out.println();
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < i+1; j++) {
                System.out.print(" ");
            }

            for (int j = 0; j < 2*(5-i)-1 ; j++) {
                System.out.print("*");
            }
            ;
            System.out.println();
        }





    }
}
