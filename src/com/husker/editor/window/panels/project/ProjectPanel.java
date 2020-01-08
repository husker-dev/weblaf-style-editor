package com.husker.editor.window.panels.project;

import com.alee.extended.breadcrumb.WebBreadcrumb;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.style.StyleId;
import com.husker.editor.core.FolderElement;
import com.husker.editor.core.FolderRoot;
import com.husker.editor.core.events.*;
import com.husker.editor.core.listeners.editable_object.EditableObjectAdapter;
import com.husker.editor.core.EditableObject;
import com.husker.editor.core.Project;
import com.husker.editor.core.listeners.folder.FolderAdapter;
import com.husker.editor.core.tools.Resources;
import com.husker.editor.core.tools.VisibleUtils;

import javax.swing.*;
import java.awt.*;

public class ProjectPanel extends WebPanel {

    public ProjectPanel(){
        setLayout(new BorderLayout());

        Project.addListener((event) -> setVisible(Project.getCurrentProject() != null));
        setVisible(false);

        add(new WebBreadcrumb(StyleId.of("custom-breadcrumb")){
            {
                setPreferredHeight(27);
                FolderRoot.addFolderListener(new FolderAdapter() {
                    public void folderCreated(FolderCreatedEvent event) {
                        update();
                    }
                    public void folderRemoved(FolderRemovedEvent event) {
                        update();
                    }
                    public void folderClicked(FolderSelectedEvent event) {
                        update();
                    }
                    public void folderParameterChanged(FolderParameterChangedEvent event) {
                        update();
                    }
                    public void selectedFolderChanged(SelectedFolderChanged event) {
                        update();
                    }
                });
                Project.addListener(e -> update());
            }
            void update(){
                removeAll();

                if(!VisibleUtils.onProject())
                    return;
                FolderElement[] elements = Project.getCurrentProject().FolderRoot.getSelectedFolder().getAllParents();

                for(int i = 0; i < elements.length; i++){
                    final int I = i;
                    FolderElement element = elements[elements.length - i - 1];
                    if(element == null)
                        continue;

                    add(new WebPanel(StyleId.panelTransparent){{
                        if(I == 0)
                            setPadding(4, 5, 0, 10);
                        else
                            setPadding(4, 10, 0, 10);
                        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                        add(new WebLabel(element.getTitle(), element.getIcon()){{
                            setPreferredHeight(18);
                        }});
                        add(new WebLabel(StyleId.labelTag, element.getTag()){{
                            setMargin(0, 4, 0, 0);
                            setPreferredHeight(18);
                            setVisible(!element.getTag().isEmpty());
                        }});
                    }});
                }
            }
        }, BorderLayout.CENTER);
    }
}
