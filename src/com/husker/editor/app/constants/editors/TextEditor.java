package com.husker.editor.app.constants.editors;

import com.husker.editor.app.tools.ConditionTextField;
import com.husker.editor.app.project.ConstantEditor;

import java.awt.*;
import java.util.function.Consumer;

public class TextEditor extends ConstantEditor {

    private ConditionTextField field;

    public Component initComponent() {
        return field = new ConditionTextField();
    }

    public void apply(String value) {
        field.setText(value);
    }

    protected String getValue() {
        return field.getText();
    }

    public void onChanged(Consumer<String> listener) {
        field.onApplied(() -> listener.accept(getValue()));
    }
}
