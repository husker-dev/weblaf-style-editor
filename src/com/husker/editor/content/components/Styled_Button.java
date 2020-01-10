package com.husker.editor.content.components;

import com.alee.laf.button.WebButton;
import com.husker.editor.core.FolderElement;
import com.husker.editor.core.Project;
import com.husker.editor.content.StyleComponent;

import javax.swing.*;
import java.awt.*;

import static com.husker.editor.content.StyleComponent.Variables.*;

public class Styled_Button extends StyleComponent {

    public Styled_Button(Project project, FolderElement folder) {
        super(project, folder, "Button", "button");

        addImplementedParameters(
                KIT_BACKGROUND,
                KIT_BORDER,
                KIT_INNER_SHADOW,
                KIT_OUTER_SHADOW,
                KIT_SHAPE,
                KIT_BUTTON_CONTENT
        );
        setDefaultValue(SHAPE_ENABLED, "true");
        setDefaultValue(ROUND_FULL, "3");
        setDefaultValue(OUTER_SHADOW_WIDTH, "2");
        setDefaultValue(BORDER_COLOR, "170,170,170");
        setDefaultValue(BACKGROUND_TYPE, "Gradient");
        setDefaultValue(BUTTON_SHOW_ICON, "true");
        setDefaultValue(BUTTON_SHOW_TEXT, "true");
        setDefaultValue(BACKGROUND_ENABLED, "true");

        setDefaultValue(ROUND_LB, "3");
        setDefaultValue(ROUND_LT, "3");
        setDefaultValue(ROUND_RB, "3");
        setDefaultValue(ROUND_RT, "3");
    }

    public JComponent createPreviewComponent() {
        return new WebButton(getExampleText(), getExampleIcon());
    }
}
