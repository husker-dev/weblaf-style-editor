package com.husker.editor.core.events;

import com.husker.editor.core.Code;
import com.husker.editor.core.Project;
import com.husker.editor.core.listeners.ProjectEvent;

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
