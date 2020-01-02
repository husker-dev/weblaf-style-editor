package com.husker.editor.app.listeners.project;

import com.husker.editor.app.events.ProjectChangedEvent;

public interface ProjectListener {
    void projectChanged(ProjectChangedEvent event);
}
