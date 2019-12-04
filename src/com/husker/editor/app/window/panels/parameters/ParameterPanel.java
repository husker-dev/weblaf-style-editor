package com.husker.editor.app.window.panels.parameters;


import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.separator.WebSeparator;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Components;
import com.husker.editor.app.project.Parameter;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.project.StyleComponent;
import com.husker.editor.app.window.components.MovableComponentList;


public class ParameterPanel extends WebPanel {
    WebScrollPane scroll;

    public ParameterPanel(){
        setPreferredWidth(230);

        Components.addListener((event, objects) -> {
            scroll.setVisible(!(Project.getCurrentProject() == null || Project.getCurrentProject().Components.getSelectedComponent() == null));
        });
        Project.addListener((event, objects) -> {
            scroll.setVisible(!(Project.getCurrentProject() == null || Project.getCurrentProject().Components.getSelectedComponent() == null));
        });

        add(scroll = new WebScrollPane(new MovableComponentList(){{
            setShowReorderGrippers(false);
            Components.addListener((event, objects) -> {
                if(event.oneOf(Components.ComponentEvent.Selected_Changed)) {
                    StyleComponent component = (StyleComponent)objects[0];

                    while(getElementCount() != 0){
                        for(int i = 0; i < getElementCount(); i++)
                            this.removeElement(getElement(i));
                    }


                    if(component != null) {
                        if(component.getDefaultParameters() != null) {
                            for (Parameter parameter : component.getDefaultParameters()) {
                                parameter.apply(component);
                                addElement(parameter.getPanel());
                                addElement(WebSeparator.createHorizontal());
                            }
                        }
                        if(component.getParameters() != null) {
                            for (Parameter parameter : component.getParameters()) {
                                parameter.apply(component);
                                addElement(parameter.getPanel());
                                addElement(WebSeparator.createHorizontal());
                            }
                        }
                    }
                    updateUI();
                }
            });

        }}){{
            setVisible(false);
            setStyleId(StyleId.scrollpaneUndecorated);
        }});
    }
}
