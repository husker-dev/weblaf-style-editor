package com.husker.editor.app.components;

import com.husker.editor.app.project.Parameter;
import com.husker.editor.app.project.StyleComponent;
import com.husker.editor.app.xml.XMLHead;
import com.alee.laf.label.WebLabel;

import java.awt.*;

public class Styled_Label extends StyleComponent {
    public Styled_Label() {
        super("Label", "label");
    }

    public Parameter[] getCustomParameters() {
        return null;
    }

    public XMLHead[] getStyleContent() {
        return null;
    }

    public Component createPreviewComponent() {
        return new WebLabel("It's a label with TEXT!");
    }
}
