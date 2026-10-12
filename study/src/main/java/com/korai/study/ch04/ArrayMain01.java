package com.korai.study.ch04;

public class ArrayMain01 {
    public static void main(String[] args) {

        byte[] a1 = new byte[4];
        short[] a2 = new short[4];
        int[] a4 = new int[4];

        a1 = new byte[6];
        a1 = null;
        int num = 10;
        num = 0;

        class Student {
            String name;
            double[] scores;
        }

        Student s = new Student();
        s.name = "123";
        s.scores = new double[3];
        s.scores[0] = 70.0;

        Student[] students = new Student[4];
        students[0] = new Student();
        students[0].scores = new double[3];
        students[0].name = "가나다";
        students[0].scores[2] = 80.0;
        students[1] = new Student();
        students[1].name = "가나다라";

        Student[] students2 = students;
        students2[0] = s;


    }




}
