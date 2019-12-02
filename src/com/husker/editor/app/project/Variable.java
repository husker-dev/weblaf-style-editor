package com.husker.editor.app.project;

public class Variable {

    String value = null;
    String default_value;

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
        return value == null;
    }
}
