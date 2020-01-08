package com.husker.editor.core.listeners;

import com.husker.editor.core.Project;

public class ProjectEvent {

    private Project project;

    public ProjectEvent(Project project){
        this.project = project;
    }

    public Project getProject(){
        return project;
    }
}
