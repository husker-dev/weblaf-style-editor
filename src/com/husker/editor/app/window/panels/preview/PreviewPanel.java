package com.husker.editor.app.window.panels.preview;

import com.alee.extended.tab.DocumentAdapter;
import com.alee.extended.tab.DocumentData;
import com.alee.extended.tab.PaneData;
import com.alee.extended.tab.WebDocumentPane;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.toolbar.WebToolBar;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Components;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.project.StyleComponent;
import com.husker.editor.app.project.listeners.component.ComponentEvent;
import com.husker.editor.app.skin.CustomSkin;

import javax.swing.*;
import javax.swing.text.Style;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.util.HashMap;
import java.util.Map;

import static com.husker.editor.app.project.listeners.component.ComponentEvent.Type.*;
import static com.husker.editor.app.project.listeners.skin.SkinEvent.Type.*;

public class PreviewPanel extends WebPanel {

    private HashMap<StyleComponent, PaintingPanel> paints = new HashMap<>();
    private WebDocumentPane tabbedPane;
    private static WebProgressBar progressBar;
    private static WebToggleButton shape;
    private static ImageIcon style_icon = new ImageIcon("bin/style_small.png");

    public PreviewPanel(){
        setLayout(new BorderLayout());

        CustomSkin.addSkinListener(e -> {
            if(e.getType().oneOf(Skin_Applying))
                progressBar.setVisible(true);
            if(e.getType().oneOf(Last_Applied))
                progressBar.setVisible(false);
            if(e.getType().oneOf(Skin_Applied)){
                SwingUtilities.invokeLater(() -> {
                    try {
                        //updateUI();
                        if(Project.getCurrentProject().Components.getSelectedComponent() != null)
                            paints.get(Project.getCurrentProject().Components.getSelectedComponent()).updateContent();
                    }catch (Exception ex){}
                });
            }
        });

        addComponentListener(new ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                event();
            }
            public void componentMoved(java.awt.event.ComponentEvent e) {
                event();
            }
            public void componentShown(java.awt.event.ComponentEvent e) {
                event();
            }
            public void componentHidden(java.awt.event.ComponentEvent e) {
                event();
            }
            public void event(){
                if(Project.getCurrentProject().Components.getSelectedComponent() != null)
                    paints.get(Project.getCurrentProject().Components.getSelectedComponent()).updateContent();
            }
        });

        Project.addListener(e -> {
            tabbedPane.closeAll();
            for(StyleComponent component : Project.getCurrentProject().Components.getComponents())
                addComponent(component);
        });

        Components.addListener(e -> {
            tabbedPane.setVisible(!(Project.getCurrentProject() == null));

            if(e.getType().oneOf(ComponentEvent.Type.Selected_Changed))
                addComponent((StyleComponent) e.getObjects()[0]);

            if(e.getType().oneOf(Style_Changed) && Project.getCurrentProject().Components.getSelectedComponent() != null)
                updateComponent(Project.getCurrentProject().Components.getSelectedComponent());
        });
        Project.addListener(e -> tabbedPane.setVisible(!(Project.getCurrentProject() == null)));

        tabbedPane = new WebDocumentPane();
        tabbedPane.setTabMenuEnabled(false);
        tabbedPane.setStyleId(StyleId.of("custom-documentpane"));
        tabbedPane.addDocumentListener(new DocumentAdapter() {
            public void selected(DocumentData document, PaneData pane, int index) {
                StyleComponent component = ((PaintingPanel)document.getComponent()).getComponent();
                Project.getCurrentProject().Components.setSelectedComponent(component);
            }
            public void closed(DocumentData document, PaneData pane, int index) {
                removeComponent(((PaintingPanel)document.getComponent()).getComponent());
            }
        });
        add(tabbedPane, BorderLayout.CENTER);
        add(new WebToolBar(){{
            add(new WebButton("Update"){{
                addActionListener(e -> {
                    if(Project.getCurrentProject().Components.getSelectedComponent() != null)
                        paints.get(Project.getCurrentProject().Components.getSelectedComponent()).updateSkin();
                });
            }});
            add(shape = new WebToggleButton("Shape"){{
                addActionListener(e -> {
                    for(Map.Entry<StyleComponent, PaintingPanel> entry : paints.entrySet())
                        entry.getValue().setDrawBorder(isSelected());
                });
            }});

            addToEnd(progressBar = new WebProgressBar(0, 100){{
                setVisible(false);
                setStringPainted(true);
                setString("Applying...");
                setIndeterminate(true);
            }});

        }}, BorderLayout.SOUTH);
    }

    private void addComponent(StyleComponent component){
        if(component != null) {
            if (paints.containsKey(component)) {
                for (int i = 0; i < tabbedPane.getDocumentsCount(); i++)
                    if (tabbedPane.getDocument(i).getComponent() == paints.get(component))
                        tabbedPane.setSelected(i);
            } else {
                PaintingPanel painting = new PaintingPanel(component);
                painting.setDrawBorder(shape.isSelected());

                paints.put(component, painting);
                tabbedPane.openDocument(new DocumentData(component.toString(), style_icon, component.getTitle(), painting));
            }
        }
    }

    private void updateComponent(StyleComponent component){
        PaintingPanel paintingPanel = paints.get(component);

        paintingPanel.updateContent();
        paintingPanel.updateSkin();

        for (int i = 0; i < tabbedPane.getDocumentsCount(); i++) {
            if (tabbedPane.getDocument(i).getComponent() == paints.get(paintingPanel.getComponent())) {
                if(component.getVariableValue("id").equals("")) {
                    tabbedPane.getDocument(i).setTitle(paintingPanel.getComponent().getTitle());
                }else
                    tabbedPane.getDocument(i).setTitle(paintingPanel.getComponent().getTitle() + " ( " + paintingPanel.getComponent().getVariableValue("id") + " )");
            }
        }
    }

    private void removeComponent(StyleComponent component){
        paints.remove(component);
        if(Project.getCurrentProject().Components.getSelectedComponent() == component){
            if(paints.size() > 0){
                for(StyleComponent c : Project.getCurrentProject().Components.getComponents())
                    if(paints.containsKey(c))
                        Project.getCurrentProject().Components.setSelectedComponent(c);
            }else
                Project.getCurrentProject().Components.setSelectedComponent(null);
        }
    }


}
