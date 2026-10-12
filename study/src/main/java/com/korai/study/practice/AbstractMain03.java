package com.korai.study.practice;

import java.util.ArrayList;
import java.util.List;

public class AbstractMain03 {
    public static void main(String[] args) {

        Dog2 dog = new Dog2();
        Tiger2 tiger = new Tiger2();

        Animal2 animal1 = dog;
        Animal2 animal2 = tiger;

        dog.move();
        animal1.move();

        ((Dog2)animal1).bark();

        List<Animal2> zoo = new ArrayList<>();
        zoo.add(new Dog2());
        zoo.add(new Tiger2());


        for (int i=0; i<zoo.size();i++){
            zoo.get(i).move();

            if(zoo.get(i) instanceof Dog2){
                Dog2 d = (Dog2)zoo.get(i);
                d.bark();
            }

            if(zoo.get(i) instanceof Tiger2){
                Tiger2 t = (Tiger2)zoo.get(i);
                t.hunt();
            }


        }

        for(Animal2 animals : zoo){
            animals.move();
        }
        dog.bark();
        tiger.hunt();

    }
}


class Animal2 {
    String name;

    void move(){
        System.out.println("움직인다.");
    }

}

class Dog2 extends Animal2{
    @Override
    void move(){
        System.out.println("개 움직인다.");
    }
    void bark(){
        System.out.println("짖다");
    }
}

class Tiger2 extends Animal2{

    void hunt(){
        System.out.println("사냥");
    }

}