package com.husker.editor.app.window.panels.preview;

import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.toolbar.WebToolBar;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.skin.CustomSkin;

import java.awt.*;


public class PreviewPanel extends WebPanel {

    PaintingPanel content = new PaintingPanel();
    public static WebProgressBar progressBar;

    public PreviewPanel(){
        setLayout(new BorderLayout());
        setStyleId(StyleId.panelDecorated);

        add(content, BorderLayout.CENTER);
        add(new WebToolBar(){{
            add(new WebButton("Update"){{
                addActionListener(e -> {
                    CustomSkin.applySkin(content, Project.getCurrentProject().Components.getSelectedComponent().getCode(true).toString());
                });
            }});
            add(new WebToggleButton("Shape"){{
                addActionListener(e -> {
                    content.drawBorder = isSelected();
                    content.repaint();
                });
            }});

            addToEnd(progressBar = new WebProgressBar(0, 100){{
                setVisible(false);
                setStringPainted(true);
                setString("Applying...");
                setIndeterminate(true);

            }});

        }}, BorderLayout.SOUTH);
    }


}
