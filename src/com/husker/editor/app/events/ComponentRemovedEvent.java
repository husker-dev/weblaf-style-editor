package com.husker.editor.app.events;

import com.husker.editor.app.project.Project;
import com.husker.editor.app.project.StyleComponent;
import com.husker.editor.app.listeners.ProjectEvent;

public class ComponentRemovedEvent extends ProjectEvent {

    private StyleComponent component;

    public ComponentRemovedEvent(Project project, StyleComponent component) {
        super(project);
        this.component = component;
    }

    public StyleComponent getComponent(){
        return component;
    }
}
