package com.husker.editor.app.events;

import com.husker.editor.app.project.Project;
import com.husker.editor.app.listeners.ProjectEvent;

public class SkinAppliedEvent extends ProjectEvent {
    public SkinAppliedEvent(Project project) {
        super(project);
    }
}
