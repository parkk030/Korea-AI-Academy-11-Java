package com.korai.study.ch12;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CollectionMain06 {
    public static void main(String[] args) {
        A4.run();

    }
}


class A1{
    static void run(){
        ArrayList<String> strList = new ArrayList<>();

        strList.add("데이터1");

        strList.addAll(List.of("데이터2","데이터3","데이터4"));
        strList.addFirst("데이터5");
        strList.addLast("데이터6");
        strList.add(2,"데이터8");


        System.out.println(strList);


    }
}

class A2{
    static void run(){
        ArrayList<String> strList = new ArrayList<>();
        strList.addAll(List.of("데이터1","데이터2","데이터3","데이터4"));
        strList.remove(3);
        strList.remove("데이터2");
        System.out.println(strList);
        strList.addAll(List.of("데이터11","데이터22","데이터33","데이터44"));
        System.out.println(strList);
        strList.removeAll(List.of("데이터11","데이터22"));
        System.out.println(strList);
        strList.removeIf(d -> d.contains("3"));
        System.out.println(strList);
       /* strList.removeIf(new Predicate<String>() {
            @Override
            public boolean test(String d) {
                return d.contains("3");
            }
        });*/




    }
}


class A3{
    static void run(){
        List<String> strList = List.of("데이터1","데이터2","데이터3");
        String a = strList.get(2);
        System.out.println(a);
    }
}


class A4{
    static void run(){
        List<String> strList = List.of("데이터1","데이터2","데이터3");
        boolean isContained = strList.contains("데이터2");
        System.out.println(isContained);
        boolean isContained2 = strList.get(1).contains("2");
        System.out.println(isContained2);
    }
}