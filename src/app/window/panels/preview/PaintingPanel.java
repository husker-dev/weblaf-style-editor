package app.window.panels.preview;

import app.Components;
import app.Project;
import app.skin.CustomSkin;
import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.style.Skin;
import com.alee.managers.style.StyleId;
import com.alee.managers.style.StyleManager;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.lang.reflect.Method;

public class PaintingPanel extends WebPanel {

    Component content;
    public boolean drawBorder = false;

    public PaintingPanel(){
        setLayout(null);

        Components.addListener((event, objects) -> {
            if(event.oneOf(Components.ComponentEvent.Selected_Component_Changed)){
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
                updateContent();
            }
            if(event.oneOf(Components.ComponentEvent.Style_Parameters_Changed)){
                CustomSkin.applySkin(content, Project.getCurrentProject().Components.getSelectedComponent().getCode(true).toString());
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
        gr.setColor(Color.white);
        gr.fillRect(0, 0, getWidth(), getHeight());
        if(content != null) {
            if(drawBorder) {
                gr.setColor(Color.red);
                gr.fillRect((getWidth() - content.getWidth()) / 2, (getHeight() - content.getHeight()) / 2, content.getWidth(), content.getHeight());
            }
        }
        super.paintComponents(gr);

    }
}
