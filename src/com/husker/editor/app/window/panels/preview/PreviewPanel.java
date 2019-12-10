package com.husker.editor.app.window.panels.preview;

import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.toolbar.WebToolBar;
import com.husker.editor.app.project.Components;
import com.husker.editor.app.project.Project;

import java.awt.*;

public class PreviewPanel extends WebPanel {

    public PaintingPanel painting = new PaintingPanel();
    public static WebProgressBar progressBar;

    public PreviewPanel(){
        setLayout(new BorderLayout());

        Components.addListener((event, objects) -> {
            painting.setVisible(!(Project.getCurrentProject() == null || Project.getCurrentProject().Components.getSelectedComponent() == null));
        });
        Project.addListener((event, objects) -> {
            painting.setVisible(!(Project.getCurrentProject() == null || Project.getCurrentProject().Components.getSelectedComponent() == null));
        });

        add(painting, BorderLayout.CENTER);
        add(new WebToolBar(){{
            add(new WebButton("Update"){{
                addActionListener(e -> {
                    painting.updateSkin();
                });
            }});
            add(new WebToggleButton("Shape"){{
                addActionListener(e -> {
                    painting.setDrawBorder(isSelected());
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
