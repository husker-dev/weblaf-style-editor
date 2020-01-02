package com.husker.editor.app.events;

import com.husker.editor.app.project.Project;
import com.husker.editor.app.listeners.ProjectEvent;

public class LastSkinAppliedEvent extends ProjectEvent {

    public LastSkinAppliedEvent(Project project) {
        super(project);
    }
}
