package com.husker.editor.content.components;

import com.husker.editor.core.FolderElement;
import com.husker.editor.core.Project;
import com.husker.editor.core.StyleComponent;
import com.alee.laf.label.WebLabel;

import java.awt.*;

public class Styled_Label extends StyleComponent {
    public Styled_Label(Project project, FolderElement folder) {
        super(project, folder, "Label", "label");

        addImplementedParameters(Variables.KIT_BACKGROUND, Variables.KIT_BORDER, Variables.KIT_SHAPE);
    }

    public Component createPreviewComponent() {
        return new WebLabel(getExampleText(), getExampleIcon());
    }
}
