package com.husker.editor.content.style.parameters.shape.background;

import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.EditableObject;
import com.husker.editor.core.parameters.ComboParameter;

import static com.husker.editor.content.style.StyleComponent.Variables.*;

public class BackgroundTypeParameter extends ComboParameter {

    private static String[] elements = new String[]{"Gradient", "Color"};

    public BackgroundTypeParameter(String group) {
        super("Background", group, elements);

        addActionListener(() -> {
            if(getValue() == null)
                return;
            boolean gradient_visible = isVisible() && getValue().equals("Gradient");
            boolean color_visible = isVisible() && !gradient_visible;

            EditableObject.getStaticParameter(GRADIENT_TYPE).setVisible(gradient_visible);
            EditableObject.getStaticParameter(GRADIENT_TO).setVisible(gradient_visible);
            EditableObject.getStaticParameter(GRADIENT_FROM).setVisible(gradient_visible);
            EditableObject.getStaticParameter(GRADIENT_COLOR_1).setVisible(gradient_visible);
            EditableObject.getStaticParameter(GRADIENT_COLOR_2).setVisible(gradient_visible);
            EditableObject.getStaticParameter(BACKGROUND_COLOR).setVisible(color_visible);
        });

        StyleParameterUtils.editHead(this, head -> {

            if (getValue().equals("Gradient")) {
                head.removeHeadByPath("painter.decorations.decoration.ColorBackground");
                head.createHeadByPath("painter.decorations.decoration.GradientBackground");
            }
            if (getValue().equals("Color")) {
                head.removeHeadByPath("painter.decorations.decoration.GradientBackground");
                head.createHeadByPath("painter.decorations.decoration.ColorBackground");
            }

            return head;
        });
    }
}
