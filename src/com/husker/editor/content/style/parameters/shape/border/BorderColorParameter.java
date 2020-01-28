package com.husker.editor.content.style.parameters.shape.border;

import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.ColorParameter;

import static com.husker.editor.content.style.StyleComponent.Variables.*;

public class BorderColorParameter extends ColorParameter {

    public BorderColorParameter(String group) {
        super("Color", group);

        StyleParameterUtils.editHead(this, head -> {
            StyleParameterUtils.checkHead(head, "painter.decorations.decoration.LineBorder");

            if(getAppliedObject().isVariableCustom(BORDER_COLOR))
                head.setParameterByPath("painter.decorations.decoration.LineBorder", "color", getValue());
            else
                head.removeParameterByPath("painter.decorations.decoration.LineBorder", "color");

            return head;
        });
    }
}
