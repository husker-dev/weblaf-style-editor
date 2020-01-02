package com.husker.editor.app.events;

import com.husker.editor.app.project.Code;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.listeners.ProjectEvent;

public class CodeChangedEvent extends ProjectEvent {

    private Code code;

    public CodeChangedEvent(Project project, Code code) {
        super(project);
        this.code = code;
    }

    public Code getCode(){
        return code;
    }
}
