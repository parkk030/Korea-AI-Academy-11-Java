package com.korai.study.ch12;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class CollectionMain08 {
    public static void main(String[] args) {
        Map<String,String> prodectMap = new HashMap<>();
        prodectMap.put("prodectName","상품1");
        prodectMap.put("price","20000");
        prodectMap.put("CreateAt","2026-10-19");

        System.out.println(prodectMap.get("price"));

        Map<String,Object> student = new LinkedHashMap<>();
        student.put("name","일번");
        student.put("age",33);
        student.put("gender",true);

        System.out.println(student);

    }

}
