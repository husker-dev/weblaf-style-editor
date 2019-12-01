package com.husker.editor.app.xml;

public class XMLParameter {

    String name = "parameter";
    String value = "value";

    public XMLParameter(String name, String value){
        this.name = name;
        this.value = value;
    }
    public XMLParameter(String name){
        this(name, "value");
    }
    public XMLParameter(){
        this("parameter");
    }

    public void setName(String name){
        this.name = name;
    }
    public void setValue(String value){
        this.value = value;
    }

    public String getName(){
        return name;
    }
    public String getValue(){
        return value;
    }

    public String toString(){
        return name + "=\"" + value + "\"";
    }
}
