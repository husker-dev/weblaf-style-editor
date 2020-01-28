package com.husker.editor.core;

import com.husker.editor.core.events.*;
import com.husker.editor.core.listeners.contants.ConstantsAdapter;
import com.husker.editor.core.listeners.editable_object.*;
import com.husker.editor.core.xml.XMLHead;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class EditableObject implements Cloneable {

    // Static

    private static ArrayList<EditableObjectListener> listeners = new ArrayList<>();

    private static LinkedHashMap<StaticVariable, Parameter> parameters = new LinkedHashMap<>();
    private static ArrayList<Class<? extends EditableObject>> used_classes = new ArrayList<>();
    private static ImageIcon example_icon = new ImageIcon("bin/icon_example.png");
    private static final String example_text = "[ Text example_!?. 123 ]";

    public static void addEditableObjectListener(EditableObjectListener listener){
        listeners.add(listener);
    }
    public static ArrayList<EditableObjectListener> getListeners(){
        return listeners;
    }

    public static ImageIcon getExampleIcon(){
        return example_icon;
    }
    public static String getExampleText(){
        return example_text;
    }

    public static void addStaticParameter(StaticVariable variable, Parameter parameter){
        if(parameters.containsKey(variable))
            return;
        variable.setConstantType(parameter.getConstantType());
        parameter.bindVariable(variable);
        parameters.put(variable, parameter);
    }
    public static void addStaticParameter(String name, Parameter parameter){
        addStaticParameter(new StaticVariable(name), parameter);
    }
    public static void addStaticParameter(String name, String default_value, Parameter parameter){
        addStaticParameter(new StaticVariable(name, default_value), parameter);
    }

    public static StaticVariable getStaticVariable(String name){
        for(Map.Entry<StaticVariable, Parameter> entry : parameters.entrySet())
            if(entry.getKey().getName().equals(name))
                return entry.getKey();
        return null;
    }

    public static Parameter getStaticParameter(String variable_name){
        for(Map.Entry<StaticVariable, Parameter> entry : parameters.entrySet())
            if(entry.getKey().getName().equals(variable_name))
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

    public static <T extends EditableObject> EditableObject newInstance(Class<T> clazz, Project project, FolderElement folder) throws UnsupportedOperationException {
        try {
            return clazz.getDeclaredConstructor(Project.class, FolderElement.class).newInstance(project, folder);
        }catch (Exception ex){
            throw new UnsupportedOperationException(clazz.getName() + " hasn't constructor with parameter: \"" + Project.class.getSimpleName() + ", " +  FolderElement.class.getSimpleName() + "\"");
        }
    }

    // Variables
    private final ArrayList<Variable> implemented_variables = new ArrayList<>();
    private final Project project;
    private final String title;

    private boolean selected = false;

    private final FolderElement folder;
    private Preview preview = null;

    private ArrayList<Consumer<Variable>> variable_changed_listeners = new ArrayList<>();

    private String code;

    protected EditableObject(Class<? extends EditableObject> clazz, Project project, String title, FolderElement folder){
        this.project = project;
        this.title = title;

        this.folder = createFolder();
        this.folder.setObject(this);
        folder.addFolder(this.folder);

        if(!used_classes.contains(clazz)){
            initStaticParameters();
            used_classes.add(clazz);
        }

        Constants.addConstantListener(new ConstantsAdapter() {
            public void renamed(ConstantRenamedEvent event) {
                for(Variable variable : implemented_variables)
                    if (variable.getConstantType() == event.getConstantType() && variable.getConstant().equals(event.getOldName()))
                        setConstant(variable, event.getNewName());
            }
            public void removed(ConstantRemovedEvent event) {
                for(Variable variable : implemented_variables)
                    if (variable.getConstantType() == event.getConstantType() && variable.getConstant().equals(event.getConstant()))
                        setConstant(variable, "");
            }
            public void valueChanged(ConstantValueChangedEvent event) {
                for(Variable variable : implemented_variables)
                    if(variable.getConstantType() == event.getConstantType() && variable.getConstant().equals(event.getConstant()))
                        setCustomValue(variable, event.getValue());
            }
        });

        Main.event(EditableObject.class, listeners, listener -> listener.newObject(new NewEditableObjectEvent(project, this)));
    }

    // Abstract
    protected abstract void initStaticParameters();

    public abstract FolderElement createFolder();

    // Functions
    public Project getProject(){
        return project;
    }
    public String getTitle(){
        return title;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }
    public boolean isSelected(){
        return selected;
    }

    public void addImplementedParameter(String variable){
        for(Variable variable1 : implemented_variables)
            if(variable1.getName().equals(variable))
                return;
        for(Map.Entry<StaticVariable, Parameter> entry : parameters.entrySet())
            if (entry.getKey().getName().equals(variable))
                implemented_variables.add(entry.getKey().generateVariable(getProject()));
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
        implemented_variables.add(new Variable(getProject(), name, default_value, getStaticVariable(name).getConstantType()));
    }
    public void setDefaultValue(StaticVariable variable, String default_value){
        setDefaultValue(variable.getName(), default_value);
    }
    public void setDefaultValue(Variable variable, String default_value){
        setDefaultValue(variable.getName(), default_value);
    }

    public void setCustomValue(String name, String value){
        for(Variable variable : implemented_variables) {
            if (variable.getName().equals(name)) {
                if(variable.getValue().equals(value))
                    continue;
                variable.setValue(value);

                Main.event(EditableObject.class, listeners, listener -> listener.variableChanged(new VariableChangedEvent(project, this, variable)));
                for(int i = 0; i < variable_changed_listeners.size(); i++){
                    try{
                        variable_changed_listeners.get(i).accept(variable);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
    public void setCustomValue(StaticVariable variable, String default_value){
        setCustomValue(variable.getName(), default_value);
    }
    public void setCustomValue(Variable variable, String default_value){
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
        return getVariable(name).getConstantValue();
    }
    public String getVariableValue(StaticVariable variable){
        return getVariableValue(variable.getName());
    }

    public boolean isVariableCustom(StaticVariable variable){
        if(!isImplemented(variable))
            return false;
        return !getVariable(variable.getName()).isDefaultValue();
    }

    public boolean areVariablesCustom(StaticVariable... variables){
        for(StaticVariable variable : variables)
            if (isImplemented(variable) && isVariableCustom(variable))
                return true;
        return false;
    }

    public void applyParameterOnCustom(XMLHead head, String path, String parameter, StaticVariable variable){
        applyParameterOnCustom(head, path, parameter, variable, null);
    }
    public void applyParameterOnCustom(XMLHead head, String path, String parameter, StaticVariable variable, Predicate<XMLHead> predicate){
        if(!isImplemented(variable))
            return;
        if(isVariableCustom(variable)) {
            if(!head.containsHeadByPath(path, predicate))
                head.createHeadByPath(path);
            XMLHead new_head = head.getHeadByPath(path, predicate);
            new_head.setParameter(parameter, getVariableValue(variable));
        }
    }
    public void applyParameterOnCustom(XMLHead head, String parameter, StaticVariable variable){
        if(!isImplemented(variable))
            return;
        if(isVariableCustom(variable))
            head.setParameter(parameter, getVariableValue(variable));
    }
    public void createHeadOnCustom(XMLHead head, String path, StaticVariable variable){
        if(!isImplemented(variable))
            return;
        if(isVariableCustom(variable))
            head.createHeadByPath(path);
    }

    public void setConstant(Variable variable, String constant){
        setConstant(variable.getName(), constant);
    }
    public void setConstant(StaticVariable variable, String constant){
        setConstant(variable.getName(), constant);
    }
    public void setConstant(String variable_name, String constant){
        for(Variable variable : implemented_variables) {
            if (variable.getName().equals(variable_name)) {
                variable.setConstant(constant);

                Main.event(EditableObject.class, listeners, listener -> listener.constantChanged(new ConstantChangedEvent(project, this, variable)));
                Main.event(EditableObject.class, listeners, listener -> listener.variableChanged(new VariableChangedEvent(project, this, variable)));
                for(int i = 0; i < variable_changed_listeners.size(); i++){
                    try{
                        variable_changed_listeners.get(i).accept(variable);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    public void remove(){
        Main.event(EditableObject.class, listeners, listener -> listener.objectRemoved(new EditableObjectRemovedEvent(project, this)));
    }

    public FolderElement getFolder(){
        return folder;
    }

    public void setPreview(Preview preview){
        this.preview = preview;
    }
    public Preview getPreview(){
        return preview;
    }

    public void addVariableChangedListener(Consumer<Variable> listener){
        variable_changed_listeners.add(listener);
    }

    public void setCode(String code){
        this.code = code;
        Main.event(EditableObject.class, listeners, listener -> listener.codeChanged(new CodeChangedEvent(project, this, getCode())));
    }
    public String getCode(){
        return code;
    }
}
