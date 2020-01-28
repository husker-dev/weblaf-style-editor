package com.husker.editor.content.style;

import com.alee.api.resource.ClassResource;
import com.alee.extended.layout.AlignLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.managers.style.StyleId;
import com.alee.managers.style.StyleManager;
import com.alee.managers.style.Styleable;
import com.alee.managers.style.XmlSkin;
import com.alee.managers.style.data.SkinInfo;
import com.alee.utils.XmlUtils;
import com.husker.editor.content.style.StyleComponent;
import com.husker.editor.core.EditableObject;
import com.husker.editor.core.Preview;
import com.husker.editor.core.PreviewUI;
import com.husker.editor.core.events.*;
import com.husker.editor.core.listeners.editable_object.EditableObjectAdapter;
import com.husker.editor.core.listeners.skin.SkinAdapter;
import com.husker.editor.core.skin.CustomSkin;
import com.husker.editor.core.tools.VisibleUtils;
import com.husker.editor.core.xml.XMLHead;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StylePreview extends Preview {

    private StyleComponent component;
    private JComponent preview;
    private boolean show_shape = false;

    private StylePreviewPanel content;
    private WebProgressBar ui_style_applying;
    private WebButton ui_update;
    private WebToggleButton ui_shape;

    public StylePreview(StyleComponent component) {
        super(component);
        this.component = component;

        CustomSkin.addSkinListener(new SkinAdapter() {
            public void lastApplied(LastSkinAppliedEvent event) {
                if(ui_style_applying != null)
                    ui_style_applying.setVisible(false);
            }
            public void applying(SkinApplyingEvent event) {
                if(ui_style_applying != null)
                    ui_style_applying.setVisible(true);
            }
        });
        EditableObject.addEditableObjectListener(new EditableObjectAdapter() {
            public void variableChanged(VariableChangedEvent event) {
                if(VisibleUtils.isEditableObject(event.getObject()))
                    updateSkin();
            }
            public void codeChanged(CodeChangedEvent event) {
                if(VisibleUtils.isEditableObject(event.getObject()))
                    updateSkin();
            }
        });
    }

    public void showed(){
        updateSkin();
    }

    public PreviewUI createUI() {
        PreviewUI ui = new PreviewUI();

        preview = component.createPreviewComponent();
        if(preview != null && preview instanceof Styleable)
            ((Styleable) preview).setStyleId(StyleId.of("::preview::"));

        ui_update = new WebButton("Update"){{
            addActionListener(e -> {
                if ((e.getModifiers() & InputEvent.SHIFT_MASK) != 0) {

                    InputStream in = new ClassResource(CustomSkin.class, "editor_skin.xml").getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    try {
                        String code = "";
                        String line;
                        while ((line = reader.readLine()) != null)
                            code += line;

                        SkinInfo skinInfo = XmlUtils.fromXML(code.replace("<!-- TEST STYLE HERE -->", component.getCode()));
                        StyleManager.setSkin(new XmlSkin(skinInfo));
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
                updateSkin();
            });
        }};

        ui_shape = new WebToggleButton("Shape"){{
            addActionListener(e -> {
                show_shape = isSelected();
                content.repaint();
            });
        }};
        ui_style_applying = new WebProgressBar(0, 100){{
            setVisible(false);
            setStringPainted(true);
            setString("Applying...");
            setIndeterminate(true);
        }};
        content = new StylePreviewPanel();

        ui.setContent(content);
        ui.addTool(ui_update);
        ui.addTool(ui_shape);
        ui.addToolToRight(ui_style_applying);
        return ui;
    }

    public void removeUI() {
        super.removeUI();
        ui_update = null;
        ui_shape = null;
        ui_style_applying = null;
        preview = null;
    }

    public void updateSkin(){
        if(preview != null && VisibleUtils.isEditableObject(getObject())) {
            try {
                XMLHead head = XMLHead.fromString(getObject().getCode());
                head.setParameter("id", "::preview::");

                CustomSkin.applySkin(preview, head.toString());
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    class StylePreviewPanel extends WebPanel{
        public StylePreviewPanel(){
            setLayout(new AlignLayout());
            add(preview);
        }

        public void paint(Graphics gr){
            super.paint(gr);
            if(preview != null) {
                if (show_shape) {
                    gr.setColor(Color.red);
                    gr.fillRect((getWidth() - preview.getWidth()) / 2, (getHeight() - preview.getHeight()) / 2, preview.getWidth(), preview.getHeight());
                }
            }
            super.paintComponents(gr);
        }
    }
}
