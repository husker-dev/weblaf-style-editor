package com.husker.editor.app.project;

import com.husker.editor.app.components.Styled_Button;
import com.husker.editor.app.components.Styled_Label;
import com.husker.editor.app.parameters.TextParameter;
import com.husker.editor.app.xml.XMLHead;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class StyleComponent implements Cloneable{

    public static HashMap<String, StyleComponent> components = new HashMap<String, StyleComponent>(){{
        put("Button", new Styled_Button());
        put("Label", new Styled_Label());
    }};

    String name;
    String type;
    String title;
    Project project;

    static TextParameter p_id;
    static TextParameter p_extends;

    HashMap<String, Variable> variables = new HashMap<>();

    ArrayList<StyleComponent> child_components = new ArrayList<>();

    public StyleComponent clone(Project project){
        try{
            StyleComponent component = (StyleComponent) super.clone();
            component.project = project;
            return component;
        }catch (Exception ex){}
        return null;
    }

    static {
        p_id = new TextParameter("Id","id");
        p_extends = new TextParameter("Extends", "extends");
    }

    public StyleComponent(String title, String type){
        this.title = title;
        this.type = type;
        addVariable("id", new Variable());
        addVariable("extends", new Variable());
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

    public abstract ArrayList<Parameter> getParameters();

    public ArrayList<Parameter> getDefaultParameters(){
        return new ArrayList<Parameter>(){{
            add(p_id);
            add(p_extends);
        }};
    }

    public XMLHead getStyleHead(boolean preview){
        return new XMLHead("style"){{
            addXMLParameter("type", type);
            if(preview)
                addXMLParameter("id", "preview");
            else if(!getVariable("id").equals(""))
                addXMLParameter("id", getVariableValue("id"));
            if(getVariable("extends") != null && !getVariable("extends").equals(""))
                addXMLParameter("extends", getVariableValue("extends"));
        }};
    }

    public XMLHead getXMLStyle(){
        return getXMLStyle(false);
    }
    public XMLHead getXMLStyle(boolean preview){
        XMLHead head = getStyleHead(preview);
        head.addXMLHead(getStyleContent());
        return head;
    }

    public abstract XMLHead getStyleContent();
    public abstract Component createPreviewComponent();

    public void addVariable(String name, Variable variable){
        variables.put(name, variable);
    }
    public void setVariable(String name, String value){
        variables.get(name).setValue(value);
        Components.doEvent(Components.ComponentEvent.Style_Changed);
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
}
