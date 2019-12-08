package com.husker.editor.app.parameters;

import com.alee.extended.colorchooser.WebColorChooserField;
import com.husker.editor.app.project.Constants;
import com.husker.editor.app.project.Parameter;

import javax.swing.*;
import java.awt.*;

public class ColorParameter extends Parameter {

    WebColorChooserField chooser;

    public ColorParameter(String name, String component_variable) {
        this(name, component_variable, null);
    }
    public ColorParameter(String name, String component_variable, String group) {
        super(name, component_variable, Constants.ConstType.Color, group);
    }

    public void setValue(String value) {
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

    public boolean isEnabled() {
        return chooser.isEnabled();
    }

    public Component initComponent() {
        chooser = new WebColorChooserField();
        chooser.setDisplayEyedropper(false);
        chooser.setPreferredWidth(50);
        chooser.setHorizontalAlignment(SwingConstants.LEFT);
        return chooser;
    }

    public void addValueChangedListener(ParameterChanged listener) {
        chooser.addColorChooserListener( (e, d) -> listener.event(getValue()));
    }
}
