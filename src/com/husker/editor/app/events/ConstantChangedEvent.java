package com.husker.editor.app.events;

import com.husker.editor.app.project.EditableObject;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.project.Variable;
import com.husker.editor.app.listeners.ProjectEvent;

public class ConstantChangedEvent extends ProjectEvent {

    private EditableObject object;
    private Variable variable;

    public ConstantChangedEvent(Project project, EditableObject object, Variable variable){
        super(project);
        this.object = object;
        this.variable = variable;
    }

    public EditableObject getObject(){
        return object;
    }
    public Variable getVariable(){
        return variable;
    }
}
