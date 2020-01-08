package com.husker.editor.core.events;

import com.husker.editor.core.EditableObject;
import com.husker.editor.core.Parameter;
import com.husker.editor.core.Project;
import com.husker.editor.core.listeners.ProjectEvent;

public class ParameterApplyingEvent extends ProjectEvent {

    private Parameter parameter;
    private EditableObject object;

    public ParameterApplyingEvent(Project project, Parameter parameter, EditableObject object) {
        super(project);
        this.parameter = parameter;
        this.object = object;
    }

    public Parameter getParameter(){
        return parameter;
    }
    public EditableObject getObject(){
        return object;
    }
}
