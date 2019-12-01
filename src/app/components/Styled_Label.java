package app.components;

import app.Parameter;
import app.StyleComponent;
import app.XMLHead;
import com.alee.laf.label.WebLabel;

import java.awt.*;
import java.util.ArrayList;

public class Styled_Label extends StyleComponent {
    public Styled_Label() {
        super("Label");
    }

    public ArrayList<Parameter> getParameters() {
        return null;
    }

    public XMLHead getCode(boolean preview) {
        return new XMLHead("style"){{
            addXMLParameter("type", "label");

        }};
    }

    public Component createPreviewComponent() {
        return new WebLabel("It's a label with TEXT!");
    }
}
