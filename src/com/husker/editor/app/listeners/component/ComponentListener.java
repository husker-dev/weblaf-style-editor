package com.husker.editor.app.listeners.component;

import com.husker.editor.app.events.*;

public interface ComponentListener {
    void newComponent(NewComponentEvent event);
    void removed(ComponentRemovedEvent event);
    void variableChanged(VariableChangedEvent event);
    void constantChanged(ConstantChangedEvent event);
    void selectedChanged(SelectedComponentChangedEvent event);

    // Child
    void newChildComponent(NewChildComponentEvent event);
    void childRemovedComponent(ChildComponentRemovedEvent event);
    void childMovedComponent(ChildComponentMovedEvent event);
}
