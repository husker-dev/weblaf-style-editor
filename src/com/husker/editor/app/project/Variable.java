package com.husker.editor.app.project;

public class Variable implements Cloneable {

    private String value = null;
    private String default_value;
    private String name;

    public Variable clone(){
        try{
            return (Variable) super.clone();
        }catch (Exception ex){}
        return null;
    }

    public Variable(String name){
        this(name, "");
    }
    public Variable(String name, String default_value){
        this.default_value = default_value;
        this.name = name;
    }
    public void setValue(String value){
        this.value = value;
    }
    public String getValue(){
        if(value == null)
            return default_value;
        return value;
    }
    public String getDefaultValue(){
        return default_value;
    }
    public boolean isDefaultValue(){
        if(value == null)
            return true;
        else
            return value.equals(default_value);
    }

    public String getName(){
        return name;
    }

    public boolean equals(String value){
        return getValue().equals(value);
    }
}
