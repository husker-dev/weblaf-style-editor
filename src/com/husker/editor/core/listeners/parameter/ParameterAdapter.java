package com.husker.editor.core.listeners.parameter;

import com.husker.editor.core.events.ParameterApplyingEvent;
import com.husker.editor.core.events.ParameterValueChangedEvent;
import com.husker.editor.core.events.ParameterVisibleChangedEvent;
import com.husker.editor.core.events.ParametersChangedEvent;

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
