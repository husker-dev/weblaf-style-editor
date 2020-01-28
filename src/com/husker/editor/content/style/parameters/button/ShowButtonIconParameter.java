package com.husker.editor.content.style.parameters.button;

import com.husker.editor.content.style.StyleComponent;
import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.BooleanParameter;

public class ShowButtonIconParameter extends BooleanParameter {
    public ShowButtonIconParameter(String group) {
        super("Show icon", group);

        StyleParameterUtils.editHead(this, head -> {
            StyleParameterUtils.checkHead(head, "painter.decorations.decoration.ButtonLayout.ButtonIcon");

            if(getAppliedObject().isVariableCustom(StyleComponent.Variables.BUTTON_SHOW_ICON))
                head.setParameterByPath("painter.decorations.decoration.ButtonLayout.ButtonIcon", "constraints", getBooleanValue() ? "icon" : "");
            else
                head.removeParameterByPath("painter.decorations.decoration.ButtonLayout.ButtonIcon", "constraints");

            return head;
        });
    }
}
