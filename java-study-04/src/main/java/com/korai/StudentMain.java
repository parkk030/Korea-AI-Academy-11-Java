package com.korai;

public class StudentMain {
    public static void main(String[] args) {
        Student jun = new Student();
        Student jun2 = new Student();
        jun.name = "준";
        jun.age = 22;
        jun.gender= "남자";
        jun.address= "집";
        System.out.println(jun.name + " " + jun.age + " " + jun.gender + " " + jun.address);

        Student2 kim = new Student2();
        kim.age = "33";
        Student2 kim2 = new Student2();
        kim2.age = 33;
        System.out.println("33"+33);
        System.out.println((String)kim.age+(Integer)kim2.age);

        Student3<String,String> park = new Student3<>();
        park.age = "32";
        Student3<Integer,String> park2 = new Student3<>();
        park2.age = 32;
        System.out.println(park.age+park2.age);

    }
}
