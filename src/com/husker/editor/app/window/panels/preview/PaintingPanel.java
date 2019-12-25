package com.husker.editor.app.window.panels.preview;


import com.alee.extended.layout.AlignLayout;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.project.StyleComponent;
import com.husker.editor.app.skin.CustomSkin;

import java.awt.*;
import java.lang.reflect.Method;

public class PaintingPanel extends WebPanel {

    private Component content;
    private StyleComponent component;
    private boolean drawBorder = false;

    public PaintingPanel(StyleComponent component){
        setLayout(new AlignLayout());
        this.component = component;
        content = component.createPreviewComponent();
        add(content);

        // setting style id
        try {
            Method method = content.getClass().getDeclaredMethod("setStyleId", StyleId.class);
            method.setAccessible(true);
            method.invoke(content, StyleId.of("::preview::"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void paint(Graphics gr){
        super.paint(gr);
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

    public StyleComponent getComponent(){
        return component;
    }

    public Component getContent(){
        return content;
    }
}
