package com.husker.editor.core.listeners.editable_object;

import com.husker.editor.core.events.*;

public interface EditableObjectListener {
    void variableChanged(VariableChangedEvent event);
    void constantChanged(ConstantChangedEvent event);
    void selectedChanged(SelectedChangedEvent event);
    void newObject(NewEditableObjectEvent event);
    void objectRemoved(EditableObjectRemovedEvent event);

    // Child
    void newChildComponent(NewChildObjectEvent event);
    void childRemovedComponent(ChildObjectRemovedEvent event);
}
