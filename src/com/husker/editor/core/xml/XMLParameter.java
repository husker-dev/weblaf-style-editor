package com.husker.editor.core.xml;

public class XMLParameter {

    private String name;
    private String value;
    private XMLHead parent;

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

    public void setParent(XMLHead head){
        parent = head;
    }
    public XMLHead getParent(){
        return parent;
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
