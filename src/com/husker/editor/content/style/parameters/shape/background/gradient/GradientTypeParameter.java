package com.husker.editor.content.style.parameters.shape.background.gradient;

import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.ComboParameter;

import static com.husker.editor.content.style.StyleComponent.Variables.*;

public class GradientTypeParameter extends ComboParameter {

    private static String[] elements = new String[]{"Linear", "Radial"};

    public GradientTypeParameter(String group) {
        super("Gradient type", group, elements);

        StyleParameterUtils.editHead(this, head -> {
            StyleParameterUtils.checkHead(head, "painter.decorations.decoration.GradientBackground");

            if(getAppliedObject().isVariableCustom(GRADIENT_TYPE)) {
                if(getAppliedObject().getVariableValue(GRADIENT_TYPE).equals("Linear"))
                    head.setParameterByPath("painter.decorations.decoration.GradientBackground", "type", "linear");
                else
                    head.setParameterByPath("painter.decorations.decoration.GradientBackground", "type", "radial");
            }else
                head.removeParameterByPath("painter.decorations.decoration.GradientBackground", "type");

            return head;
        });
    }
}
