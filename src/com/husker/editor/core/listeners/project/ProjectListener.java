package com.husker.editor.core.listeners.project;

import com.husker.editor.core.events.ProjectChangedEvent;

public interface ProjectListener {
    void projectChanged(ProjectChangedEvent event);
}
