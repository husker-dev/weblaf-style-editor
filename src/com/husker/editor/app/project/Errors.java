package com.husker.editor.app.project;

import com.husker.editor.app.project.listeners.error.ErrorsEvent;
import com.husker.editor.app.project.listeners.error.ErrorsListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Errors {
    private static ArrayList<ErrorsListener> listeners = new ArrayList<>();

    public static void addListener(ErrorsListener listener){
        listeners.add(listener);
    }
    public static void doEvent(ErrorsEvent.Type event, Object... objects){
        doEvent(new ErrorsEvent(event, objects));
    }
    public static void doEvent(ErrorsEvent event){
        System.out.println("EVENT Errors: " + event.getType().toString());
        for(ErrorsListener listener : listeners)
            listener.event(event);
    }

    private LinkedHashMap<String, Error> errors = new LinkedHashMap<>();

    public void addError(Error error){
        if(errors.containsKey(error.getId()))
            removeError(error.getId());
        errors.put(error.getId(), error);
        doEvent(ErrorsEvent.Type.Added, error);
    }

    public Error[] getErrors(){
        ArrayList<Error> out = new ArrayList<>();
        for(Map.Entry<String, Error> entry : errors.entrySet())
            out.add(entry.getValue());
        return out.toArray(new Error[0]);
    }
    public Error getError(String id){
        return errors.get(id);
    }
    public void removeError(String id){
        if(errors.containsKey(id))
            doEvent(ErrorsEvent.Type.Removed, errors.remove(id));
    }

    public static class Current {
        public static void addError(Error error){
            if(Project.getCurrentProject() != null)
                Project.getCurrentProject().Errors.addError(error);
        }
        public static void removeError(String id){
            if(Project.getCurrentProject() != null)
                Project.getCurrentProject().Errors.removeError(id);
        }
    }
}
