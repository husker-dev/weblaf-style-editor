package com.husker.editor.content.parameters;

import com.alee.extended.image.WebImage;
import com.alee.laf.text.WebTextField;
import com.husker.editor.content.constants.text.TextConstant;
import com.husker.editor.core.Parameter;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class TextParameter extends Parameter {

    private WebTextField textField;

    public TextParameter(String name){
        this(name, null);
    }
    public TextParameter(String name, String group) {
        super(name, TextConstant.class, group);
    }

    public void apply(String value) {
        textField.setText(value);
    }
    public String getValue() {
        return textField.getText();
    }

    public void setEnabled(boolean enabled) {
        textField.setEnabled(enabled);
    }

    public Component initComponent() {
        textField = new WebTextField();
        textField.setPreferredWidth(50);
        textField.setTrailingComponent(new WebImage(new ImageIcon("bin/text_field.png")));
        return textField;
    }

    public void addValueChangedListener(Consumer<String> consumer) {
        textField.onChange((e, d) -> consumer.accept(textField.getText()));
    }
}


