package com.husker.editor.app.project;

import com.husker.editor.app.Parameter;
import com.husker.editor.app.components.Styled_Button;
import com.husker.editor.app.components.Styled_Label;
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
    String title;
    Project project;

    HashMap<String, String> values = new HashMap<>();

    public StyleComponent clone(Project project){
        try{
            StyleComponent component = (StyleComponent) super.clone();
            component.project = project;
            return component;
        }catch (Exception ex){}
        return null;
    }

    public StyleComponent(String title){
        this.title = title;
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

    public abstract XMLHead getCode(boolean preview);
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
