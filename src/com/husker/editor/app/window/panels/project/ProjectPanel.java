package com.husker.editor.app.window.panels.project;

import com.alee.extended.breadcrumb.WebBreadcrumb;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.events.SelectedChangedEvent;
import com.husker.editor.app.listeners.editable_object.EditableObjectAdapter;
import com.husker.editor.app.project.EditableObject;
import com.husker.editor.app.project.Project;

import javax.swing.*;
import java.awt.*;

public class ProjectPanel extends WebPanel {

    private static ImageIcon project_icon = new ImageIcon("bin/project.png");
    private static ImageIcon style_icon = new ImageIcon("bin/style.png");

    public ProjectPanel(){
        setLayout(new BorderLayout());

        Project.addListener((event) -> setVisible(Project.getCurrentProject() != null));
        setVisible(false);

        add(new WebBreadcrumb(StyleId.of("custom-breadcrumb")){
            {
                setPreferredHeight(27);
                EditableObject.addEditableObjectListener(new EditableObjectAdapter() {
                    public void selectedChanged(SelectedChangedEvent event) {
                        update();
                    }
                });
                Project.addListener(e -> update());
                update();
            }
            void update(){
                removeAll();

                if(Project.getCurrentProject() != null){
                    add(new WebLabel(Project.getCurrentProject().getName(), project_icon));

                    EditableObject component = Project.getCurrentProject().getSelectedObject();
                    if(component != null) {
                        add(new WebPanel(StyleId.panelTransparent){{
                            setPadding(4, 10, 0, 0);
                            setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                            add(new WebLabel(component.getTitle(), style_icon){{
                                setPreferredHeight(18);
                            }});
                            add(new WebLabel(StyleId.labelTag, component.getVariableValue("id")){{
                                setMargin(0, 4, 0, 0);
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
