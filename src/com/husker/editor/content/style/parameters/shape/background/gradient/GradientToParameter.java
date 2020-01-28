package com.husker.editor.content.style.parameters.shape.background.gradient;

import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.Point2DParameter;

import static com.husker.editor.content.style.StyleComponent.Variables.*;

public class GradientToParameter extends Point2DParameter {

    public GradientToParameter(String group) {
        super("Gradient to", group);

        StyleParameterUtils.editHead(this, head -> {
            StyleParameterUtils.checkHead(head, "painter.decorations.decoration.GradientBackground");

            if(getAppliedObject().isVariableCustom(GRADIENT_TO))
                head.setParameterByPath("painter.decorations.decoration.GradientBackground", "to", getValue());
            else
                head.removeParameterByPath("painter.decorations.decoration.GradientBackground", "to");

            return head;
        });
    }
}
