package com.husker.editor.app.project;

import com.husker.editor.app.xml.XMLHead;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractEditableObject implements Cloneable {

    private static LinkedHashMap<StaticVariable, Parameter> parameters = new LinkedHashMap<>();
    private static ArrayList<Class<? extends AbstractEditableObject>> used_classes = new ArrayList<>();

    static {
        Code.addActionListener(code -> {
            new Thread(() -> {
                if(!code.isEmpty()) {
                    try {
                        XMLHead head = XMLHead.fromString(code);
                        if (head == null)
                            throw new NullPointerException();
                        Project.getCurrentProject().Components.getSelectedComponent().applyXML(head);

                        Errors.Current.removeError("xml_reading");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Errors.Current.addError(new Error("xml_reading", "XML Reading error", "If you are sure that everything is correct, please contact the developer."));
                    }
                }else{
                    Errors.Current.removeError("xml_reading");
                }
            }).start();
        });
    }

    public static void addStaticParameter(StaticVariable variable, Parameter parameter){
        if(parameters.containsKey(variable))
            return;
        parameter.setVariableName(variable.getName());
        parameters.put(variable, parameter);
    }
    public static void addStaticParameter(String name, Parameter parameter){
        addStaticParameter(new StaticVariable(name), parameter);
    }
    public static void addStaticParameter(String name, String default_value, Parameter parameter){
        addStaticParameter(new StaticVariable(name, default_value), parameter);
    }
    public static StaticVariable getStaticVariable(String variable){
        for(Map.Entry<StaticVariable, Parameter> entry : parameters.entrySet())
            if(entry.getKey().getName().equals(variable))
                return entry.getKey();
        return null;
    }

    public static Parameter getStaticParameter(String variable){
        for(Map.Entry<StaticVariable, Parameter> entry : parameters.entrySet())
            if(entry.getKey().getName().equals(variable))
                return entry.getValue();
        return null;
    }
    public static Parameter getStaticParameter(StaticVariable variable){
        return getStaticParameter(variable.getName());
    }
    public static Parameter[] getStaticParameters(){
        ArrayList<Parameter> out = new ArrayList<>();
        for(Map.Entry<StaticVariable, Parameter> entry : parameters.entrySet())
            out.add(entry.getValue());
        return out.toArray(new Parameter[0]);
    }
    public static AbstractEditableObject newInstance(Class<? extends AbstractEditableObject> clazz, Project project) throws IllegalAccessException, InstantiationException{
        AbstractEditableObject object = clazz.newInstance();;
        object.project = project;
        return object;
    }

    private ArrayList<Variable> implemented_variables = new ArrayList<>();
    private Project project;
    private String title;

    protected AbstractEditableObject(Class cl, String title){
        this.title = title;
        if(!used_classes.contains(cl)){
            initParameters();
            used_classes.add(cl);
            Parameters.event();
        }
    }

    // Abstract
    protected abstract void initParameters();
    protected abstract void onVariableChanged(String variable);

    public XMLHead getXMLStyle(){
        return getXMLStyle(false);
    }
    public abstract XMLHead getXMLStyle(boolean preview);
    public abstract void applyXML(XMLHead code);

    public abstract Component createPreviewComponent();

    // Functions
    public Project getProject(){
        return project;
    }
    public String getTitle(){
        return title;
    }

    public void addImplementedParameter(String variable){
        for(Variable variable1 : implemented_variables)
            if(variable1.getName().equals(variable))
                return;
        for(Map.Entry<StaticVariable, Parameter> entry : parameters.entrySet())
            if (entry.getKey().getName().equals(variable))
                implemented_variables.add(entry.getKey().generateVariable());
    }
    public void addImplementedParameter(StaticVariable variable){
        addImplementedParameter(variable.getName());
    }
    public void addImplementedParameters(StaticVariable... variables){
        for(StaticVariable variable : variables)
            addImplementedParameter(variable);
    }
    public void addImplementedParameters(StaticVariable[]... variables){
        for(StaticVariable[] variable : variables)
            addImplementedParameters(variable);
    }
    public boolean isImplemented(String variable){
        for(Variable var : implemented_variables)
            if(var.getName().equals(variable))
                return true;
        return false;
    }
    public boolean isImplemented(StaticVariable variable){
        return isImplemented(variable.getName());
    }
    public Variable[] getImplementedVariables(){
        return implemented_variables.toArray(new Variable[0]);
    }

    public Parameter[] getParameters(){
        ArrayList<Parameter> param = new ArrayList<>();
        for(Map.Entry<StaticVariable, Parameter> entry : parameters.entrySet())
            for(Variable variable : implemented_variables)
                if(variable.getName().equals(entry.getKey().getName()))
                    param.add(entry.getValue());
        return param.toArray(new Parameter[0]);
    }
    public Parameter getParameter(String variable){
        if(!implemented_variables.contains(variable))
            return null;
        return getStaticParameter(variable);
    }

    public void setDefaultValue(String name, String default_value){
        implemented_variables.removeIf(variable -> variable.getName().equals(name));
        implemented_variables.add(new Variable(name, default_value));
    }
    public void setDefaultValue(StaticVariable variable, String default_value){
        setDefaultValue(variable.getName(), default_value);
    }

    public void setCustomValue(String name, String value){
        for(Variable variable : implemented_variables) {
            if (variable.getName().equals(name)) {
                variable.setValue(value);
                onVariableChanged(name);
            }
        }
    }
    public void setCustomValue(StaticVariable variable, String default_value){
        setCustomValue(variable.getName(), default_value);
    }
    public Variable getVariable(String name){
        for(Variable variable : implemented_variables)
            if(variable.getName().equals(name))
                return variable;
        return null;
    }
    public Variable getVariable(StaticVariable variable){
        return getVariable(variable.getName());
    }
    public String getVariableValue(String name){
        return getVariable(name).getValue();
    }
    public String getVariableValue(StaticVariable variable){
        return getVariableValue(variable.getName());
    }


}
