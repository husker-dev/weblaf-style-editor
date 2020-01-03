package com.husker.editor.app.events;

import com.husker.editor.app.project.Project;
import com.husker.editor.app.component.StyleComponent;
import com.husker.editor.app.listeners.ProjectEvent;

public class NewChildComponentEvent extends ProjectEvent {

    private StyleComponent parent, child;

    public NewChildComponentEvent(Project project, StyleComponent parent, StyleComponent child) {
        super(project);
        this.parent = parent;
        this.child = child;
    }

    public StyleComponent getParent(){
        return parent;
    }

    public StyleComponent getChild(){
        return child;
    }
}
