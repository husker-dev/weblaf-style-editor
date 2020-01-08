package com.husker.editor.core;

public class Variable implements Cloneable {

    private String value = null;
    private String default_value;
    private String name;
    private Class<? extends Constant> constType;
    private String constant = "";
    private Project project;

    public Variable clone(){
        try{
            return (Variable) super.clone();
        }catch (Exception ex){}
        return null;
    }

    public Variable(Project project, String name, Class<? extends Constant> constType){
        this(project, name, "", constType);
    }
    public Variable(Project project, String name, String default_value, Class<? extends Constant> constType){
        this.constType = constType;
        this.default_value = default_value;
        this.name = name;
        this.project = project;
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
        if(getConstantValue() == null)
            return true;
        else
            return getConstantValue().equals(default_value);
    }

    public String getName(){
        return name;
    }
    public Project getProject(){
        return project;
    }

    public boolean equals(String value){
        return getValue().equals(value);
    }

    public Class<? extends Constant> getConstantType(){
        return constType;
    }
    public void setConstant(String constant){
        this.constant = constant;
    }
    public String getConstant(){
        return constant;
    }
    public String getConstantValue(){
        if(constant.isEmpty())
            return getValue();
        else
            return getProject().Constants.getConstant(constType, constant);
    }
}
