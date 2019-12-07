package com.husker.editor.app.project;

import com.husker.editor.app.components.Styled_Button;
import com.husker.editor.app.components.Styled_Label;
import com.husker.editor.app.parameters.BooleanParameter;
import com.husker.editor.app.parameters.TextParameter;
import com.husker.editor.app.xml.XMLHead;
import com.husker.editor.app.xml.XMLParameter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class StyleComponent implements Cloneable{

    public static HashMap<String, StyleComponent> components = new HashMap<String, StyleComponent>(){{
        put("Button", new Styled_Button());
        put("Label", new Styled_Label());
    }};

    String name;
    String type;
    String title;
    Project project;

    static TextParameter p_id, p_extends;
    static BooleanParameter p_decoration;

    HashMap<String, Variable> variables = new HashMap<>();

    ArrayList<StyleComponent> child_components = new ArrayList<>();

    public StyleComponent clone(Project project){
        try{
            StyleComponent component = (StyleComponent) super.clone();
            component.project = project;

            // Copying variables
            HashMap<String, Variable> new_variables = new HashMap<>();
            for(Map.Entry<String, Variable> entry : variables.entrySet())
                new_variables.put(entry.getKey() + "", entry.getValue().clone());
            component.variables = new_variables;

            return component;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    static {
        p_id = new TextParameter("Id","id");
        p_extends = new TextParameter("Extends", "extends");
        p_decoration = new BooleanParameter("Decorations", "decorations");
    }

    public StyleComponent(String title, String type){
        this.title = title;
        this.type = type;
        addVariable("id");
        addVariable("extends");
        addVariable("decorations", "true");
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public String getTitle(){
        return title;
    }

    public Project getProject(){
        return project;
    }

    public abstract Parameter[] getCustomParameters();

    public Parameter[] getDefaultParameters(){
        return new Parameter[]{
                p_id, p_extends, p_decoration
        };
    }

    public XMLHead getStyleHead(boolean preview){
        return new XMLHead("style"){{
            addXMLParameter("type", type);
            if(preview)
                addXMLParameter("id", "preview");
            else if(!getVariable("id").isDefaultValue())
                addXMLParameter("id", getVariableValue("id"));
            if(!getVariable("extends").isDefaultValue())
                addXMLParameter("extends", getVariableValue("extends"));
        }};
    }

    public XMLHead getXMLStyle(){
        return getXMLStyle(false);
    }
    public XMLHead getXMLStyle(boolean preview){
        XMLHead head = getStyleHead(preview);
        XMLHead[] custom = getStyleContent();
        if(custom != null)
            for(XMLHead content : custom)
                head.addXMLHead(content);

        // Decorations
        if(getVariableValue("decorations").equals("false"))
            head.setParameterByPath("painter.decorations.decoration", new XMLParameter("visible", "false"));


        return head;
    }

    public abstract XMLHead[] getStyleContent();
    public abstract Component createPreviewComponent();

    public void addVariable(String name, Variable variable){
        variables.put(name, variable);
    }
    public void addVariable(String name, String default_value){
        this.addVariable(name, new Variable(default_value));
    }
    public void addVariable(String name){
        this.addVariable(name, new Variable());
    }
    public void setVariable(String name, String value){
        variables.get(name).setValue(value);
        Components.doEvent(Components.ComponentEvent.Style_Changed, this);
    }
    public Variable getVariable(String name){
        return variables.get(name);
    }
    public String getVariableValue(String name){
        return variables.get(name).getValue();
    }

    public void doEvent(Components.ComponentEvent event, Object... objects){
        Components.doEvent(event, this, objects);
    }

    public void addChildComponent(StyleComponent component){
        child_components.add(component);
        doEvent(Components.ComponentEvent.New_Child, component);
    }
    public ArrayList<StyleComponent> getChildComponents(){
        return child_components;
    }
    public void removeChild(StyleComponent component){
        removeChild(getChildComponents().indexOf(component));
    }
    public void removeChild(int index){
        child_components.remove(index);
        doEvent(Components.ComponentEvent.Removed_Child);
    }
    public void moveChildComponent(int from, int to){
        StyleComponent component = child_components.get(from);
        child_components.remove(component);
        child_components.add(to, component);
    }

    public boolean areVariablesDefault(String... variables){
        for(String variable : variables)
            if(!this.variables.get(variable).isDefaultValue())
                return false;
        return true;
    }

    public Parameter[] getParameters(){
        ArrayList<Parameter> both = new ArrayList<>();

        Parameter[] default_parameters = getDefaultParameters();
        Parameter[] custom_parameters = getCustomParameters();
        if(default_parameters != null)
            both.addAll(Arrays.asList(default_parameters));
        if(custom_parameters != null)
            both.addAll(Arrays.asList(custom_parameters));

        return both.toArray(new Parameter[0]);
    }
}
