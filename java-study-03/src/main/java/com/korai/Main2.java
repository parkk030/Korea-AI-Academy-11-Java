package com.korai;
class Animal{

}

class Dog extends Animal{

}


public class Main2 {
    public static void main(String[] args) {
        double b = 10.12;
        int a = (int)b;
        char c= (char) b;

        Animal a2 = new Dog();
        Dog b2 = (Dog)a2;
    }
}
