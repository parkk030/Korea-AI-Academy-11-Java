package com.korai.study.practice;

public class AbstractMain02 {
    public static void main(String[] args) {

        Dog dog = new Dog();
        Tiger tiger = new Tiger();
        Animal a = new Animal();
        Animal a2 = dog;
        Animal a3 = tiger;
        tiger.move();

    }
}


class Animal {
    String name;

    void move(){
        System.out.println("움직인다.");
    }

}

class Dog extends Animal{

    void bark(){
        System.out.println("짖다");
    }
}

class Tiger extends Animal{

    void hunt(){
        System.out.println("사냥");
    }

}