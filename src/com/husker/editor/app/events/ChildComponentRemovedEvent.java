package com.husker.editor.app.events;

import com.husker.editor.app.project.Project;
import com.husker.editor.app.component.StyleComponent;
import com.husker.editor.app.listeners.ProjectEvent;

public class ChildComponentRemovedEvent extends ProjectEvent {

    private StyleComponent parent, child;

    public ChildComponentRemovedEvent(Project project, StyleComponent parent, StyleComponent child) {
        super(project);
    }

    public StyleComponent getParent(){
        return parent;
    }

    public StyleComponent getChild(){
        return child;
    }
}
