package com.husker.editor.app.project;

import com.husker.editor.app.project.listeners.component.ComponentEvent;
import com.husker.editor.app.project.listeners.component.ComponentListener;

import java.util.ArrayList;

import static com.husker.editor.app.project.listeners.component.ComponentEvent.Type.*;

public class Components {

    private static ArrayList<ComponentListener> listeners = new ArrayList<>();

    public static void doEvent(ComponentEvent.Type event, Object... objects){
        doEvent(new ComponentEvent(event, objects));
    }
    public static void doEvent(ComponentEvent event){
        System.out.println("EVENT Components: " + event.getType().toString());
        for(ComponentListener listener : listeners)
            listener.event(event);
    }
    public static void addListener(ComponentListener listener){
        listeners.add(listener);
    }

    private ArrayList<StyleComponent> components = new ArrayList<>();
    private StyleComponent selected = null;

    public void addComponent(StyleComponent component){
        components.add(component);
        doEvent(New, component);
    }
    public void removeComponent(StyleComponent component){
        components.remove(component);
        if(component == selected)
            setSelectedComponent(null);
        doEvent(Removed, component);
    }

    public ArrayList<StyleComponent> getComponents(){
        return components;
    }

    public void setSelectedComponent(StyleComponent component){
        selected = component;
        doEvent(Selected_Changed, component);
    }
    public StyleComponent getSelectedComponent(){
        return selected;
    }

    public void moveComponent(int from, int to){
        StyleComponent component = components.get(from);
        components.remove(from);
        components.add(to, component);
    }

}
