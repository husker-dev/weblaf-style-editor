package com.husker.editor.content.style.parameters.margin;

import com.husker.editor.content.style.StyleComponent;
import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.IntegerParameter;

public class LeftMarginParameter extends IntegerParameter {

    public LeftMarginParameter(String group) {
        super("Left", group);

        StyleParameterUtils.editHead(this, head -> {

            if(getAppliedObject().areVariablesCustom(
                    StyleComponent.Variables.MARGIN_LEFT,
                    StyleComponent.Variables.MARGIN_TOP,
                    StyleComponent.Variables.MARGIN_RIGHT,
                    StyleComponent.Variables.MARGIN_BOTTOM)) {
                String margin_value = "";

                margin_value += getValue() + ",";
                margin_value += getAppliedObject().getVariableValue(StyleComponent.Variables.MARGIN_TOP) + ",";
                margin_value += getAppliedObject().getVariableValue(StyleComponent.Variables.MARGIN_RIGHT) + ",";
                margin_value += getAppliedObject().getVariableValue(StyleComponent.Variables.MARGIN_BOTTOM);

                head.setParameter("margin", margin_value);
            }else
                head.removeParameter("margin");

            return head;
        });
    }
}
