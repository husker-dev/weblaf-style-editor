package com.husker.editor.content.folders;

import com.husker.editor.core.Project;
import com.husker.editor.core.FolderElement;
import com.husker.editor.core.StyleComponent;
import com.husker.editor.core.tools.Resources;

public class StyleComponentFolder extends FolderElement {

    private StyleComponent component;

    public StyleComponentFolder(Project project, StyleComponent component) {
        super(project, component.getTitle(), Resources.getImageIcon("style.png"));
        this.component = component;
        component.addVariableChangedListener(variable -> {
            if(StyleComponent.Variables.ID.equals(variable))
                setTag(variable.getConstantValue());
        });
    }

    protected void onSelected() {
        getProject().setSelectedObject(component);
    }

    protected void onRemoved() {
        getProject().Components.removeComponent(component);
    }
}
