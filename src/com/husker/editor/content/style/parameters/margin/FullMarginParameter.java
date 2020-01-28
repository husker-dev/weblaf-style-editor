package com.husker.editor.content.style.parameters.margin;

import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.IntegerParameter;

import static com.husker.editor.content.style.StyleComponent.Variables.PADDING_FULL;

public class FullMarginParameter extends IntegerParameter {

    public FullMarginParameter(String group) {
        super("Padding", group);

        StyleParameterUtils.editHead(this, head -> {
            if(getAppliedObject().isVariableCustom(PADDING_FULL))
                head.setParameter("padding", getAppliedObject().getVariableValue(PADDING_FULL));
            else
                head.removeParameter("padding");
            return head;
        });
    }
}
