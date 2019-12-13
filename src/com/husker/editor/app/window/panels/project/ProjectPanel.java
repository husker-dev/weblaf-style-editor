package com.husker.editor.app.window.panels.project;

import com.alee.extended.breadcrumb.WebBreadcrumb;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Components;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.project.StyleComponent;

import javax.swing.*;
import java.awt.*;

public class ProjectPanel extends WebPanel {

    private static ImageIcon project_icon = new ImageIcon("bin/project.png");
    private static ImageIcon style_icon = new ImageIcon("bin/style.png");

    public ProjectPanel(){
        setLayout(new BorderLayout());
        add(new WebBreadcrumb(){
            {
                setStyleId(StyleId.of("custom-breadcrumb"));

                setPreferredHeight(27);
                Components.addListener(e -> update());
                Project.addListener(e -> update());
                update();
            }
            void update(){
                removeAll();

                if(Project.getCurrentProject() != null){
                    add(new WebLabel(Project.getCurrentProject().getName(), project_icon));

                    StyleComponent component = Project.getCurrentProject().Components.getSelectedComponent();
                    if(component != null) {
                        add(new WebPanel(){{
                            setPadding(4, 10, 0, 0);
                            setStyleId(StyleId.panelTransparent);
                            setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                            add(new WebLabel(component.getTitle(), style_icon){{
                                setPreferredHeight(18);
                            }});
                            add(new WebLabel(component.getVariableValue("id")){{
                                setMargin(0, 4, 0, 0);
                                setStyleId(StyleId.labelTag);
                                setPreferredHeight(18);
                                setVisible(!component.getVariableValue("id").equals(""));
                            }});
                        }});
                    }
                }
            }
        }, BorderLayout.CENTER);
    }
}
