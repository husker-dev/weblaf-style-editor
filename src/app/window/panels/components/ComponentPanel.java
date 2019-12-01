package app.window.panels.components;


import app.StyleComponent;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.grouping.GroupPane;
import com.alee.laf.grouping.GroupPaneConstraints;
import com.alee.laf.panel.WebPanel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


class ComponentPanel extends WebPanel {

    boolean dragged = false;
    WebToggleButton title;

    static ImageIcon close_1, close_2;
    static {
        close_1 = new ImageIcon("bin/close.png");
        close_2 = new ImageIcon("bin/close_h.png");
    }

    public ComponentPanel(StyleComponent component){
        setPreferredSize(170, 26);

        title = new WebToggleButton(component.getTitle()){{
            setHorizontalAlignment(LEFT);

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
                    }
                }
            });
            addActionListener(e -> {
                if(isSelected())
                    component.getProject().Components.setSelectedComponent(component);
                else
                    component.getProject().Components.setSelectedComponent(null);
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
        WebButton delete = new WebButton(){{
            setPreferredSize(22, 20);
            addActionListener(e -> {
                component.getProject().Components.removeComponent(component);
            });
            setIcon(close_1);
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    setIcon(close_2);
                }
                public void mouseExited(MouseEvent e) {
                    setIcon(close_1);
                }
            });
        }};

        add(title);


        add(new GroupPane(){{
            setGroupButtons(false);
            add(title, GroupPaneConstraints.FILL);
            add(delete);
        }});

    }

    public void setSelected(boolean selected){
        title.setSelected(selected);
    }
}