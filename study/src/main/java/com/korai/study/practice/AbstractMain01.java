package com.korai.study.practice;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AbstractMain01 {
    public static void main(String[] args) {




        ArrayList<String> names = new ArrayList<>();

        names.add("일번");
        names.add("이번");
        names.add("삼번");
        System.out.println(names);

        LinkedList<String> names2 = new LinkedList<>();

        names2.add("일번");
        names2.add("이번");
        names2.add("삼번");
        System.out.println(names2);

        double[][] doubles = new double[2][2];

        List<List<String>> lists = new ArrayList<>();

        lists.add(new ArrayList<>());
        lists.add(new LinkedList<>());
        lists.add(new ArrayList<>());

        lists.get(0).add("안녕");
        lists.get(1).add("잘가");
        lists.get(0).add("World");
        System.out.println(lists.get(0));
        System.out.println(lists.get(0).get(1));
        System.out.println(lists.get(1));
        lists.get(3).add("World");
        System.out.println(lists.get(3));

        List<List<String>> list1 = new ArrayList<>();
        list1.add(new ArrayList<>());
        list1.get(0).add("안녕");
        List<String> list2 = new ArrayList<>();

    }
}
