package com.husker.editor.app.events;

import com.husker.editor.app.project.Project;
import com.husker.editor.app.listeners.ProjectEvent;

public class SkinApplyingEvent extends ProjectEvent {
    public SkinApplyingEvent(Project project) {
        super(project);
    }
}
