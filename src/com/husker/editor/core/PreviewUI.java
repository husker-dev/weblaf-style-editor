package com.husker.editor.core;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PreviewUI {

    private JComponent content;
    private ArrayList<JComponent> tools = new ArrayList<>();
    private ArrayList<JComponent> right_tools = new ArrayList<>();

    public PreviewUI(){}
    public PreviewUI(JComponent content){
        setContent(content);
    }

    public void setContent(JComponent content){
        this.content = content;
    }

    public void addTool(JComponent component){
        tools.add(component);
    }
    public void addToolToRight(JComponent component){
        right_tools.add(component);
    }

    public JComponent getContent(){
        return content;
    }
    public JComponent[] getTools(){
        return tools.toArray(new JComponent[0]);
    }
    public JComponent[] getToolsToRight(){
        return right_tools.toArray(new JComponent[0]);
    }
}
