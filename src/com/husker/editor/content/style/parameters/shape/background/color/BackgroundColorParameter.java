package com.husker.editor.content.style.parameters.shape.background.color;

import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.ColorParameter;

import static com.husker.editor.content.style.StyleComponent.Variables.*;

public class BackgroundColorParameter extends ColorParameter {

    public BackgroundColorParameter(String group) {
        super("Color", group);

        StyleParameterUtils.editHead(this, head -> {
            StyleParameterUtils.checkHead(head, "painter.decorations.decoration.ColorBackground");

            if(getAppliedObject().isVariableCustom(BACKGROUND_COLOR))
                head.setParameterByPath("painter.decorations.decoration.ColorBackground", "color", getValue());
            else
                head.removeParameterByPath("painter.decorations.decoration.ColorBackground", "color");

            return head;
        });
    }
}
