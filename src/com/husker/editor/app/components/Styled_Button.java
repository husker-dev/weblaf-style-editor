package com.husker.editor.app.components;

import com.alee.laf.button.WebButton;
import com.husker.editor.app.project.StyleComponent;

import java.awt.*;

import static com.husker.editor.app.project.StyleComponent.Parameters.*;

public class Styled_Button extends StyleComponent {

    public Styled_Button() {
        super("Button", "button");

        addImplementedParameters(
                KIT_BACKGROUND,
                KIT_BORDER,
                KIT_INNER_SHADOW,
                KIT_OUTER_SHADOW,
                KIT_SHAPE
        );
        setCustomVariable(ROUND, "3");
        setCustomVariable(OUTER_SHADOW_WIDTH, "2");
        setCustomVariable(BORDER_COLOR, "170,170,170");
        setCustomVariable(BACKGROUND_TYPE, "Gradient");

    }

    public Component createPreviewComponent() {
        return new WebButton("It's a button with text");
    }
}
