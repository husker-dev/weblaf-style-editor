package com.husker.editor.app.window.panels.preview;


import com.alee.laf.panel.WebPanel;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Components;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.skin.CustomSkin;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.Method;

import static com.husker.editor.app.project.Components.ComponentEvent.*;

public class PaintingPanel extends WebPanel {

    private Component content;
    private boolean drawBorder = false;

    public PaintingPanel(){
        setLayout(null);

        Components.addListener((event, objects) -> {
            if(event.oneOf(Selected_Changed)){
                removeAll();
                if(Project.getCurrentProject().Components.getSelectedComponent() != null) {
                    content = Project.getCurrentProject().Components.getSelectedComponent().createPreviewComponent();
                    // setting style id
                    try{
                        Method method = content.getClass().getDeclaredMethod("setStyleId", StyleId.class);
                        method.setAccessible(true);
                        method.invoke(content, StyleId.of("preview"));
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    add(content);
                }
            }
            if(event.oneOf(Selected_Changed, Style_Changed)){
                updateContent();
                if(Project.getCurrentProject().Components.getSelectedComponent() != null)
                    updateSkin();
            }
        });

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                updateContent();
            }
        });
    }

    public void updateContent(){
        if(content != null) {
            content.setSize(content.getPreferredSize());
            content.setLocation((getWidth() - content.getWidth()) / 2, (getHeight() - content.getHeight()) / 2);
        }
        repaint();
    }

    public void paint(Graphics gr){
        gr.setColor(new Color(237, 237 ,237));
        gr.fillRect(0, 0, getWidth(), getHeight());
        if(content != null) {
            if(drawBorder) {
                gr.setColor(Color.red);
                gr.fillRect((getWidth() - content.getWidth()) / 2, (getHeight() - content.getHeight()) / 2, content.getWidth(), content.getHeight());
            }
        }
        super.paintComponents(gr);
    }

    public void setDrawBorder(boolean drawBorder){
        this.drawBorder = drawBorder;
        repaint();
    }

    public void updateSkin(){
        try {
            CustomSkin.applySkin(content, Project.getCurrentProject().Components.getSelectedComponent().getXMLStyle(true));
        }catch (Exception ex){}
    }
}
