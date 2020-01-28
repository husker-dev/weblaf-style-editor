package com.husker.editor.content.style.parameters.shape;

import com.husker.editor.content.style.StyleParameterUtils;
import com.husker.editor.core.EditableObject;
import com.husker.editor.core.parameters.BooleanParameter;

import static com.husker.editor.content.style.StyleComponent.Variables.*;

public class ShapeEnabledParameter extends BooleanParameter {

    public ShapeEnabledParameter(String group) {
        super("Enable", group);

        StyleParameterUtils.editHead(this, head -> {
            if(getAppliedObject().isVariableCustom(SHAPE_ENABLED))
                head.createHeadByPath("painter.decorations.decoration.WebShape").setAutoRemovable(false);
            else
                head.removeHeadByPath("painter.decorations.decoration.WebShape");

            EditableObject.getStaticParameter(ROUND_TYPE).setVisible(getBooleanValue());
            return head;
        });
    }
}
