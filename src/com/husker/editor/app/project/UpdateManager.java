package com.husker.editor.app.project;

import com.alee.extended.layout.HorizontalFlowLayout;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.WebNotification;
import com.husker.editor.app.Main;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

public class UpdateManager {

    public static void checkForUpdate(){
        new Thread(() -> {

            String last_version = getLatestVersion();

            if(last_version != null && getVersionSize(last_version) > getVersionSize(Main.current_version)){

                WebNotification notification = new WebNotification();
                notification.setClickToClose(false);
                notification.setPreferredWidth(300);
                notification.setContent(new WebPanel(){{
                    setOpaque(false);
                    setLayout(new VerticalFlowLayout(0,10));
                    add(new WebLabel("Update!"){{
                        setFontSize(13);
                        setBoldFont();
                    }});
                    add(new WebLabel("WebLaF Style Editor " + last_version){{
                        setFontSize(14);
                        setBoldFont();
                    }});
                    add(new WebLabel("Is available to download!"){{
                        setFontSize(13);
                    }});
                    add(new WebLabel(Main.current_version + " --> " + last_version){{
                        setFontSize(13);
                    }});
                    add(new WebPanel(){{
                        setOpaque(false);
                        setLayout(new HorizontalFlowLayout());
                        add(new WebButton("Go to GitHub"){{
                            setFontSize(13);
                            addActionListener (e -> {
                                notification.hidePopup();
                                String url = "https://github.com/Husker-hub/WebLaF-Style-Editor/releases";
                                try {
                                    if (Desktop.isDesktopSupported()) {
                                        Desktop desktop = Desktop.getDesktop();
                                        desktop.browse(new URI(url));
                                    } else {
                                        Runtime runtime = Runtime.getRuntime();
                                        runtime.exec("xdg-open " + url);
                                    }
                                }catch (Exception ex){
                                    ex.printStackTrace();
                                }
                            });
                        }});
                        add(new WebButton("Close"){{
                            setFontSize(13);
                            addActionListener (e -> {
                                notification.hidePopup();
                            });
                        }});
                    }});
                }});
                NotificationManager.showNotification ( notification );
            }

        }).start();
    }

    public static String getLatestVersion(){
        try {
            URL url = new URL("https://api.github.com/repos/husker-dev/weblaf-style-editor/releases/latest");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            return sb.toString().split("tag_name\":\"")[1].split("\"")[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getVersionSize(String version){
        return Integer.parseInt(version.replace(".",""));
    }

    private void showNotification(Component component){
        WebNotification notificationPopup = new WebNotification();
        notificationPopup.setClickToClose(false);
        notificationPopup.setPreferredWidth(300);
        notificationPopup.setContent(component);

        NotificationManager.showNotification ( notificationPopup );
    }
}
