package com.korai.todo01;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class TodoController {

    @PostMapping("/api/todos")
    public void create(@RequestBody Todo todo){
        System.out.println(todo);
    }

}
