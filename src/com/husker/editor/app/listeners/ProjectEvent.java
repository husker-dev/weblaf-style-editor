package com.husker.editor.app.listeners;

import com.husker.editor.app.project.Project;

public class ProjectEvent {

    private Project project;

    public ProjectEvent(Project project){
        this.project = project;
    }

    public Project getProject(){
        return project;
    }
}
