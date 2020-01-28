package com.husker.editor.content.style.components;

import com.alee.laf.button.WebButton;
import com.husker.editor.core.FolderElement;
import com.husker.editor.core.Project;
import com.husker.editor.content.style.StyleComponent;

import javax.swing.*;

import static com.husker.editor.content.style.StyleComponent.Variables.*;

public class StyledButton extends StyleComponent {

    public StyledButton(Project project, FolderElement folder) {
        super(project, folder, "Button", "button");

        addImplementedParameters(
                KIT_MARGIN,
                KIT_PADDING,
                KIT_BACKGROUND,
                KIT_BORDER,
                KIT_INNER_SHADOW,
                KIT_OUTER_SHADOW,
                KIT_SHAPE,
                KIT_BUTTON_CONTENT
        );
        setDefaultValue(PADDING_TYPE, "Custom");
        setDefaultValue(PADDING_LEFT, "2");
        setDefaultValue(PADDING_TOP, "4");
        setDefaultValue(PADDING_RIGHT, "2");
        setDefaultValue(PADDING_BOTTOM, "4");

        setDefaultValue(SHAPE_ENABLED, "true");
        setDefaultValue(ROUND_FULL, "3");
        setDefaultValue(OUTER_SHADOW_WIDTH, "2");
        setDefaultValue(BORDER_COLOR, "170,170,170");
        setDefaultValue(BACKGROUND_TYPE, "Gradient");
        setDefaultValue(BUTTON_SHOW_ICON, "true");
        setDefaultValue(BUTTON_SHOW_TEXT, "true");
        setDefaultValue(BACKGROUND_ENABLED, "true");

        setDefaultValue(GRADIENT_FROM, "0.0,0.0");
        setDefaultValue(GRADIENT_TO, "0.0,1.0");
        setDefaultValue(GRADIENT_COLOR_1, "255,255,255");
        setDefaultValue(GRADIENT_COLOR_2, "223,223,223");

        setDefaultValue(ROUND_LB, "3");
        setDefaultValue(ROUND_LT, "3");
        setDefaultValue(ROUND_RB, "3");
        setDefaultValue(ROUND_RT, "3");
    }

    public JComponent createPreviewComponent() {
        return new WebButton(getExampleText(), getExampleIcon());
    }
}
