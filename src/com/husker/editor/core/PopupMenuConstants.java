package com.husker.editor.core;

import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import com.husker.editor.content.StyleComponent;
import com.husker.editor.content.folders.Folder;
import com.husker.editor.core.tools.Resources;

import java.util.HashMap;
import java.util.Map;

public class PopupMenuConstants {

    public static final String FOLDER = "folder";
    public static final String PROJECT = "project";
    public static final String COMPONENT = "component";

    private static final HashMap<String, WebPopupMenu> elements = new HashMap<>();

    public static void setPopupMenu(String name, WebPopupMenu menu){
        elements.put(name, menu);
    }
    public static WebPopupMenu getPopupMenu(String name){
        return elements.get(name);
    }

    static {
        PopupMenuConstants.setPopupMenu(PopupMenuConstants.FOLDER, new WebPopupMenu(){{
            add(new WebMenu("New"){{
                add(new WebMenuItem("Folder"){{
                    setIcon(Resources.getImageIcon("folder.png"));
                    addActionListener(e -> {
                        Project project = Project.getCurrentProject();
                        project.FolderRoot.getSelectedFolder().addFolder(new Folder(project, "test"));
                    });
                }});
                addSeparator();
                for(Map.Entry<String, Class<? extends StyleComponent>> entry : StyleComponent.components.entrySet()){
                    add(new WebMenuItem(entry.getKey()){{
                        setIcon(Resources.getImageIcon("style.png"));
                        addActionListener(e -> {
                            Project project = Project.getCurrentProject();

                            project.Components.addComponent(entry.getValue(), project.FolderRoot.getSelectedFolder());
                        });
                    }});
                }
            }});
            add(new WebMenuItem("Delete"){{
                addActionListener(e -> {
                    Project.getCurrentProject().FolderRoot.getSelectedFolder().remove();
                });

            }});
        }});
    }

}
