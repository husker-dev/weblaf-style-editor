package com.husker.editor.window.panels.folders;


import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.style.StyleId;
import com.husker.editor.core.FolderElement;
import com.husker.editor.core.events.FolderParameterChangedEvent;
import com.husker.editor.core.tools.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;


class FolderPanel extends WebPanel{

    private WebLabel name, tag, separator, resize;
    private WebPanel title, children;

    private FolderElement element;
    private FoldersPanel foldersPanel;

    private static ImageIcon arrow_closed, arrow_opened;
    static {
        arrow_closed = Resources.getImageIcon("arrow_closed.png");
        arrow_opened = Resources.getImageIcon("arrow_opened.png");
    }

    public FolderPanel(FoldersPanel foldersPanel, FolderElement element){
        this.foldersPanel = foldersPanel;
        this.element = element;
        setLayout(new BorderLayout(0, 0));

        title = new WebPanel(){{
            setPadding(1, element.getAllParentCount() * 20, 1, 0);
            setLayout(new BorderLayout(0, 0));
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent event) {
                    element.setSelected();
                    title.requestFocusInWindow();

                    if(event.getButton() == MouseEvent.BUTTON3){
                        WebPopupMenu menu = element.getPopupMenu();
                        if(menu != null)
                            menu.show(title, event.getX(), event.getY());
                    }
                }
                public void mouseClicked(MouseEvent event){
                    if(event.getClickCount() % 2 == 0)
                        element.selected();
                }
            });
            addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_ENTER)
                        element.selected();
                    if(e.getKeyCode() == KeyEvent.VK_DELETE && element.isRemovable())
                        element.remove();
                }
            });

            add(new WebPanel(StyleId.panelTransparent){{

                setFocusable(true);
                setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

                add(resize = new WebLabel(){{
                    setPadding(0, 3, 0, 3);
                    setPreferredSize(22, 15);
                    addMouseListener(new MouseAdapter() {
                        public void mousePressed(MouseEvent e) {
                            if(element.getFolders().length == 0)
                                return;
                            element.setOpened(!element.isOpened());
                        }
                    });
                }});

                add(name = new WebLabel(element.getTitle(), element.getIcon()){{
                    setPreferredHeight(18);
                }});
                add(separator = new WebLabel("  "){{
                    setPreferredHeight(18);
                    setVisible(false);
                }});
                add(tag = new WebLabel(StyleId.labelTag, element.getTag()){{
                    setPreferredHeight(18);
                    setVisible(false);
                }});
            }});
        }};

        DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer(title, DnDConstants.ACTION_MOVE, drg -> drg.startDrag(null, new FolderTransfer(element), null));
        new DropTarget(title, DnDConstants.ACTION_MOVE, new DropTargetAdapter() {
            public void dragEnter(DropTargetDragEvent dtde) {

            }
            public void dragOver(DropTargetDragEvent dtde) {

            }
            public void dropActionChanged(DropTargetDragEvent dtde) {

            }
            public void dragExit(DropTargetEvent dte) {

            }
            public void drop(DropTargetDropEvent event) {
                if (event.getTransferable().isDataFlavorSupported(FolderTransfer.FOLDER_FLAVOR)) {
                    try {
                        FolderElement transferData = (FolderElement) event.getTransferable().getTransferData(FolderTransfer.FOLDER_FLAVOR);
                        event.acceptDrop(DnDConstants.ACTION_MOVE);

                        transferData.moveTo(element);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        event.rejectDrop();
                    }
                }else
                    event.rejectDrop();
            }
        });

        add(title);
        add(children = new WebPanel(){
            {
                setLayout(new VerticalFlowLayout(0, 0));
            }
        }, BorderLayout.SOUTH);

        selectUpdate();
        updateArrow();
        updatePadding();
    }

    public void sort(){
        SwingUtilities.invokeLater(() -> {
            FolderElement[] elements = element.getFolders();
            children.removeAll();
            for(FolderElement sorted_element : elements)
                children.add(foldersPanel.getFolderPanel(sorted_element));

            updatePadding();
            updateUI();
        });
    }

    public void updatePadding(){
        title.setPadding(1, element.getAllParentCount() * 20, 1, 0);
        for(Component component : children.getComponents())
            ((FolderPanel)component).updatePadding();
    }

    public void addFolder(FolderPanel panel){
        children.add(panel);
        updateArrow();
        sort();
    }
    public void removeFolder(FolderPanel panel){
        children.remove(panel);
        updateArrow();
    }

    public void updateArrow(){
        if(element.getFolders().length != 0) {
            if (element.isOpened()) {
                children.setVisible(true);
                resize.setIcon(arrow_opened);
            } else {
                children.setVisible(false);
                resize.setIcon(arrow_closed);
            }
        }else{
            children.setVisible(false);
            resize.setIcon(null);
        }

    }

    public void selectUpdate(){
        if(element.isSelected())
            title.setStyleId(StyleId.of("focused-tree-element"));
        else
            title.setStyleId(StyleId.panel);
    }
    public void update(FolderParameterChangedEvent.FolderParameter parameter){
        if(parameter == FolderParameterChangedEvent.FolderParameter.STATE)
            updateArrow();
        if(parameter == FolderParameterChangedEvent.FolderParameter.TITLE) {
            name.setText(element.getTitle());

            if(element.getParent() != null)
                foldersPanel.getFolderPanel(element.getParent()).sort();
        }
        if(parameter == FolderParameterChangedEvent.FolderParameter.TAG) {
            tag.setVisible(!element.getTag().isEmpty());
            separator.setVisible(!element.getTag().isEmpty());
            tag.setText(element.getTag());

            if(element.getParent() != null)
                foldersPanel.getFolderPanel(element.getParent()).sort();
        }
        if(parameter == FolderParameterChangedEvent.FolderParameter.ICON)
            name.setIcon(element.getIcon());
    }
}