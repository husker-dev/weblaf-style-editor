package com.husker.editor.app.components;

import com.alee.laf.button.WebButton;
import com.husker.editor.app.Parameter;
import com.husker.editor.app.project.StyleComponent;
import com.husker.editor.app.parameters.*;
import com.husker.editor.app.xml.XMLHead;

import java.awt.*;
import java.util.ArrayList;

public class Styled_Button extends StyleComponent {

    static TextParameter p_id;
    static IntegerParameter p_round;
    static {
        p_id = new TextParameter("Id", "id");
        p_round = new IntegerParameter("Round", "round");
    }


    public Styled_Button() {
        super("Button");
    }

    public ArrayList<Parameter> getParameters() {
        return new ArrayList<Parameter>(){{
            add(p_id);
            add(p_round);
        }};
    }

    public XMLHead getCode(boolean preview) {
        return new XMLHead("style"){{
            addXMLParameter("type", "button");

            if(getValue("id") != null && !getValue("id").isEmpty())
                addXMLParameter("id", getValue("id"));
            if(preview)
                addXMLParameter("id", "preview");

            addXMLHead(new XMLHead("painter"){{
                addXMLHead(new XMLHead("decorations"){{
                    addXMLHead(new XMLHead("decoration"){{
                        addXMLHead(new XMLHead("WebShape"){{
                            addXMLParameter("round", getValue("round"));
                        }});
                        addXMLHead(new XMLHead("ButtonLayout"){{
                            addXMLHead(new XMLHead("ButtonIcon"){{
                                addXMLParameter("constraints", "icon");
                            }});
                            addXMLHead(new XMLHead("ButtonText"){{
                                addXMLParameter("constraints", "text");
                            }});
                        }});
                    }});
                }});
            }});
        }};
    }

    public Component createPreviewComponent() {
        return new WebButton("It's a button with text");
    }
}
