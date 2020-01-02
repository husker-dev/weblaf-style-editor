package com.husker.editor.app.listeners.parameter;

import com.husker.editor.app.events.*;

public interface ParameterListener {
    void visibleChanged(ParameterVisibleChangedEvent event);
    void valueChanged(ParameterValueChangedEvent event);
    void objectApplying(ParameterApplyingEvent event);
    void parametersChanged(ParametersChangedEvent event);
}
