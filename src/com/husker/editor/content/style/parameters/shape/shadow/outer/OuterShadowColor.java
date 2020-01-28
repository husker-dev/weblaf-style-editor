package com.husker.editor.content.style.parameters.shape.shadow.outer;

import com.husker.editor.content.style.StyleComponent;
import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.ColorParameter;
import com.husker.editor.core.xml.XMLHead;

import java.util.function.Predicate;

public class OuterShadowColor extends ColorParameter {

    public OuterShadowColor(String group) {
        super("Color", group);

        StyleParameterUtils.editHead(this, head -> {
            Predicate<XMLHead> predicate = h -> ("outer").equals(h.getParameterValue("type"));

            if(getAppliedObject().isVariableCustom(StyleComponent.Variables.INNER_SHADOW_COLOR)) {
                checkForHead(head, predicate);
                head.setParameterByPath("painter.decorations.decoration.WebShadow", "color", getValue(), predicate);
            }else
                head.removeParameterByPath("painter.decorations.decoration.WebShadow", "color", predicate);

            return head;
        });
    }

    void checkForHead(XMLHead head, Predicate<XMLHead> predicate){
        if(head.containsHeadByPath("painter.decorations.decoration.WebShadow", predicate))
            head.createHeadByPath("painter.decorations.decoration", new XMLHead("WebShadow", new String[]{"type", "outer"}));
    }
}
