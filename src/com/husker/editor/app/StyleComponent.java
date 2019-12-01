package com.husker.editor.app;

import com.husker.editor.app.components.Styled_Button;
import com.husker.editor.app.components.Styled_Label;
import com.husker.editor.app.parameters.TextParameter;
import com.husker.editor.app.project.Components;
import com.husker.editor.app.project.Project;
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

    HashMap<String, String> values = new HashMap<>();

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
            else if(!getValue("id").equals(""))
                addXMLParameter("id", getValue("id"));
            if(getValue("extends") != null && !getValue("extends").equals(""))
                addXMLParameter("extends", getValue("extends"));
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

    public void setValue(String tag, String value){
        values.put(tag, value);
        Components.doEvent(Components.ComponentEvent.Style_Parameters_Changed);
    }
    public String getValue(String tag){
        return values.get(tag);
    }

    public void doEvent(Components.ComponentEvent event, Object... objects){
        Components.doEvent(event, this, objects);
    }
}
