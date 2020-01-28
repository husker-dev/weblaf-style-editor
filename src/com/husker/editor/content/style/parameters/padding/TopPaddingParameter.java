package com.husker.editor.content.style.parameters.padding;

import com.husker.editor.content.style.StyleComponent;
import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.IntegerParameter;

public class TopPaddingParameter extends IntegerParameter {

    public TopPaddingParameter(String group) {
        super("Top", group);

        StyleParameterUtils.editHead(this, head -> {

            if(getAppliedObject().areVariablesCustom(
                    StyleComponent.Variables.PADDING_LEFT,
                    StyleComponent.Variables.PADDING_TOP,
                    StyleComponent.Variables.PADDING_RIGHT,
                    StyleComponent.Variables.PADDING_BOTTOM)) {
                String padding_value = "";

                padding_value += head.getParameterValue("padding").split(",")[0] + ",";
                padding_value += getValue() + ",";
                padding_value += head.getParameterValue("padding").split(",")[2] + ",";
                padding_value += head.getParameterValue("padding").split(",")[3];

                head.setParameter("padding", padding_value);
            }else
                head.removeParameter("padding");

            return head;
        });
    }
}
