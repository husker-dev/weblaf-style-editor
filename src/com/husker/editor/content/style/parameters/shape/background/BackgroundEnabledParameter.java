package com.husker.editor.content.style.parameters.shape.background;

import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.EditableObject;
import com.husker.editor.core.parameters.BooleanParameter;

import static com.husker.editor.content.style.StyleComponent.Variables.*;

public class BackgroundEnabledParameter extends BooleanParameter {

    public BackgroundEnabledParameter(String group) {
        super("Enabled", group);

        StyleParameterUtils.editHead(this, head -> {
            if(getBooleanValue()) {
                if (getAppliedObject().getVariableValue(BACKGROUND_TYPE).equals("Gradient")) {
                    head.removeHeadByPath("painter.decorations.decoration.ColorBackground");
                    head.createHeadByPath("painter.decorations.decoration.GradientBackground");
                }
                if (getAppliedObject().getVariableValue(BACKGROUND_TYPE).equals("Color")) {
                    head.removeHeadByPath("painter.decorations.decoration.GradientBackground");
                    head.createHeadByPath("painter.decorations.decoration.ColorBackground");
                }
            }else{
                head.removeHeadByPath("painter.decorations.decoration.GradientBackground");
                head.removeHeadByPath("painter.decorations.decoration.ColorBackground");
            }

            EditableObject.getStaticParameter(BACKGROUND_TYPE).setVisible(getBooleanValue());
            return head;
        });
    }
}
