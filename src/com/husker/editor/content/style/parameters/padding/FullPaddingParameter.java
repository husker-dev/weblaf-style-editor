package com.husker.editor.content.style.parameters.padding;

import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.IntegerParameter;

import static com.husker.editor.content.style.StyleComponent.Variables.*;

public class FullPaddingParameter extends IntegerParameter {

    public FullPaddingParameter(String group) {
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
