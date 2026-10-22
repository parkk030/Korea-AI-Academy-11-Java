package com.korai.study.ch12;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionMain07 {
    public static void main(String[] args) {
        Set<Integer> nums = new TreeSet<>();
        nums.add(10);
        nums.add(20);
        nums.add(30);
        nums.add(40);
        nums.add(50);
        nums.add(60);
        nums.add(70);
        nums.add(70);
        nums.add(70);
        System.out.println(nums.contains(70));
        System.out.println(nums.containsAll(List.of(30,50)));

        List<String> names = List.of(
                "일번",
                "이번",
                "삼번",
                "일번",
                "이번"
        );

        Set<String> nameSet = new HashSet<>();
        nameSet.addAll(names);
        System.out.println(nameSet);

        @Data
        @AllArgsConstructor
        class Student{
            String name;
            String address;
        }

        List<Student> students = List.of(
                new Student("안녕","부산"),
                new Student("안녕1","서울"),
                new Student("안녕2","경남"),
                new Student("안녕3","서울"),
                new Student("안녕4","창원")
        );

        Set<String> addressSet = new HashSet<>();

        Set<Student> abc = new HashSet<>();
        abc.add(new Student("안녕","부산"));
        abc.add(new Student("안녕2","부산"));
        abc.add(new Student("안녕3","부산"));
        abc.add(new Student("안녕4","부산"));
        abc.add(new Student("안녕5","부산"));

        System.out.println(abc);

        for(int i = 0; i < students.size(); i++){
            addressSet.add(students.get(i).address);
        }

        System.out.println(addressSet);
        System.out.println(students.stream().map(s -> s.getAddress()).collect(Collectors.toSet()));

        for(String addr : addressSet){
            System.out.println(addr);
        }
        Iterator<String> addr = addressSet.iterator();
        while(addr.hasNext()){
            System.out.println(addr.next());
        }


    }
}
