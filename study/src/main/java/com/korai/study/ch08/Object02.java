package com.korai.study.ch08;

import java.util.Objects;

public class Object02 {
    public static void main(String[] args) {

        class Student{

            private String name;
            private int age;

            public Student(String name, int age) {
                this.name = name;
                this.age = age;
            }

            @Override
            public boolean equals(Object o) {
                if (o == null || getClass() != o.getClass()) return false;
                Student student = (Student) o;
                return age == student.age && Objects.equals(name, student.name);
            }

            @Override
            public int hashCode() {
                return Objects.hash(name, age);
            }
        }

        Student s1 = new Student("안녕",33);
        Student s2 = new Student("안녕",33);
        Student s3 = s1;

        boolean result = s1.equals(s2);
        boolean result2 = s1.equals(s3);

        System.out.println(result);
        System.out.println(result2);
        System.out.println(s1==s2);
        System.out.println(s1==s3);

        System.out.println(s1.hashCode() == s2.hashCode());

    }
}
