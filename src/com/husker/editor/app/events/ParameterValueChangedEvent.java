package com.husker.editor.app.events;

import com.husker.editor.app.project.Parameter;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.listeners.ProjectEvent;

public class ParameterValueChangedEvent{

    private Parameter parameter;
    private String old_value, new_value;

    public ParameterValueChangedEvent(Parameter parameter, String old_value, String new_value) {
        this.parameter = parameter;
        this.old_value = old_value;
        this.new_value = new_value;
    }

    public Parameter getParameter(){
        return parameter;
    }
    public String getOldValue(){
        return old_value;
    }
    public String getNewValue(){
        return new_value;
    }
}
