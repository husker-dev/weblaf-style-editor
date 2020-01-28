package com.husker.editor.content.style.parameters;

import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.TextParameter;

import static com.husker.editor.content.style.StyleComponent.Variables.*;

public class ExtendsParameter extends TextParameter {

    public ExtendsParameter() {
        super("extends");

        StyleParameterUtils.editHead(this, head -> {

            if(getAppliedObject().isVariableCustom(EXTENDS))
                head.setParameter("extends", getAppliedObject().getVariableValue(EXTENDS));
            else
                head.removeParameter("extends");

            return head;
        });
    }
}
