package com.husker.editor.content.style.parameters.shape.round;

import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.IntegerParameter;

import static com.husker.editor.content.style.StyleComponent.Variables.*;

public class FullRoundParameter extends IntegerParameter {

    public FullRoundParameter(String group) {
        super("Round", group);

        StyleParameterUtils.editHead(this, head -> {
            StyleParameterUtils.checkHead(head, "painter.decorations.decoration.WebShape");

            if(getAppliedObject().isVariableCustom(ROUND_FULL))
                head.setParameterByPath("painter.decorations.decoration.WebShape", "round", getValue());
            else
                head.removeParameterByPath("painter.decorations.decoration.WebShape", "round");

            return head;
        });
    }
}
