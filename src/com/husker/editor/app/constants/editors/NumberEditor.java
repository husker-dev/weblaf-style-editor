package com.husker.editor.app.constants.editors;

import com.husker.editor.app.tools.DoubleSpinner;
import com.husker.editor.app.project.ConstantEditor;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class NumberEditor extends ConstantEditor {

    private DoubleSpinner field;

    public Component initComponent() {
        return field = new DoubleSpinner(1){{
            setPreferredHeight(24);
            JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)getEditor();
            spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
        }};
    }

    public void apply(String value) {
        try {
            field.setValue(Float.parseFloat(value));
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println(value);
        }
    }

    protected String getValue() {
        if(field.getDouble() == field.getDouble().intValue())
            return field.getDouble().intValue() + "";
        return field.getDouble().toString();
    }

    public void onChanged(Consumer<String> listener) {
        field.addChangeListener(e -> listener.accept(getValue()));
    }
}
