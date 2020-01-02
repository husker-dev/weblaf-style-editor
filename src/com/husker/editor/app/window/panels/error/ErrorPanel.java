package com.husker.editor.app.window.panels.error;

import com.alee.extended.image.WebImage;
import com.alee.extended.label.WebStyledLabel;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Error;

import javax.swing.*;
import java.awt.*;

public class ErrorPanel extends WebPanel {

    public ErrorPanel(Error error){
        super(StyleId.panelDecorated);
        setLayout(new BorderLayout());
        setPadding(3);
        setMargin(3, 3, 0, 3);
        add(new WebPanel(){{
            setLayout(new BorderLayout());
            add(new WebImage(new ImageIcon("bin/error_large.png")), BorderLayout.WEST);
            add(new WebLabel(){{
                setMargin(0, 5, 0, 0);
                setText(error.getTitle());
                setFontSize(15);
            }});
        }}, BorderLayout.NORTH);
        add(new WebPanel(){{
            setMargin(0, 5, 0, 0);
            add(new WebStyledLabel(){{
                setMargin(3);
                setText("{" + error.getText() + ":c(gray)}");
                setFontSize(12);
            }});
        }}, BorderLayout.SOUTH);
    }
}
