package com.husker.editor.app.events;

import com.husker.editor.app.project.Project;
import com.husker.editor.app.listeners.ProjectEvent;

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
