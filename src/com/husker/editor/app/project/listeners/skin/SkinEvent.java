package com.husker.editor.app.project.listeners.skin;

public class SkinEvent {
    public enum Type {
        Last_Applied,
        Skin_Applied,
        Skin_Applying,
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

    public SkinEvent(Type type, Object... objects){
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
