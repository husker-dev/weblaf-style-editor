package com.husker.editor.window.panels.folders;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.managers.style.StyleId;
import com.husker.editor.core.FolderElement;
import com.husker.editor.core.FolderRoot;
import com.husker.editor.core.events.*;
import com.husker.editor.core.Project;
import com.husker.editor.core.listeners.folder.FolderAdapter;


import javax.swing.*;
import java.util.HashMap;

public class FoldersPanel extends WebPanel {

    private HashMap<FolderElement, FolderPanel> folders = new HashMap<>();

    public FoldersPanel(){
        setPreferredWidth(250);

        add(new WebScrollPane(StyleId.scrollpaneUndecorated, new WebPanel(){
            {
                setLayout(new VerticalFlowLayout(0, 0));

                Project.addListener(e -> applyProject(e.getProject()));
                FolderRoot.addFolderListener(new FolderAdapter() {
                    public void folderCreated(FolderCreatedEvent event) {
                        addElement(event.getFolder());
                    }
                    public void folderRemoved(FolderRemovedEvent event) {
                        removeFolder(event.getFolder());
                    }
                    public void folderParameterChanged(FolderParameterChangedEvent event) {
                        parameterChanged(event);
                    }
                    public void selectedFolderChanged(SelectedFolderChanged event) {
                        selectedChanged(event.getFolder(), event.getOldFolder());
                    }
                    public void folderMoved(FolderMovedEvent event) {
                        moveFolder(event.getFolder(), event.getFrom(), event.getTo());
                    }
                });
            }
            void moveFolder(FolderElement folder, FolderElement from, FolderElement to){
                SwingUtilities.invokeLater(() -> {
                    if(folders.get(from) == null)
                        remove(folders.get(folder));
                    else
                        folders.get(from).removeFolder(folders.get(folder));
                    if(folders.get(to) == null)
                        add(folders.get(folder));
                    else
                        folders.get(to).addFolder(folders.get(folder));
                    updateUI();
                });
            }
            void selectedChanged(FolderElement new_folder, FolderElement old_folder){
                SwingUtilities.invokeLater(() -> {
                    if(folders.get(old_folder) != null)
                        folders.get(old_folder).selectUpdate();
                    folders.get(new_folder).selectUpdate();
                    updateUI();
                });
            }
            void parameterChanged(FolderParameterChangedEvent event){
                SwingUtilities.invokeLater(() -> {
                    folders.get(event.getElement()).update(event.getParameter());
                    updateUI();
                });
            }
            void applyProject(Project project){
                SwingUtilities.invokeLater(() -> {
                    removeAll();
                    for(FolderElement element : project.FolderRoot.getSubFolders())
                        addElement(element);
                    updateUI();
                });
            }
            void addElement(FolderElement element){
                SwingUtilities.invokeLater(() -> {
                    FolderPanel panel = new FolderPanel(FoldersPanel.this, element);
                    folders.put(element, panel);

                    if(element.getParent() == null)
                        add(panel);
                    else
                        folders.get(element.getParent()).addFolder(panel);
                    updateUI();
                });
            }
            void removeFolder(FolderElement element){
                SwingUtilities.invokeLater(() -> {
                    FolderPanel panel = folders.get(element);
                    if(element.getParent() == null)
                        remove(panel);
                    else
                        folders.get(element.getParent()).removeFolder(panel);
                    updateUI();
                });
            }
        }));
    }

    public FolderPanel getFolderPanel(FolderElement element){
        return folders.get(element);
    }
}





