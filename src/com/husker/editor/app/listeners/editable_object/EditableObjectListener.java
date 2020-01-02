package com.husker.editor.app.listeners.editable_object;

import com.husker.editor.app.events.*;

public interface EditableObjectListener {
    void variableChanged(VariableChangedEvent event);
    void constantChanged(ConstantChangedEvent event);
    void selectedChanged(SelectedChangedEvent event);
    void newObject(NewEditableObjectEvent event);
    void objectRemoved(EditableObjectRemovedEvent event);
}
