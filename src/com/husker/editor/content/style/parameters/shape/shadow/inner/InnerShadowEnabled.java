package com.husker.editor.content.style.parameters.shape.shadow.inner;

import com.husker.editor.content.style.StyleComponent;
import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.BooleanParameter;
import com.husker.editor.core.xml.XMLHead;

public class InnerShadowEnabled extends BooleanParameter {

    public InnerShadowEnabled(String group) {
        super("Enabled", group);

        StyleParameterUtils.editHead(this, head -> {

            if(getAppliedObject().isVariableCustom(StyleComponent.Variables.INNER_SHADOW_ENABLED))
                head.createHeadByPath("painter.decorations.decoration", new XMLHead("WebShadow", new String[]{"type", "inner"}));
            else
                head.removeHeadByPath("painter.decorations.decoration.WebShadow", h -> ("inner").equals(h.getParameterValue("type")));

            return head;
        });
    }
}
