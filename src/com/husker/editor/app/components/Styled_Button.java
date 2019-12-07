package com.husker.editor.app.components;

import com.alee.laf.button.WebButton;
import com.husker.editor.app.project.Parameter;
import com.husker.editor.app.project.StyleComponent;
import com.husker.editor.app.parameters.*;
import com.husker.editor.app.xml.XMLHead;
import com.husker.editor.app.xml.XMLParameter;

import java.awt.*;

public class Styled_Button extends StyleComponent {

    static IntegerParameter p_round, p_inner_shadow, p_outer_shadow;
    static BooleanParameter p_gradient;
    static ColorParameter p_border_color;

    static {
        p_round = new IntegerParameter("Round", "round", "Shape");
        p_gradient = new BooleanParameter("Gradient", "gradient", "Appearance");
        p_border_color = new ColorParameter("Border color", "border_color", "Appearance");

        p_inner_shadow = new IntegerParameter("Inner shadow", "inner_shadow", "Appearance");
        p_outer_shadow = new IntegerParameter("Outer shadow", "outer_shadow", "Appearance");
    }

    public Styled_Button() {
        super("Button", "button");
        addVariable("round", "3");
        addVariable("gradient","false");
        addVariable("border_color","170,170,170");
        addVariable("inner_shadow","0");
        addVariable("outer_shadow","2");
    }

    public Parameter[] getCustomParameters() {
        return new Parameter[]{
            p_round, p_gradient, p_border_color, p_inner_shadow, p_outer_shadow
        };
    }

    public XMLHead[] getStyleContent() {
        if(areVariablesDefault("round", "border_color", "inner_shadow", "outer_shadow"))
            return null;

        // Painter
        XMLHead painter = new XMLHead("painter");
        if(!getVariable("round").isDefaultValue())
            painter.setParameterByPath("decorations.decoration.WebShape", new XMLParameter("round", getVariableValue("round")));
        if(!getVariable("border_color").isDefaultValue())
            painter.setParameterByPath("decorations.decoration.LineBorder", new XMLParameter("color", getVariableValue("border_color")));
        if(!getVariable("inner_shadow").isDefaultValue())
            painter.setHeadByPath("decorations.decoration", new XMLHead("WebShadow"){{
                addXMLParameter("type","inner");
                addXMLParameter("width",getVariableValue("inner_shadow"));
            }});
        if(!getVariable("outer_shadow").isDefaultValue())
            painter.setHeadByPath("decorations.decoration", new XMLHead("WebShadow"){{
                addXMLParameter("type","outer");
                addXMLParameter("width",getVariableValue("outer_shadow"));
            }});

        return new XMLHead[]{painter};
    }

    public Component createPreviewComponent() {
        return new WebButton("It's a button with text");
    }
}
