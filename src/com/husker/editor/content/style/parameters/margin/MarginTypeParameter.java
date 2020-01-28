package com.husker.editor.content.style.parameters.margin;

import com.husker.editor.content.style.StyleComponent;
import com.husker.editor.core.EditableObject;
import com.husker.editor.core.parameters.ComboParameter;

public class MarginTypeParameter extends ComboParameter {

    private static String[] items =  new String[]{"Full", "Custom"};

    public MarginTypeParameter(String group) {
        super("Type", group, items);

        addActionListener(() -> {
            boolean full = isVisible() && getValue().equals("Full");
            boolean custom = isVisible() && !full;

            EditableObject.getStaticParameter(StyleComponent.Variables.MARGIN_LEFT).setVisible(custom);
            EditableObject.getStaticParameter(StyleComponent.Variables.MARGIN_TOP).setVisible(custom);
            EditableObject.getStaticParameter(StyleComponent.Variables.MARGIN_RIGHT).setVisible(custom);
            EditableObject.getStaticParameter(StyleComponent.Variables.MARGIN_BOTTOM).setVisible(custom);
            EditableObject.getStaticParameter(StyleComponent.Variables.MARGIN_FULL).setVisible(full);
        });
    }
}
