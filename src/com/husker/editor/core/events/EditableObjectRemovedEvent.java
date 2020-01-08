package com.husker.editor.core.events;

import com.husker.editor.core.listeners.ProjectEvent;
import com.husker.editor.core.EditableObject;
import com.husker.editor.core.Project;

public class EditableObjectRemovedEvent extends ProjectEvent {

    private EditableObject object;

    public EditableObjectRemovedEvent(Project project, EditableObject object) {
        super(project);
        this.object = object;
    }

    public EditableObject getObject(){
        return object;
    }
}
