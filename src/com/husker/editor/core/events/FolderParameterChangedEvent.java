package com.husker.editor.core.events;

import com.husker.editor.core.FolderElement;
import com.husker.editor.core.Project;
import com.husker.editor.core.listeners.ProjectEvent;

public class FolderParameterChangedEvent extends ProjectEvent {

    public enum FolderParameter {
        TITLE, TAG, STATE, ICON
    }

    private FolderParameter parameter;
    private FolderElement element;

    public FolderParameterChangedEvent(Project project, FolderElement element, FolderParameter parameter) {
        super(project);
        this.element = element;
        this.parameter = parameter;
    }

    public FolderParameter getParameter(){
        return parameter;
    }
    public FolderElement getElement(){
        return element;
    }
}
