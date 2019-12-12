package com.husker.editor.app.project.listeners.project;

public class ProjectEvent {
    public enum Type {
        Changed,
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

    public ProjectEvent(Type type, Object... objects){
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
