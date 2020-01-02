package com.husker.editor.app.tools;

import javax.swing.*;

public class Resources {

    private static final String folder = "bin/";

    public static ImageIcon getImageIcon(String name){
        return new ImageIcon(folder + name);
    }

    public static String getFolder(){
        return folder;
    }
}
