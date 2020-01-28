package com.husker.editor.core.constants.color;

import com.alee.extended.colorchooser.WebColorChooserField;
import com.alee.managers.style.StyleId;
import com.husker.editor.core.ConstantEditor;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ColorEditor extends ConstantEditor {

    private WebColorChooserField field;

    protected Component initComponent() {
        return field = new WebColorChooserField(StyleId.of("custom-chooserfield"), Color.black){{
            setDisplayEyedropper(false);
            setPreferredWidth(50);
            setHorizontalAlignment(SwingConstants.LEFT);
        }};
    }

    public void apply(String value) {
        String[] col = value.split(",");
        int r = Integer.parseInt(col[0]);
        int g = Integer.parseInt(col[1]);
        int b = Integer.parseInt(col[2]);
        if(col.length == 4)
            field.setColor(new Color(r, g, b, Integer.parseInt(col[3])));
        else
            field.setColor(new Color(r, g, b));
    }

    protected String getValue() {
        String r = field.getColor().getRed() + "";
        String g = field.getColor().getGreen() + "";
        String b = field.getColor().getBlue() + "";
        String a = field.getColor().getAlpha() + "";
        String color = r + "," + g + "," + b + (a.equals("255") ? "" : "," + a);
        return color;
    }

    public void onChanged(Consumer<String> listener) {
        field.addColorChooserListener((e, d) -> listener.accept(getValue()));
    }
}
