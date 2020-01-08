package com.husker.editor.core.listeners.editable_object;

import com.husker.editor.core.events.*;

public abstract class EditableObjectAdapter implements EditableObjectListener {
    public void variableChanged(VariableChangedEvent event) {}
    public void constantChanged(ConstantChangedEvent event) {}
    public void selectedChanged(SelectedChangedEvent event) {}
    public void newObject(NewEditableObjectEvent event) {}
    public void objectRemoved(EditableObjectRemovedEvent event) {}
    public void newChildComponent(NewChildObjectEvent event) {}
    public void childRemovedComponent(ChildObjectRemovedEvent event) {}
}
