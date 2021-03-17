package com.yasin.realtimechatapp;

public class MessageModel {

    private String from;
    private String text;


    public MessageModel(String from, String text) {
        this.from = from;
        this.text = text;
    }

    public MessageModel(){}

    public String getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "from='" + from + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
