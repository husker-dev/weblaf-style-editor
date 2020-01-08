package com.husker.editor.window.panels.folders;

import com.husker.editor.core.FolderElement;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class FolderTransfer implements Transferable {

    private FolderElement folder;
    public final static DataFlavor FOLDER_FLAVOR = new DataFlavor(FolderElement.class, FolderElement.class.getSimpleName());

    public FolderTransfer(FolderElement folder){
        this.folder = folder;
    }
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{
                FOLDER_FLAVOR
        };
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return FOLDER_FLAVOR.equals(flavor);
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (flavor.equals(FOLDER_FLAVOR))
            return folder;
        else
            throw new UnsupportedFlavorException(flavor);
    }
}
