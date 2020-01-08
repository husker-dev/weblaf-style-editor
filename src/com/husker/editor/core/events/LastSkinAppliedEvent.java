package com.husker.editor.core.events;

import com.husker.editor.core.Project;
import com.husker.editor.core.listeners.ProjectEvent;

public class LastSkinAppliedEvent extends ProjectEvent {

    public LastSkinAppliedEvent(Project project) {
        super(project);
    }
}
