package com.korai.study.ch12;

import java.util.ArrayList;


class 내가만든예외 extends RuntimeException{
    public 내가만든예외(String message) {
        super(message);
    }
}


public class CollectionMain04 {
    public static void main(String[] args) {

        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(100);
        nums.add(200);
        nums.add(300);
        nums.add(400);
        nums.add(500);
        nums.add(600);
        nums.add(700);

        try {
            print(nums, nums.size());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("index 초과 예외처리");
            //e.printStackTrace();
        } catch (NullPointerException e){
            System.out.println("null 주소 오류 예외처리");
        } catch(내가만든예외 e){
            System.out.println("예외처리");
            System.out.println(e.getMessage());
        } catch(Exception e){
            System.out.println("예외처리");
            System.out.println(e.getMessage());
        }

        System.out.println("프로그램 정상 종료");


    }

    static void print(ArrayList<Integer> arr, int size) throws Exception{ //throw's' 여러개 예외처리가능
        System.out.println("리스트크기:" + arr.size());
        if(size==7){
            throw new RuntimeException(("내가 강제로 호출한예외"));
        }
        for(int i = 0; i < size; i++) {
            System.out.println(arr.get(i));
        }


    }
}


