package com.husker.editor.core;

public class StaticVariable {
    private String default_value;
    private String name;
    private Class<? extends Constant> constant_type;

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
    public Variable generateVariable(Project project){
        return new Variable(project, name, default_value, constant_type);
    }
    public void setConstantType(Class<? extends Constant> type){
        constant_type = type;
    }
    public Class<? extends Constant> getConstantType(){
        return constant_type;
    }

    public boolean equals(StaticVariable variable){
        if(variable == null)
            return false;
        return name.equals(variable.getName());
    }
    public boolean equals(Variable variable){
        if(variable == null)
            return false;
        return name.equals(variable.getName());
    }

}
