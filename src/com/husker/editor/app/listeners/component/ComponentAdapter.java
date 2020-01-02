package com.husker.editor.app.listeners.component;

import com.husker.editor.app.events.*;

public abstract class ComponentAdapter implements ComponentListener {
    public void newComponent(NewComponentEvent event) {

    }

    public void removed(ComponentRemovedEvent event) {

    }

    public void variableChanged(VariableChangedEvent event) {

    }

    public void constantChanged(ConstantChangedEvent event) {

    }

    public void selectedChanged(SelectedComponentChangedEvent event) {

    }

    public void newChildComponent(NewChildComponentEvent event) {

    }

    public void childRemovedComponent(ChildComponentRemovedEvent event) {

    }

    public void childMovedComponent(ChildComponentMovedEvent event) {

    }

}
