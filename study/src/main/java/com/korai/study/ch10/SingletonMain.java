package com.korai.study.ch10;

public class SingletonMain {
    public static void main(String[] args) {
        StudentService studentService = StudentService.getInstance();
        studentService.laught();
    }
}

class StudentService{
    private static StudentService instance=null;

    private StudentService(){}

    public static StudentService getInstance() {
        if(instance == null){
            instance = new StudentService();
        }
        return instance;


    }
    public void laught(){
        System.out.println("하하");
    }

}
