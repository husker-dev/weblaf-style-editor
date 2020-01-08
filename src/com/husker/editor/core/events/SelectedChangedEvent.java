package com.husker.editor.core.events;

import com.husker.editor.core.EditableObject;
import com.husker.editor.core.Project;
import com.husker.editor.core.listeners.ProjectEvent;

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
