package com.husker.editor.app.window.panels.components;

import com.alee.extended.statusbar.WebStatusBar;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.grouping.GroupPane;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.toolbar.WebToolBar;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Components;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.project.StyleComponent;
import com.husker.editor.app.project.listeners.project.ProjectEvent;
import com.husker.editor.app.window.tools.MovableComponentList;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.husker.editor.app.project.listeners.component.ComponentEvent.Type.*;

public class ComponentsPanel extends WebPanel {

    private WebComboBox combo;
    private WebButton add;
    private MovableComponentList list;
    private WebScrollPane scroll;

    private HashMap<StyleComponent, ComponentPanel> components = new HashMap<>();

    public ComponentsPanel(){
        setPreferredWidth(250);

        combo = new WebComboBox(){{
            ArrayList<String> components = new ArrayList<>();
            for(Map.Entry<String, Class<? extends StyleComponent>> entry : StyleComponent.components.entrySet())
                components.add(entry.getKey());
            Collections.sort(components);
            for(String component : components)
                addItem(component);
        }};
        add = new WebButton("+"){{
            addActionListener(e -> {
                try {
                    if (Project.getCurrentProject() != null)
                        Project.getCurrentProject().Components.addComponent(StyleComponent.components.get(combo.getSelectedItem()).newInstance().clone(Project.getCurrentProject()));
                }catch (Exception ex){
                    ex.printStackTrace();
                };
            });
        }};
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
        add(new WebToolBar(){{
            add(new GroupPane(combo, add));
        }}, BorderLayout.NORTH);
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
        combo.setEnabled(active);
        add.setEnabled(active);
        scroll.setVisible(active);
    }
}





