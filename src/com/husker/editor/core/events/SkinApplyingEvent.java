package com.husker.editor.core.events;

import com.husker.editor.core.Project;
import com.husker.editor.core.listeners.ProjectEvent;

public class SkinApplyingEvent extends ProjectEvent {
    public SkinApplyingEvent(Project project) {
        super(project);
    }
}
