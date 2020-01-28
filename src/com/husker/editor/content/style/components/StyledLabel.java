package com.husker.editor.content.style.components;

import com.husker.editor.core.FolderElement;
import com.husker.editor.core.Project;
import com.husker.editor.content.style.StyleComponent;
import com.alee.laf.label.WebLabel;

import javax.swing.*;

public class StyledLabel extends StyleComponent {
    public StyledLabel(Project project, FolderElement folder) {
        super(project, folder, "Label", "label");

        addImplementedParameters(Variables.KIT_BACKGROUND, Variables.KIT_BORDER, Variables.KIT_SHAPE);
    }

    public JComponent createPreviewComponent() {
        return new WebLabel(getExampleText(), getExampleIcon());
    }
}
