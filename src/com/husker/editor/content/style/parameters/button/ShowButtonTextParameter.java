package com.husker.editor.content.style.parameters.button;

import com.husker.editor.content.style.StyleComponent;
import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.BooleanParameter;

public class ShowButtonTextParameter extends BooleanParameter {

    public ShowButtonTextParameter(String group) {
        super("Show text", group);

        StyleParameterUtils.editHead(this, head -> {
            StyleParameterUtils.checkHead(head, "painter.decorations.decoration.ButtonLayout.ButtonText");

            if(getAppliedObject().isVariableCustom(StyleComponent.Variables.BUTTON_SHOW_TEXT))
                head.setParameterByPath("painter.decorations.decoration.ButtonLayout.ButtonText", "constraints", getBooleanValue() ? "text" : "");
            else
                head.removeParameterByPath("painter.decorations.decoration.ButtonLayout.ButtonText", "constraints");

            return head;
        });
    }
}
