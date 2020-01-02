package com.husker.editor.app.events;

import com.husker.editor.app.listeners.ProjectEvent;
import com.husker.editor.app.project.EditableObject;
import com.husker.editor.app.project.Project;

public class NewEditableObjectEvent extends ProjectEvent {

    private EditableObject object;

    public NewEditableObjectEvent(Project project, EditableObject object) {
        super(project);
        this.object = object;
    }

    public EditableObject getObject(){
        return object;
    }
}
