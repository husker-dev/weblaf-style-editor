package com.husker.editor.content.style.parameters.shape.round;

import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.parameters.IntegerParameter;
import com.husker.editor.core.tools.Resources;

import static com.husker.editor.content.style.StyleComponent.Variables.*;
import static com.husker.editor.content.style.StyleComponent.Variables.ROUND_LB;

public class RBRoundParameter extends IntegerParameter {

    public RBRoundParameter(String group) {
        super("Corner", group);
        setIcon(Resources.getImageIcon("round_rb.png"));

        StyleParameterUtils.editHead(this, head -> {
            StyleParameterUtils.checkHead(head, "painter.decorations.decoration.WebShape");

            if(getAppliedObject().areVariablesCustom(ROUND_LT, ROUND_RT, ROUND_RB, ROUND_LB)) {
                String[] corners = head.getParameterByPath("painter.decorations.decoration.WebShape", "round").getValue().split(",");

                String round = "";
                round += corners[0] + ",";
                round += corners[1] + ",";
                round += getValue() + ",";
                round += corners[3];
                head.setParameterByPath("painter.decorations.decoration.WebShape", "round", round);
            }else
                head.removeParameterByPath("painter.decorations.decoration.WebShape", "round");

            return head;
        });
    }
}
