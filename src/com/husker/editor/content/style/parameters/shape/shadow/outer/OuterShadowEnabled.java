package com.husker.editor.content.style.parameters.shape.shadow.outer;

import com.husker.editor.content.style.StyleComponent;
import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.BooleanParameter;
import com.husker.editor.core.xml.XMLHead;

public class OuterShadowEnabled extends BooleanParameter {

    public OuterShadowEnabled(String group) {
        super("Enabled", group);

        StyleParameterUtils.editHead(this, head -> {

            if(getAppliedObject().isVariableCustom(StyleComponent.Variables.INNER_SHADOW_ENABLED))
                head.createHeadByPath("painter.decorations.decoration", new XMLHead("WebShadow", new String[]{"type", "outer"}));
            else
                head.removeHeadByPath("painter.decorations.decoration.WebShadow", h -> ("outer").equals(h.getParameterValue("type")));

            return head;
        });
    }
}
