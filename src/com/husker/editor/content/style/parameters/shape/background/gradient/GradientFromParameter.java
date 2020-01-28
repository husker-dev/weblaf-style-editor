package com.husker.editor.content.style.parameters.shape.background.gradient;

import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.Point2DParameter;

import static com.husker.editor.content.style.StyleComponent.Variables.*;

public class GradientFromParameter extends Point2DParameter {

    public GradientFromParameter(String group) {
        super("Gradient from", group);

        StyleParameterUtils.editHead(this, head -> {
            StyleParameterUtils.checkHead(head, "painter.decorations.decoration.GradientBackground");

            if(getAppliedObject().isVariableCustom(GRADIENT_FROM))
                head.setParameterByPath("painter.decorations.decoration.GradientBackground", "from", getValue());
            else
                head.removeParameterByPath("painter.decorations.decoration.GradientBackground", "from");

            return head;
        });
    }
}
