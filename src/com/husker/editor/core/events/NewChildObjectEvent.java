package com.husker.editor.core.events;

import com.husker.editor.core.EditableObject;
import com.husker.editor.core.Project;
import com.husker.editor.core.listeners.ProjectEvent;

public class NewChildObjectEvent extends ProjectEvent {

    private EditableObject parent, child;

    public NewChildObjectEvent(Project project, EditableObject parent, EditableObject child) {
        super(project);
        this.parent = parent;
        this.child = child;
    }

    public EditableObject getParent(){
        return parent;
    }

    public EditableObject getChild(){
        return child;
    }
}
