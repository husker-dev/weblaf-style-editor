package com.husker.editor.core.events;

import com.husker.editor.core.Project;
import com.husker.editor.core.listeners.ProjectEvent;

public class ProjectChangedEvent extends ProjectEvent {

    private Project old_project;

    public ProjectChangedEvent(Project project, Project old_project) {
        super(project);
        this.old_project = old_project;
    }

    public Project getOldProject(){
        return old_project;
    }
}
