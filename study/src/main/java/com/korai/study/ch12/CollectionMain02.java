package com.korai.study.ch12;

import lombok.AllArgsConstructor;

public class CollectionMain02 {
    public static void main(String[] args) {
        CustomLinkedList<String> list1 = new CustomLinkedList<>();
        list1.add("자바");
        list1.add("자바2");
        list1.add("자바3");
        list1.add("자바4");
        list1.add("자바5");
        System.out.println(list1);
        System.out.println(list1.get(4));
        list1.remove(4);
        System.out.println(list1);
        list1.add("자바6");
        System.out.println(list1);
        list1.remove(4);
        System.out.println(list1);
    }
}

//LinkedList -> 선형리스트(단방향)
// 노드연결방식 - 다른노드의 주소와 저장할값을 포함

class CustomLinkedList<E>{
    @AllArgsConstructor
    private static class Node<E>{
        E item; //data 저장
       // Node<E> prev; //이전
        Node<E> next; //다음
    }

    private Node<E> head;
    private Node<E> tail;
    private int size;


    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        Node<E> current = head;
        for(int i=0; i<size; i++){
            builder.append(current.item);
            if(i != size -1){
                builder.append(", ");
            }
            current = current.next;
        }
        builder.append("]");
        return builder.toString();


    }

    public void add(E e){
        Node<E> newNode = new Node<>(e,null);
        if(head==null){
            head=newNode;
            tail = head;
        }
        else{
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public E get(int index){


        if(!(index<size && index >-1)){
        throw new IndexOutOfBoundsException("범위를 초과");
        }

        Node<E> current = head;
        for(int i=0; i<index; i++){
            current = current.next;
        }
        return current.item;
    }

    public void remove(int index){

        if(index==0){
            head = head.next;
            if(size==1){
                head = null;
                tail = null;
            }
        }

        else{
            Node<E> current = head;
            Node<E> current2 = head;

            for(int i=0; i<index; i++){
                current2 = current;
                current = current.next;
            }
            if(index == size - 1){
                current.next = null;
            }
            else{
                current2.next = current.next;
            }

        }

    size-=1;
    }


}
