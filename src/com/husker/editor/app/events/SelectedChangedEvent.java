package com.husker.editor.app.events;

import com.husker.editor.app.project.EditableObject;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.listeners.ProjectEvent;

public class SelectedChangedEvent extends ProjectEvent {

    private EditableObject object;

    public SelectedChangedEvent(Project project, EditableObject object){
        super(project);
        this.object = object;
    }

    public EditableObject getObject(){
        return object;
    }
}
