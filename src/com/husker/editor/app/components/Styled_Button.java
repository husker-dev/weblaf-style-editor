package com.husker.editor.app.components;

import com.alee.laf.button.WebButton;
import com.husker.editor.app.project.Parameter;
import com.husker.editor.app.project.StyleComponent;
import com.husker.editor.app.parameters.*;
import com.husker.editor.app.project.Variable;
import com.husker.editor.app.xml.XMLHead;

import java.awt.*;
import java.util.ArrayList;

public class Styled_Button extends StyleComponent {

    static IntegerParameter p_round;
    static {
        p_round = new IntegerParameter("Round", "round");
    }

    public Styled_Button() {
        super("Button", "button");
        addVariable("round", new Variable("3"));
    }

    public ArrayList<Parameter> getParameters() {
        return new ArrayList<Parameter>(){{
            add(p_round);
        }};
    }

    public XMLHead getStyleContent() {
        return new XMLHead("painter"){{
            addXMLHead(new XMLHead("decorations"){{
                addXMLHead(new XMLHead("decoration"){{
                    addXMLHead(new XMLHead("WebShape"){{
                        addXMLParameter("round", getVariableValue("round"));
                    }});
                }});
            }});
        }};
    }

    public Component createPreviewComponent() {
        return new WebButton("It's a button with text");
    }
}
