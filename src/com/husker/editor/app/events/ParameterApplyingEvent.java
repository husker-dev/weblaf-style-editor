package com.husker.editor.app.events;

import com.husker.editor.app.project.EditableObject;
import com.husker.editor.app.project.Parameter;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.listeners.ProjectEvent;

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
