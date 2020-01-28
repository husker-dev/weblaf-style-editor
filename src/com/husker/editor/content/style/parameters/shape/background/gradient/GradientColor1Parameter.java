package com.husker.editor.content.style.parameters.shape.background.gradient;

import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.ColorParameter;
import com.husker.editor.core.xml.XMLHead;

import static com.husker.editor.content.style.StyleComponent.Variables.*;

public class GradientColor1Parameter extends ColorParameter {

    public GradientColor1Parameter(String group) {
        super("Color 1", group);

        StyleParameterUtils.editHead(this, head -> {
            StyleParameterUtils.checkHead(head, "painter.decorations.decoration.GradientBackground");

            if(getAppliedObject().areVariablesCustom(GRADIENT_COLOR_1, GRADIENT_COLOR_2)){
                String color1 = getAppliedObject().getVariableValue(GRADIENT_COLOR_1);
                String color2 = getAppliedObject().getVariableValue(GRADIENT_COLOR_2);
                head.createHeadByPath("painter.decorations.decoration.GradientBackground", new XMLHead("color", color1));
                head.createHeadByPath("painter.decorations.decoration.GradientBackground", new XMLHead("color", color2));
            }else
                head.removeHeadsByPath("painter.decorations.decoration.GradientBackground.color");

            return head;
        });
    }
}
