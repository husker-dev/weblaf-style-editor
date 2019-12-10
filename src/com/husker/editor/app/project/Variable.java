package com.husker.editor.app.project;

public class Variable implements Cloneable {

    private String value = null;
    private String default_value;

    public Variable clone(){
        try{
            return (Variable) super.clone();
        }catch (Exception ex){}
        return null;
    }

    public Variable(){
        this("");
    }
    public Variable(String default_value){
        this.default_value = default_value;
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
}
