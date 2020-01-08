package com.husker.editor.core.events;

import com.husker.editor.core.EditableObject;
import com.husker.editor.core.Project;
import com.husker.editor.core.listeners.ProjectEvent;

public class ChildObjectRemovedEvent extends ProjectEvent {

    private EditableObject parent, child;

    public ChildObjectRemovedEvent(Project project, EditableObject parent, EditableObject child) {
        super(project);
    }

    public EditableObject getParent(){
        return parent;
    }

    public EditableObject getChild(){
        return child;
    }
}
