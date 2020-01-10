package com.husker.editor.core.events;

import com.husker.editor.core.EditableObject;
import com.husker.editor.core.Project;
import com.husker.editor.core.listeners.ProjectEvent;

public class CodeChangedEvent extends ProjectEvent {

    private String code;
    private EditableObject object;

    public CodeChangedEvent(Project project, EditableObject object, String code) {
        super(project);
        this.code = code;
        this.object = object;
    }

    public String getCode(){
        return code;
    }
    public EditableObject getObject(){
        return object;
    }
}
