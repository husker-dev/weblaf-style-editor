package com.husker.editor.app.events;

import com.husker.editor.app.project.Project;
import com.husker.editor.app.listeners.ProjectEvent;
import com.husker.editor.app.project.Error;

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
