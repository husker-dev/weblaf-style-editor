package com.husker.editor.core.events;

import com.husker.editor.core.Project;
import com.husker.editor.core.listeners.ProjectEvent;
import com.husker.editor.core.Error;

public class ErrorAddedEvent extends ProjectEvent {

    private Error error;

    public ErrorAddedEvent(Project project, Error error) {
        super(project);
        this.error = error;
    }

    public Error getError(){
        return error;
    }
}
