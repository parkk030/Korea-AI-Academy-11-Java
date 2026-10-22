package com.korai.study.ch12;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
class Car{
    private String model;
}

public class CollectionMain03 {
    public static void main(String[] args) {
        int[] nums = new int[5];
        nums[0]=100;
        nums[1]=200;
        nums[2]=300;
        nums[3]=400;
        nums[4]=500;

        print(nums);

        String[] names = {"일","이","삼","사","오"};

        print(names);



        Car[] cars = new Car[5];
        cars[0] = new Car("일번마");
        cars[1] = new Car("이번마");
        cars[2] = new Car("삼번마");
        cars[3] = new Car("사번마");
        cars[4] = new Car("오번마");

        print(cars);

        int total = 0;
        for (int i = 1; i <= 20; i++) {
            if (i % 4 == 0) continue;
            if (total > 30) break;
            total += i;
        }
        System.out.println(total);


    }

    static void print(Car []arr){
        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }

    static void print(int []arr){
        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }

    static void print(Object[] arr){ //최상위 object로
        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }

    static void print(String []arr){
        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }

}






