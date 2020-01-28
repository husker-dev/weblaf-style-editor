package com.husker.editor.content.style.parameters;

import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.BooleanParameter;

import static com.husker.editor.content.style.StyleComponent.Variables.*;

public class DecorationsEnabledParameter extends BooleanParameter {
    public DecorationsEnabledParameter() {
        super("Decorations");

        StyleParameterUtils.editHead(this, head -> {

            if(getAppliedObject().isVariableCustom(DECORATIONS))
                head.setParameterByPath("painter.decorations.decoration", "visible", getValue());
            else
                head.removeParameterByPath("painter.decorations.decoration", "visible");

            return head;
        });
    }
}
