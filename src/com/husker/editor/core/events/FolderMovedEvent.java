package com.husker.editor.core.events;

import com.husker.editor.core.FolderElement;
import com.husker.editor.core.Project;
import com.husker.editor.core.listeners.ProjectEvent;

public class FolderMovedEvent extends ProjectEvent {

    private FolderElement folder, from, to;

    public FolderMovedEvent(Project project, FolderElement folder, FolderElement from, FolderElement to) {
        super(project);
        this.folder = folder;
        this.from = from;
        this.to = to;
    }

    public FolderElement getFolder() {
        return folder;
    }

    public FolderElement getFrom() {
        return from;
    }
    public FolderElement getTo() {
        return to;
    }
}
