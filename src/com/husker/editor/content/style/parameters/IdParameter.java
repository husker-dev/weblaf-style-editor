package com.husker.editor.content.style.parameters;

import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.TextParameter;

import static com.husker.editor.content.style.StyleComponent.Variables.*;

public class IdParameter extends TextParameter {
    public IdParameter() {
        super("Id");

        StyleParameterUtils.editHead(this, head -> {

            if(getAppliedObject().isVariableCustom(ID))
                head.setParameter("id", getAppliedObject().getVariableValue(ID));
            else
                head.removeParameter("id");

            return head;
        });
    }
}
