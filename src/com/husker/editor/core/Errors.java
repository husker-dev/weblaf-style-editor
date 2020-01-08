package com.husker.editor.core;

import com.husker.editor.core.events.ErrorAddedEvent;
import com.husker.editor.core.events.ErrorRemovedEvent;
import com.husker.editor.core.listeners.error.ErrorsListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Errors {
    private static ArrayList<ErrorsListener> listeners = new ArrayList<>();

    public static void addErrorsListener(ErrorsListener listener){
        listeners.add(listener);
    }

    private LinkedHashMap<String, Error> errors = new LinkedHashMap<>();
    private Project project;

    public Errors(Project project){
        this.project = project;
    }

    public void addError(Error error){
        if(errors.containsKey(error.getId()))
            removeError(error.getId());
        errors.put(error.getId(), error);
        Main.event(Errors.class, listeners, listener -> listener.added(new ErrorAddedEvent(project, error)));
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
        if(errors.containsKey(id)) {
            Error removed = errors.remove(id);
            Main.event(Errors.class, listeners, listener -> listener.removed(new ErrorRemovedEvent(project, removed)));
        }
    }
}
