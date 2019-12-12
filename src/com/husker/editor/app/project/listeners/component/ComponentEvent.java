package com.husker.editor.app.project.listeners.component;

public class ComponentEvent {
    public enum Type {
        New,
        Removed,
        Selected_Changed,
        Style_Changed,
        New_Child,
        Removed_Child,
        Moved_Child
        ;
        public boolean oneOf(Type... events){
            for (Type e : events)
                if(name().equals(e.name()))
                    return true;
            return false;
        }
    }

    private Type event;
    private Object[] objects;

    public ComponentEvent(Type type, Object... objects){
        this.event = type;
        this.objects = objects;
    }

    public Type getType(){
        return event;
    }
    public Object[] getObjects(){
        return objects;
    }
}