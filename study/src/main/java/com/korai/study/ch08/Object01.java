package com.korai.study.ch08;

public class Object01 {
    public static void main(String[] args) {
        System.out.println(Student.class.getName());
        System.out.println(new Student().getClass().getName());
        System.out.println(new Student() instanceof Student);
        System.out.println(new Student().getClass() == Student.class);
        System.out.println(new Student().hashCode());
        Student s = new Student();
        System.out.println(s.hashCode());
        System.out.println(s.toString());
        System.out.println(s);
        String st1 = s.toString();
       // String st2 = s;  xx
        Student s2 = s;
        HighStudent hs1 = new HighStudent();
        System.out.println(hs1);
        System.out.println(st1);
    }
}

class Student extends Object{

}

class HighStudent extends Student{
  //  @Override
   // public String toString() {
   //     return "내 마음대로 재정의 가능";


    @Override
    public String toString() {
        return "객체가 가지고있는 데이터를 문자열로 시각화 할 때 사용";
    }
}

class Teacher{
    private  String name;
    private int age;
    private String address;

    public Teacher(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
