package com.husker.editor.core.events;

import com.husker.editor.core.EditableObject;
import com.husker.editor.core.Project;
import com.husker.editor.core.Variable;
import com.husker.editor.core.listeners.ProjectEvent;

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
