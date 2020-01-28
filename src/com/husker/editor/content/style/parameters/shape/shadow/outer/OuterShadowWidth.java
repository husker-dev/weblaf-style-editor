package com.husker.editor.content.style.parameters.shape.shadow.outer;

import com.husker.editor.content.style.StyleComponent;
import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.IntegerParameter;
import com.husker.editor.core.xml.XMLHead;

import java.util.function.Predicate;

public class OuterShadowWidth extends IntegerParameter {

    public OuterShadowWidth(String group) {
        super("Width", group);

        StyleParameterUtils.editHead(this, head -> {
            Predicate<XMLHead> predicate = h -> ("outer").equals(h.getParameterValue("type"));

            if(getAppliedObject().isVariableCustom(StyleComponent.Variables.INNER_SHADOW_WIDTH)) {
                checkForHead(head, predicate);
                head.setParameterByPath("painter.decorations.decoration.WebShadow", "width", getValue(), predicate);
            }else
                head.removeParameterByPath("painter.decorations.decoration.WebShadow", "width", predicate);

            return head;
        });
    }

    void checkForHead(XMLHead head, Predicate<XMLHead> predicate){
        if(head.containsHeadByPath("painter.decorations.decoration.WebShadow", predicate))
            head.createHeadByPath("painter.decorations.decoration", new XMLHead("WebShadow", new String[]{"type", "outer"}));
    }
}
