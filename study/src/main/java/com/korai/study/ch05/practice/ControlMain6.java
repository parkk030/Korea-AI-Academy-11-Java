package com.korai.study.ch05.practice;

import java.util.Arrays;
import java.util.Scanner;

public class ControlMain6 {
    public static void main(String[] args) {




        String[] names = new String[0];
        Scanner sc = new Scanner(System.in);
        System.out.println("이름 입력 프로그램");

        while(true){
            System.out.println("이름추가?");
            String yesorno=sc.nextLine();
            if(yesorno.equalsIgnoreCase("y")){
                System.out.print("이름:");
                String name = sc.nextLine();

                String[] newNames = new String[names.length + 1];
                for(int i=0;i<names.length;i++){
                    newNames[i] = names[i];
                }
                newNames[newNames.length-1] = name;
                names = newNames;

            }
            else if(yesorno.equalsIgnoreCase("n")){
                break;
            }
            else {
                System.out.println("다시입력");
            }
        }

        System.out.println(Arrays.toString(names));

    }
}
