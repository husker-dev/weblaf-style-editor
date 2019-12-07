package com.husker.editor.app.window.panels.components;


import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.grouping.GroupPane;
import com.alee.laf.grouping.GroupPaneConstraints;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.project.StyleComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


class ComponentPanel extends WebPanel {

    boolean dragged = false;
    WebToggleButton title;
    WebButton actions, resize;

    WebLabel type, id, separator;

    StyleComponent component;

    static ImageIcon close_1, close_2, more, arrow_down, arrow_up;
    static {
        close_1 = new ImageIcon("bin/close.png");
        close_2 = new ImageIcon("bin/close_h.png");
        more = new ImageIcon("bin/more.png");
        arrow_down = new ImageIcon("bin/arrow_down.png");
        arrow_up = new ImageIcon("bin/arrow_up.png");
    }

    public ComponentPanel(StyleComponent component){
        this.component = component;
        setPreferredSize(170, 27);

        title = new WebToggleButton(){{
            setHorizontalAlignment(LEFT);
            setVerticalAlignment(CENTER);
            add(new WebPanel(){{
                setStyleId(StyleId.panelTransparent);
                setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                add(type = new WebLabel(component.getTitle()){{
                    setPreferredHeight(18);
                }});
                add(separator = new WebLabel("  ->  "){{
                    setPreferredHeight(18);
                    setVisible(false);
                }});
                add(id = new WebLabel(){{
                    setStyleId(StyleId.labelTag);
                    setPreferredHeight(18);
                    setVisible(false);
                }});
            }});

            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    ComponentPanel.this.getParent().dispatchEvent(e);
                }
                public void mouseReleased(MouseEvent e) {
                    ComponentPanel.this.getParent().dispatchEvent(e);
                    if(dragged){
                        dragged = false;
                        setEnabled(true);
                        return;
                    }else{
                        if(isSelected())
                            component.getProject().Components.setSelectedComponent(component);
                        else
                            component.getProject().Components.setSelectedComponent(null);
                    }
                }
            });
            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if(!dragged){
                        setEnabled(false);
                        if(isSelected()){
                            component.getProject().Components.setSelectedComponent(null);
                            setSelected(false);
                        }
                    }
                    dragged = true;

                    ComponentPanel.this.getParent().dispatchEvent(e);
                }
                public void mouseMoved(MouseEvent e) {
                    ComponentPanel.this.getParent().dispatchEvent(e);
                }
            });
        }};
        WebPopupMenu popup = new WebPopupMenu();
        popup.add(new WebMenuItem("Add"){{
            addActionListener(e -> {
                System.out.println("ok");
            });
        }});
        popup.add(new WebMenuItem("Remove"){{
            addActionListener(e -> {
                Project.getCurrentProject().Components.removeComponent(component);
            });
        }});


        actions = new WebButton(){{
            setPreferredSize(22, 20);
            setIcon(more);
        }};
        actions.addActionListener(e -> popup.show(actions, -actions.getWidth() + 3, actions.getHeight() - 8));

        resize = new WebButton(){{
            setPreferredSize(22, 20);
            setIcon(arrow_down);
        }};


        add(title);
        add(new GroupPane(){{
            setGroupButtons(false);
            add(title, GroupPaneConstraints.FILL);
            add(actions);
            add(resize);
        }});

    }

    public void onStyleUpdate(){
        if(!component.getVariable("id").isDefaultValue())
            id.setText(component.getVariableValue("id"));
        id.setVisible(!component.getVariable("id").isDefaultValue());
        separator.setVisible(!component.getVariable("id").isDefaultValue());
    }

    public void setSelected(boolean selected){
        title.setSelected(selected);
    }
}