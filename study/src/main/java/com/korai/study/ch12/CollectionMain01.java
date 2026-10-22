package com.korai.study.ch12;


// collection
// 여러 데이터관리방법 *데이터의 중복허용x (index or keyword)
// 1.배열
// 2.리스트 -ArrayList
// 3. 세트 - HashSet -중복제거
// 4. 맵 - HashMap - 키값라벨

import java.util.ArrayList;

public class CollectionMain01 {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("안녕");
        strings.add("잘가");
        StringArrayList strList = new StringArrayList();
        strList.add("안녕");
        strList.add("잘가");

        System.out.println(strings);
        System.out.println(strList);

        System.out.println(strings.get(1));
        System.out.println(strList.get(1));

        strings.set(1,"잘가세요");
        strList.set(1,"잘가세요");

        System.out.println(strings.get(1));
        System.out.println(strList.get(1));

        String removeValue = strings.remove(0);
        System.out.println(removeValue);
        System.out.println(strings);

        String removeValue2 = strList.remove(0);
        System.out.println(removeValue);
        System.out.println(strList);

    }


}

class StringArrayList{
    private String[] list;

    public StringArrayList(){
        list = new String[0];
    }

    public void add(String str){
        String[] newArray = new String[list.length+1];
        for(int i=0;i<list.length;i++){
            newArray[i] = list[i];
        }
        newArray[newArray.length-1] = str;
        list = newArray;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for(int i=0;i<list.length;i++){
            builder.append(list[i]);
            if(list.length-1 !=i){
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    public String get(int index) {
        return list[index];
    }

    public void set(int index, String aa) {
        list[index]=aa;
    }

    public String remove(int index) {
        String[] newArray = new String[list.length-1];
        String value = list[index];
        for(int i=0;i<newArray.length;i++){
            if(i<index){
                newArray[i]=list[i];
            }
            else {
                newArray[i]=list[i+1];
            }
        }
        list = newArray;
        return value;
    }


}
