package com.husker.editor.app.component.components;

import com.husker.editor.app.project.Project;
import com.husker.editor.app.component.StyleComponent;
import com.alee.laf.label.WebLabel;

import java.awt.*;

public class Styled_Label extends StyleComponent {
    public Styled_Label(Project project) {
        super(project, "Label", "label");

        addImplementedParameters(Variables.KIT_BACKGROUND, Variables.KIT_BORDER, Variables.KIT_SHAPE);
    }

    public Component createPreviewComponent() {
        return new WebLabel(getExampleText(), getExampleIcon());
    }
}
