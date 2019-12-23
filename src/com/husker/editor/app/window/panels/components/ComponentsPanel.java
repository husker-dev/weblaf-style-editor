package com.husker.editor.app.window.panels.components;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Components;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.project.StyleComponent;
import com.husker.editor.app.project.listeners.project.ProjectEvent;
import com.husker.editor.app.tools.MovableComponentList;


import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.husker.editor.app.project.listeners.component.ComponentEvent.Type.*;

public class ComponentsPanel extends WebPanel {

    private MovableComponentList list;
    private WebScrollPane scroll;

    private HashMap<StyleComponent, ComponentPanel> components = new HashMap<>();

    public ComponentsPanel(){
        setPreferredWidth(250);

        list = new MovableComponentList(){{
                Components.addListener(e -> {
                    if(e.getType().oneOf(New))
                        addComponent((StyleComponent) e.getObjects()[0]);
                    if(e.getType().oneOf(Removed))
                        removeComponent((StyleComponent) e.getObjects()[0]);
                });
                Project.addListener(e -> {
                    if(e.getType().oneOf(ProjectEvent.Type.Changed))
                        resetComponents(Project.getCurrentProject().Components.getComponents());
                });
                addComponentReorderListener((component, i, i1) -> {
                    Project.getCurrentProject().Components.moveComponent(i, i1);
                });
            }
            void resetComponents(ArrayList<StyleComponent> components){
                ComponentsPanel.this.components.clear();
                while(getElementCount() != 0){
                    for(int i = 0; i < getElementCount(); i++)
                        this.removeElement(getElement(i));
                }
                for(StyleComponent component : components)
                    addComponent(component);
                updateUI();
            }
            void addComponent(StyleComponent component){
                ComponentPanel panel = new ComponentPanel(component);
                ComponentsPanel.this.components.put(component, panel);
                addElement(panel);
                updateUI();
            }
            void removeComponent(StyleComponent component) {
                removeElement(ComponentsPanel.this.components.get(component));
                ComponentsPanel.this.components.remove(component);
                updateUI();
            }
        };
        scroll = new WebScrollPane(list){{
            getVerticalScrollBar().setUnitIncrement(16);
            setStyleId(StyleId.scrollpaneUndecorated);
        }};

        setLayout(new BorderLayout());
        add(scroll);

        Components.addListener(e -> {
            if(e.getType().oneOf(Selected_Changed))
                for(Map.Entry<StyleComponent, ComponentPanel> entry : components.entrySet())
                    entry.getValue().setSelected(Project.getCurrentProject().Components.getSelectedComponent() == entry.getKey());
            if(e.getType().oneOf(Style_Changed))
                components.get(e.getObjects()[0]).onStyleUpdate();
        });
        Project.addListener(e -> {
            setActive(Project.getCurrentProject() != null);
        });
    }

    public void setActive(boolean active){
        scroll.setVisible(active);
    }
}





