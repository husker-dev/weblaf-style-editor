package com.husker.editor.app.listeners.parameter;

import com.husker.editor.app.events.ParameterApplyingEvent;
import com.husker.editor.app.events.ParameterValueChangedEvent;
import com.husker.editor.app.events.ParameterVisibleChangedEvent;
import com.husker.editor.app.events.ParametersChangedEvent;

public abstract class ParameterAdapter implements ParameterListener {
    public void visibleChanged(ParameterVisibleChangedEvent event) {

    }

    public void valueChanged(ParameterValueChangedEvent event) {

    }

    public void objectApplying(ParameterApplyingEvent event) {

    }
    public void parametersChanged(ParametersChangedEvent event){

    }
}
