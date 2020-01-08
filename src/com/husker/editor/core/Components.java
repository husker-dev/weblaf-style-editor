package com.husker.editor.core;

import java.util.ArrayList;

public class Components {

    private ArrayList<StyleComponent> components = new ArrayList<>();
    private final Project project;

    public Components(Project project){
        this.project = project;
    }

    public void addComponent(Class<? extends StyleComponent> clazz, FolderElement folder){
        addComponent((StyleComponent) EditableObject.newInstance(clazz, project, folder));
    }
    public void addComponent(StyleComponent component){
        components.add(component);
    }
    public void removeComponent(StyleComponent component){
        if(component == project.getSelectedObject())
            project.setSelectedObject(null);
        components.remove(component);
        component.remove();
    }

    public ArrayList<StyleComponent> getComponents(){
        return components;
    }

}
