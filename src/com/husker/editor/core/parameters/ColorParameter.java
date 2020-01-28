package com.husker.editor.core.parameters;

import com.alee.extended.colorchooser.WebColorChooserField;
import com.alee.managers.style.StyleId;
import com.husker.editor.core.constants.color.ColorConstant;
import com.husker.editor.core.Parameter;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ColorParameter extends Parameter {

    private WebColorChooserField chooser;

    public ColorParameter(String name) {
        this(name, null);
    }
    public ColorParameter(String name, String group) {
        super(name, ColorConstant.class, group);
    }

    public void apply(String value) {
        String[] color = value.split(",");
        if(color.length < 3){
            chooser.setColor(Color.WHITE);
            return;
        }
        if(color.length == 3)
            chooser.setColor(new Color(Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[2])));
        else
            chooser.setColor(new Color(Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[2]), Integer.parseInt(color[3])));
    }

    public String getValue() {
        if(chooser.getColor().getAlpha() == 255)
            return chooser.getColor().getRed() + "," + chooser.getColor().getGreen() + "," + chooser.getColor().getBlue();
        else
            return chooser.getColor().getRed() + "," + chooser.getColor().getGreen() + "," + chooser.getColor().getBlue() + "," + chooser.getColor().getAlpha();
    }

    public void setEnabled(boolean enabled) {
        chooser.setEnabled(enabled);
    }

    public Component initComponent() {
        chooser = new WebColorChooserField(StyleId.of("custom-chooserfield"));
        chooser.setDisplayEyedropper(false);
        chooser.setPreferredWidth(50);
        chooser.setHorizontalAlignment(SwingConstants.LEFT);
        return chooser;
    }

    public void addValueChangedListener(Consumer<String> consumer) {
        chooser.addColorChooserListener( (e, d) -> consumer.accept(getValue()));
    }
}
