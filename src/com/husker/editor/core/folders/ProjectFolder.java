package com.husker.editor.core.folders;

import com.husker.editor.core.Project;
import com.husker.editor.core.FolderElement;
import com.husker.editor.core.tools.Resources;

public class ProjectFolder extends FolderElement {

    public ProjectFolder(Project project) {
        super(project, project.getName(), Resources.getImageIcon("project.png"));
        setRemovable(false);
    }
}
