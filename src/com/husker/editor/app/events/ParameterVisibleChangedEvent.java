package com.husker.editor.app.events;

import com.husker.editor.app.project.Parameter;
import com.husker.editor.app.project.Project;

public class ParameterVisibleChangedEvent{

    private Parameter parameter;
    private boolean visible;

    public ParameterVisibleChangedEvent(Parameter parameter, boolean visible) {
        this.visible = visible;
        this.parameter = parameter;
    }

    public Parameter getParameter(){
        return parameter;
    }
    public boolean isVisible(){
        return visible;
    }
}
