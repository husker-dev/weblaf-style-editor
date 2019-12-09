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
                KIT_SHAPE,
                KIT_BUTTON_CONTENT
        );
        setCustomValue(SHAPE_ENABLED, "true");
        setCustomValue(ROUND, "3");
        setCustomValue(OUTER_SHADOW_WIDTH, "2");
        setCustomValue(BORDER_COLOR, "170,170,170");
        setCustomValue(BACKGROUND_TYPE, "Gradient");
        setCustomValue(BUTTON_SHOW_ICON, "true");
        setCustomValue(BUTTON_SHOW_TEXT, "true");
    }

    public Component createPreviewComponent() {
        return new WebButton("It's a button with text");
    }
}
