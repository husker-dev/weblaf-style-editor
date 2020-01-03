package com.husker.editor.app.project;

import com.husker.editor.app.Main;
import com.husker.editor.app.component.StyleComponent;
import com.husker.editor.app.events.ComponentRemovedEvent;
import com.husker.editor.app.events.NewComponentEvent;
import com.husker.editor.app.listeners.component.ComponentListener;

import java.util.ArrayList;

public class Components {

    private static ArrayList<ComponentListener> listeners = new ArrayList<>();

    public static void addComponentListener(ComponentListener listener){
        listeners.add(listener);
    }

    private ArrayList<StyleComponent> components = new ArrayList<>();
    private final Project project;

    public Components(Project project){
        this.project = project;
    }

    public void addComponent(StyleComponent component){
        components.add(component);
        Main.event(Components.class, listeners, listener -> listener.newComponent(new NewComponentEvent(project, component)));
    }
    public void removeComponent(StyleComponent component){
        components.remove(component);
        component.remove();
        if(component == project.getSelectedObject())
            project.setSelectedObject(null);
        Main.event(Components.class,listeners, listener -> listener.removed(new ComponentRemovedEvent(project, component)));
    }

    public ArrayList<StyleComponent> getComponents(){
        return components;
    }

    public void moveComponent(int from, int to){
        StyleComponent component = components.get(from);
        components.remove(from);
        components.add(to, component);
    }

}
