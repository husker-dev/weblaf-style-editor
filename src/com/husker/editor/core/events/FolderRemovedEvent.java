package com.husker.editor.core.events;

import com.husker.editor.core.FolderElement;
import com.husker.editor.core.Project;
import com.husker.editor.core.listeners.ProjectEvent;

public class FolderRemovedEvent extends ProjectEvent {

    private FolderElement folder;

    public FolderRemovedEvent(Project project, FolderElement folder) {
        super(project);
        this.folder = folder;
    }

    public FolderElement getFolder() {
        return folder;
    }
}
