package com.husker.editor.app.project;

public class Error {

    private String title;
    private String text;
    private String id;

    public Error(String id){
        this(id, "", "");
    }
    public Error(String id, String title){
        this(id, title, "");
    }
    public Error(String id, String title, String text){
        setText(text);
        setTitle(title);
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setText(String text){
        this.text = text;
    }
    public String getTitle(){
        return title;
    }
    public String getText(){
        return text;
    }
    public String getId(){
        return id;
    }
}
