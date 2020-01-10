package com.husker.editor.core;

public class ErrorException extends Exception {

    private String title, text;

    public ErrorException(String title, String text){
        this.text = text;
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
    public String getText(){
        return text;
    }
}
