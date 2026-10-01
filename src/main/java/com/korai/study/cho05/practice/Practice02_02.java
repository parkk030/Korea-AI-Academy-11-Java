package com.korai.study.cho05.practice;

import java.util.Scanner;

public class Practice02_02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = 0;
        System.out.print("몇명의 이름을 입력할수있나요?");
        n = sc.nextInt();
        sc.nextLine();

        String[] names = new String[n];
        for(int i = 0; i<names.length;i++){
            System.out.print("이름: ");
        names[i] = sc.nextLine();
        }
        System.out.println("입력한 이름 정보");
        for(int i =0; i<names.length;i++){
            System.out.println(String.format("%d: %s", i+1,names[i]));
        }

    }
}
