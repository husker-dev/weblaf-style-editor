package com.husker.editor.core.listeners.parameter;

import com.husker.editor.core.events.*;

public interface ParameterListener {
    void visibleChanged(ParameterVisibleChangedEvent event);
    void valueChanged(ParameterValueChangedEvent event);
    void objectApplying(ParameterApplyingEvent event);
    void parametersChanged(ParametersChangedEvent event);
}
