package com.husker.editor.core.events;

import com.husker.editor.core.Project;
import com.husker.editor.core.listeners.ProjectEvent;

public class SkinAppliedEvent extends ProjectEvent {
    public SkinAppliedEvent(Project project) {
        super(project);
    }
}
