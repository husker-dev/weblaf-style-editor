package com.husker.editor.app.events;

import com.husker.editor.app.project.Project;
import com.husker.editor.app.component.StyleComponent;
import com.husker.editor.app.listeners.ProjectEvent;

public class ChildComponentMovedEvent extends ProjectEvent {

    private StyleComponent parent, child;
    private int from, to;

    public ChildComponentMovedEvent(Project project, StyleComponent parent, StyleComponent child, int from, int to) {
        super(project);
        this.parent = parent;
        this.child = child;
        this.from = from;
        this.to = to;
    }

    public StyleComponent getParent(){
        return parent;
    }
    public StyleComponent getChild(){
        return child;
    }
    public int getFrom(){
        return from;
    }
    public int getTo(){
        return to;
    }
}
