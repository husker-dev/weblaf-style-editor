package app;

import java.util.ArrayList;

public class Components {

    public static ArrayList<IComponentListener> listeners = new ArrayList<>();
    public enum ComponentEvent {
        New_Component,
        Removed_Component,
        Selected_Component_Changed,
        Style_Parameters_Changed
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
        doEvent(ComponentEvent.New_Component, component);
    }
    public void removeComponent(StyleComponent component){
        components.remove(component);
        if(component == selected)
            setSelectedComponent(null);
        doEvent(ComponentEvent.Removed_Component, component);
    }

    public ArrayList<StyleComponent> getComponents(){
        return components;
    }

    public void setSelectedComponent(StyleComponent component){
        selected = component;
        doEvent(ComponentEvent.Selected_Component_Changed, component);
    }
    public StyleComponent getSelectedComponent(){
        return selected;
    }

    public interface IComponentListener {
        void event(Components.ComponentEvent event, Object... objects);
    }

}
