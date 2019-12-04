package com.husker.editor.app.project;

import java.util.ArrayList;

public class Components {

    public static ArrayList<IComponentListener> listeners = new ArrayList<>();
    public enum ComponentEvent {
        New,
        Removed,
        Selected_Changed,
        Style_Changed,
        New_Child,
        Removed_Child,
        Moved_Child
        ;
        public boolean oneOf(ComponentEvent... events){
            for (ComponentEvent e : events)
                if(name().equals(e.name()))
                    return true;
            return false;
        }
    }

    public static void doEvent(ComponentEvent event, Object... objects){
        System.out.println("EVENT " + event.toString());
        for(IComponentListener listener : listeners)
            listener.event(event, objects);
    }
    public static void addListener(IComponentListener listener){
        listeners.add(listener);
    }

    ArrayList<StyleComponent> components = new ArrayList<>();
    StyleComponent selected = null;

    public void addComponent(StyleComponent component){
        components.add(component);
        doEvent(ComponentEvent.New, component);
    }
    public void removeComponent(StyleComponent component){
        components.remove(component);
        if(component == selected)
            setSelectedComponent(null);
        doEvent(ComponentEvent.Removed, component);
    }

    public ArrayList<StyleComponent> getComponents(){
        return components;
    }

    public void setSelectedComponent(StyleComponent component){
        selected = component;
        doEvent(ComponentEvent.Selected_Changed, component);
    }
    public StyleComponent getSelectedComponent(){
        return selected;
    }

    public void moveComponent(int from, int to){
        StyleComponent component = components.get(from);
        components.remove(from);
        components.add(to, component);
    }

    public interface IComponentListener {
        void event(Components.ComponentEvent event, Object... objects);
    }

}
