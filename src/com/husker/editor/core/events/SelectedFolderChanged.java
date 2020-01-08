package com.husker.editor.core.events;

import com.husker.editor.core.FolderElement;
import com.husker.editor.core.Project;
import com.husker.editor.core.listeners.ProjectEvent;

public class SelectedFolderChanged extends ProjectEvent {

    private FolderElement folder, old;

    public SelectedFolderChanged(Project project, FolderElement folder, FolderElement old) {
        super(project);
        this.folder = folder;
        this.old = old;
    }

    public FolderElement getFolder(){
        return folder;
    }
    public FolderElement getOldFolder(){
        return old;
    }

}
