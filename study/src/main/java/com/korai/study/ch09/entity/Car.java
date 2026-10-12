package com.korai.study.ch09.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;


//entity class (정보저장클래스)
// 생성자 , getter, setter ,equals, hashcode, toString

@AllArgsConstructor
@Data // getter, setter ,equals, hashcode, toString 를 자동생성해줌
public class Car {

    private Long id;
    private String number;
    private String model;
    private String owner;


}
