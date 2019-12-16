package com.husker.editor.app.project;

public class StaticVariable {
    private String default_value;
    private String name;

    public Variable clone(){
        try{
            return (Variable) super.clone();
        }catch (Exception ex){}
        return null;
    }

    public StaticVariable(String name){
        this(name, "");
    }
    public StaticVariable(String name, String default_value){
        this.default_value = default_value;
        this.name = name;
    }

    public String getName(){
        return name;
    }
    public String getDefaultValue(){
        return default_value;
    }
    public Variable generateVariable(){
        return new Variable(name, default_value);
    }
}
