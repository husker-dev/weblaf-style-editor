package com.husker.editor.core.events;

import com.husker.editor.core.Parameter;

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
