package com.husker.editor.content.style.parameters.shape.round;

import com.husker.editor.core.EditableObject;
import com.husker.editor.core.parameters.ComboParameter;

import static com.husker.editor.content.style.StyleComponent.Variables.*;

public class RoundTypeParameter extends ComboParameter {

    private static String[] elements = new String[]{"Full", "Custom"};

    public RoundTypeParameter(String group) {
        super("Round type", group, elements);

        addActionListener(() -> {
            if(getValue() == null)
                return;
            boolean full = isVisible() && getValue().equals("Full");
            boolean custom = isVisible() && !full;

            EditableObject.getStaticParameter(ROUND_LT).setVisible(custom);
            EditableObject.getStaticParameter(ROUND_LB).setVisible(custom);
            EditableObject.getStaticParameter(ROUND_RB).setVisible(custom);
            EditableObject.getStaticParameter(ROUND_RT).setVisible(custom);
            EditableObject.getStaticParameter(ROUND_FULL).setVisible(full);
        });
    }
}
