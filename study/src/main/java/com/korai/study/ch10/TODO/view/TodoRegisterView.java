package com.korai.study.ch10.TODO.view;

import com.korai.study.ch10.TODO.router.RootRouter;
import com.korai.study.ch10.TODO.service.TodoService;

import java.util.Scanner;

public class TodoRegisterView implements View{
    private final TodoService todoService;
    private Scanner scanner;

    public TodoRegisterView(TodoService todoService) {
        this.todoService = todoService;
        scanner = new Scanner(System.in);
    }

    @Override
    public void show(){
        String content;
        System.out.println("[ 할 일 등록하기 ]");
        System.out.println("내용: ");
        content = scanner.nextLine();
        todoService.register(content);
        RootRouter.setCurrent("todo-list");

    }


}
