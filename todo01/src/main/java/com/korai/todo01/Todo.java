package com.korai.todo01;

public class Todo {
    private String writter;
    private String date;
    private String content;

    public void setWritter(String writter) {
        this.writter = writter;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "writter='" + writter + '\'' +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
