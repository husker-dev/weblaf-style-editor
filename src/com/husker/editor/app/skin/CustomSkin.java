package com.husker.editor.app.skin;

import app.window.panels.preview.PreviewPanel;
import com.alee.laf.button.WebButton;
import com.alee.managers.style.Skin;
import com.alee.managers.style.StyleId;
import com.alee.managers.style.StyleManager;
import com.alee.managers.style.XmlSkin;
import com.alee.managers.style.data.SkinInfo;
import com.alee.utils.XmlUtils;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;

public class CustomSkin extends XmlSkin {

    static String pattern = "<skin xmlns=\"http://weblookandfeel.com/XmlSkin\">\n" +
            "<id>husker.editor.skin</id>\n" +
            "<class>app.skin.CustomSkin</class>\n" +
            "<supportedSystems>all</supportedSystems>\n" +
            "<title>Custom skin</title>\n" +
            "<description>WebLaF Editor Skin</description>\n" +
            "<author>Husker</author>\n" +
            "<include nearClass=\"com.alee.skin.web.WebSkin\">resources/skin.xml</include>\n" +
            "<!-- CODE -->\n" +
            "</skin>";

    static boolean applying = false;
    static int thread_id = 0;

    public CustomSkin() {
        super(CustomSkin.class, "editor_skin.xml");
    }

    public static void applySkin(Component component, String skin){
        new Thread(() -> {
            thread_id = (int)Thread.currentThread().getId();
            try {
                while(applying)
                    Thread.sleep(50);

                if(thread_id != (int)Thread.currentThread().getId())
                    return;
                applying = true;

                PreviewPanel.progressBar.setVisible(true);

                String text = pattern.replace("<!-- CODE -->", skin);
                SkinInfo skinInfo = XmlUtils.fromXML(text);

                StyleManager.setSkin((JComponent)component, new XmlSkin(skinInfo));
            } catch (Exception e) {
                //e.printStackTrace();
            }finally {
                applying = false;
                if(thread_id == (int)Thread.currentThread().getId())
                    PreviewPanel.progressBar.setVisible(false);
            }
        }).start();
    }
}
