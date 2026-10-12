package com.korai.study.ch04;

public class ArrayMain03{


    public static void main(String[] args) {

        int[] nums = new int[100];
        int index = 0;
        while(index<100){
            if(index%2==0){
                nums[index] = index +1;
                index ++;
            }
            else {
                nums[index] = index +2;
                index ++;

            }

        }


        for(int index2 = 0;index2<100;index2++){
            nums[index2] = index2 +1;

        }


    }
}

