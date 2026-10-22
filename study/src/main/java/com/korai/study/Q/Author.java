package com.korai.study.Q;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data // get set eq hc tostring
public class Author {
    private String name;
    private String birth;
    private int a;

    public Author(){

    }

    public Author(String name) {
        this.name = name;
    }

    public Author(int a) {
        this.a = a;
    }

    @Setter
    @Getter
    class Publisher{
        private String name;
        private String address;
    }



}

class AuthorMain{
    public static void main(String[] args) {

    }
}
