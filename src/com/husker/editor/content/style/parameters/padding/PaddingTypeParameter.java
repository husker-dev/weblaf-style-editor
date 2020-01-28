package com.husker.editor.content.style.parameters.padding;

import com.husker.editor.content.style.StyleComponent;
import com.husker.editor.core.EditableObject;
import com.husker.editor.core.parameters.ComboParameter;

public class PaddingTypeParameter extends ComboParameter {

    private static String[] items =  new String[]{"Full", "Custom"};

    public PaddingTypeParameter(String group) {
        super("Type", group, items);

        addActionListener(() -> {
            boolean full = isVisible() && getValue().equals("Full");
            boolean custom = isVisible() && !full;

            EditableObject.getStaticParameter(StyleComponent.Variables.PADDING_LEFT).setVisible(custom);
            EditableObject.getStaticParameter(StyleComponent.Variables.PADDING_TOP).setVisible(custom);
            EditableObject.getStaticParameter(StyleComponent.Variables.PADDING_RIGHT).setVisible(custom);
            EditableObject.getStaticParameter(StyleComponent.Variables.PADDING_BOTTOM).setVisible(custom);
            EditableObject.getStaticParameter(StyleComponent.Variables.PADDING_FULL).setVisible(full);
        });
    }
}
