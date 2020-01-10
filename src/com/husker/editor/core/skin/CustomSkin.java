package com.husker.editor.core.skin;

import com.alee.api.resource.ClassResource;
import com.alee.managers.style.StyleManager;
import com.alee.managers.style.XmlSkin;
import com.alee.managers.style.data.SkinInfo;
import com.alee.utils.XmlUtils;
import com.husker.editor.core.Main;
import com.husker.editor.core.events.LastSkinAppliedEvent;
import com.husker.editor.core.events.SkinAppliedEvent;
import com.husker.editor.core.events.SkinApplyingEvent;
import com.husker.editor.core.Error;
import com.husker.editor.core.listeners.skin.SkinListener;
import com.husker.editor.core.Project;
import com.husker.editor.core.xml.XMLHead;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CustomSkin extends XmlSkin {

    private static ArrayList<SkinListener> listeners = new ArrayList<>();

    private static String pattern = "<skin xmlns=\"http://weblookandfeel.com/XmlSkin\">\n" +
            "    <id>husker.editor.skin</id>\n" +
            "    <class>com.husker.editor.core.skin.CustomSkin</class>\n" +
            "    <supportedSystems>all</supportedSystems>\n" +
            "    <title>Custom skin</title>\n" +
            "    <description>WebLaF Editor Skin</description>\n" +
            "    <author>Husker</author>\n" +
            "    <include nearClass=\"com.alee.skin.light.WebLightSkin\">resources/web-light-skin.xml</include>\n" +
            "\n" +
            "<!-- CODE -->\n" +
            "</skin>";

    private static boolean applying = false;
    private static int last_thread_id = 0;

    public CustomSkin() {
        super(new ClassResource(CustomSkin.class, "editor_skin.xml"));
    }

    public static void applySkin(JComponent component, String code){
        Project project = Project.getCurrentProject();
        new Thread(() -> {
            last_thread_id = (int)Thread.currentThread().getId();

            try {
                while(applying)
                    Thread.sleep(50);

                if(last_thread_id != (int)Thread.currentThread().getId())
                    return;
                applying = true;


                project.Errors.removeError("style_applying");
                Main.event(CustomSkin.class, listeners, listener -> listener.applying(new SkinApplyingEvent(project)));

                String text = pattern.replace("<!-- CODE -->", code);

                SkinInfo skinInfo = XmlUtils.fromXML(text);
                skinInfo.install();
                StyleManager.setSkin(component, new XmlSkin(skinInfo));
                //component.

            } catch (Exception e) {
                if(e.getMessage().contains("extends missing style")){
                    String style_name = e.getMessage().split("'")[1].split("'")[0];
                    String extends_name = e.getMessage().split("'")[3].split("'")[0];
                    if(style_name.equals("::preview::"))
                        project.Errors.addError(new Error("style_applying", "Incorrect Style",  "Style extends missing style '" + extends_name + "'"));
                    else
                        project.Errors.addError(new Error("style_applying", "Incorrect Style",  "Style '" + style_name + "' extends missing style '" + extends_name + "'"));
                }else{
                    project.Errors.addError(new Error("style_applying", "Incorrect Style",  e.getMessage()));
                }
            }finally {
                applying = false;
                if(last_thread_id == (int)Thread.currentThread().getId())
                    Main.event(CustomSkin.class, listeners, listener -> listener.lastApplied(new LastSkinAppliedEvent(project)));
                Main.event(CustomSkin.class, listeners, listener -> listener.applied(new SkinAppliedEvent(project)));
            }

        }).start();
    }
    public static void applySkin(JComponent component, XMLHead skin){
        applySkin(component, skin.toString(1));
    }

    public static void addSkinListener(SkinListener listener){
        listeners.add(listener);
    }
}
