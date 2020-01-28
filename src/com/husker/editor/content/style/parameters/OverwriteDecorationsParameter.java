package com.husker.editor.content.style.parameters;

import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.BooleanParameter;

import static com.husker.editor.content.style.StyleComponent.Variables.OVERWRITE_DECORATIONS;

public class OverwriteDecorationsParameter extends BooleanParameter {

    public OverwriteDecorationsParameter() {
        super("Overwrite skin");

        StyleParameterUtils.editHead(this, head -> {

            if(getAppliedObject().getVariable(OVERWRITE_DECORATIONS).isDefaultValue())
                head.removeParameterByPath("painter.decorations", "overwrite");
            else
                head.setParameterByPath("painter.decorations", "overwrite", getValue());

            return head;
        });
    }
}
