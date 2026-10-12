package com.korai.study.practice;

import java.util.ArrayList;
import java.util.List;

public class AbstractMain04 {
    public static void main(String[] args) {

        Dog3 dog = new Dog3();
        Tiger3 tiger = new Tiger3();
        Animal3 animal = new Animal3();

        Animal3 animal1 = dog;
        Animal3 animal2 = tiger;

        animal1.move();
        dog.move();


        Dog3 animalToDog = (Dog3) animal1;
        ((Dog3)animal1).bark();



    }
}


class Animal3 {
    String name;

    void move(){
        System.out.println("움직인다.");
    }

}

class Dog3 extends Animal3{
    @Override
    void move(){
        System.out.println("개 움직인다.");
    }
    void bark(){
        System.out.println("짖다");
    }
}

class Tiger3 extends Animal3{

    void hunt(){
        System.out.println("사냥");
    }

}