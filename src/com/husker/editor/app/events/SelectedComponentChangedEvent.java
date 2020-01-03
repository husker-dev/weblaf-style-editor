package com.husker.editor.app.events;

import com.husker.editor.app.project.Project;
import com.husker.editor.app.component.StyleComponent;
import com.husker.editor.app.listeners.ProjectEvent;

public class SelectedComponentChangedEvent extends ProjectEvent {

    private StyleComponent old, selected;

    public SelectedComponentChangedEvent(Project project, StyleComponent old, StyleComponent selected) {
        super(project);
        this.old = old;
        this.selected = selected;
    }

    public StyleComponent getSelectedComponent(){
        return selected;
    }
    public StyleComponent getOldComponent(){
        return old;
    }
}
