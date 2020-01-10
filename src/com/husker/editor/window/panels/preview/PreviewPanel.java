package com.husker.editor.window.panels.preview;

import com.alee.api.annotations.NotNull;
import com.alee.api.annotations.Nullable;
import com.alee.extended.label.WebStyledLabel;
import com.alee.extended.tab.*;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.tabbedpane.TabComponent;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.toolbar.WebToolBar;
import com.alee.managers.icon.Icons;
import com.alee.managers.style.StyleId;
import com.husker.editor.core.EditableObject;
import com.husker.editor.core.FolderElement;
import com.husker.editor.core.FolderRoot;
import com.husker.editor.core.Project;
import com.husker.editor.core.events.*;
import com.husker.editor.core.listeners.editable_object.EditableObjectAdapter;
import com.husker.editor.core.listeners.folder.FolderAdapter;

import javax.swing.*;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.lang.ref.WeakReference;
import java.util.HashMap;


public class PreviewPanel extends WebPanel {

    private HashMap<String, EditableObject> opened_objects = new HashMap<>();
    private String current_id = "";

    private CustomDocumentPane components_tab;
    private WebToolBar toolBar;

    public PreviewPanel(){
        setLayout(new BorderLayout());

        EditableObject.addEditableObjectListener(new EditableObjectAdapter() {
            public void selectedChanged(SelectedChangedEvent event) {
                addComponent(event.getObject());
            }
            public void objectRemoved(EditableObjectRemovedEvent event) {
                if(opened_objects.containsKey(getId(event.getObject())))
                    components_tab.closeDocument(getId(event.getObject()));
            }
        });

        FolderRoot.addFolderListener(new FolderAdapter() {
            public void folderParameterChanged(FolderParameterChangedEvent event) {
                if(event.getElement().getObject() != null) {
                    EditableObject object = event.getElement().getObject();
                    if(opened_objects.containsKey(getId(object)))
                        components_tab.getDocument(getId(object)).setTitle(getTitle(event.getElement()));
                }
            }
        });

        components_tab = new CustomDocumentPane();
        components_tab.addDocumentListener(new DocumentAdapter() {
            public void selected(DocumentData document, PaneData pane, int index) {
                if(opened_objects.containsKey(current_id))
                    opened_objects.get(current_id).getPreview().hidden();
                current_id = document.getId();

                Project.getCurrentProject().setSelectedObject(opened_objects.get(current_id));
                opened_objects.get(current_id).getPreview().showed();

                toolBar.removeAll();
                for(Component component : opened_objects.get(current_id).getPreview().getUI().getTools())
                    toolBar.add(component);
                for(Component component : opened_objects.get(current_id).getPreview().getUI().getToolsToRight())
                    toolBar.addToEnd(component);
            }
            public void closed(DocumentData document, PaneData pane, int index) {
                opened_objects.get(document.getId()).getPreview().hidden();
                removeComponent(opened_objects.get(document.getId()));
            }
        });
        add(components_tab, BorderLayout.CENTER);
        add(toolBar = new WebToolBar(), BorderLayout.SOUTH);
    }

    private void addComponent(EditableObject component){
        if(component != null) {
            if (opened_objects.containsKey(getId(component))) {
                components_tab.setSelected(getId(component));
            } else {
                opened_objects.put(getId(component), component);
                Component content = component.getPreview().getUI(true).getContent();
                components_tab.openDocument(new DocumentData(getId(component), component.getFolder().getIcon(), getTitle(component.getFolder()), content));

            }
        }
    }

    private void removeComponent(EditableObject component){
        component.getPreview().removeUI();
        opened_objects.remove(getId(component));
        if(opened_objects.size() == 0)
            Project.getCurrentProject().resetSelectedObject();
    }

    private String getId(EditableObject object){
        return object.toString();
    }

    public String getTitle(FolderElement folderElement){
        if(folderElement.getTag().isEmpty())
            return folderElement.getTitle();
        return folderElement.getTitle() + "(" + folderElement.getTag() + ")";
    }


    public static class CustomDocumentPane extends WebDocumentPane{

        public CustomDocumentPane(){
            super(
                    StyleId.of("custom-documentpane"),
                    tabbedPane -> ((WebTabbedPane)tabbedPane).setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT),
                    CustomTitleComponent::new
            );

            getActivePane().getTabbedPane().getUI().getTabContainer().setPreferredHeight(27);

            setSplitEnabled(true);
            setTabMenuEnabled(false);
        }
    }

    public static class CustomTitleComponent<T extends DocumentData> extends WebPanel implements TabComponent, UIResource {

        protected JComponent title;
        protected AbstractButton closeButton;

        protected WebStyledLabel titleLabel;
        protected WebLabel tagLabel;

        protected String text;

        public CustomTitleComponent(PaneData<T> paneData, T document, MouseAdapter mouseAdapter) {
            super(StyleId.documentpaneTabPanel.at(paneData.getTabbedPane()), new BorderLayout(2, 0));

            this.text = document.getTitle();

            this.addMouseListener(mouseAdapter);
            this.addMouseMotionListener(mouseAdapter);

            this.title = this.createTitle(document, mouseAdapter);
            this.add(this.title, "Center");
            if (paneData.getDocumentPane().isClosable() && document.isClosable()) {
                this.closeButton = this.createCloseButton(paneData, document);
                this.add(this.closeButton, "After");
            }
        }

        protected JPanel createTitle(T document, MouseAdapter mouseAdapter) {
            StyleId titleStyleId = StyleId.documentpaneTabTitle.at(this);

            titleLabel = new WebStyledLabel(titleStyleId, document.getIcon());
            tagLabel = new WebLabel(StyleId.labelTag);
            tagLabel.setMargin(0, 6, 0, 0);

            if(document.getTitle().contains("(") && document.getTitle().contains(")")){
                String text = document.getTitle();
                titleLabel.setText(text.substring(0, text.indexOf("(")));
                tagLabel.setText(text.substring(text.indexOf("(") + 1, text.lastIndexOf(")")));
            }else{
                titleLabel.setText(document.getTitle());
                tagLabel.setVisible(false);
            }


            titleLabel.addMouseListener(mouseAdapter);
            titleLabel.addMouseMotionListener(mouseAdapter);

            //tagLabel.addMouseListener(mouseAdapter);
            //tagLabel.addMouseMotionListener(mouseAdapter);

            WebPanel panel = new WebPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
            panel.add(titleLabel);
            panel.add(tagLabel);

            return panel;
        }

        protected AbstractButton createCloseButton(PaneData<T> paneData, T document) {
            final WeakReference<T> weakDocument = new WeakReference(document);
            StyleId closeButtonStyleId = StyleId.documentpaneTabCloseButton.at(this);
            WebButton closeButton = new WebButton(closeButtonStyleId, Icons.crossSmall, Icons.crossSmallHover);
            closeButton.addActionListener(e -> paneData.close(weakDocument.get()));
            return closeButton;
        }

        public Icon getIcon() {
            return titleLabel.getIcon();
        }

        public String getTitle() {
            return text;
        }
    }

}
