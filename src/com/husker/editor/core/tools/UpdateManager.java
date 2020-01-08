package com.husker.editor.core.tools;

import com.alee.extended.layout.HorizontalFlowLayout;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.WebNotification;
import com.husker.editor.core.Main;
import com.husker.editor.core.tools.NetUtils;

public class UpdateManager {

    public static void checkForUpdate(){
        new Thread(() -> {

            String latest_version = getLatestVersion();

            if(getVersionSize(latest_version) > getVersionSize(Main.current_version)){

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
                    add(new WebLabel("WebLaF Style Editor " + latest_version){{
                        setFontSize(14);
                        setBoldFont();
                    }});
                    add(new WebLabel("Is available to download!"){{
                        setFontSize(13);
                    }});
                    add(new WebLabel(Main.current_version + " --> " + latest_version){{
                        setFontSize(13);
                    }});
                    add(new WebPanel(){{
                        setOpaque(false);
                        setLayout(new HorizontalFlowLayout());
                        add(new WebButton("Go to GitHub"){{
                            setFontSize(13);
                            addActionListener(e -> {
                                notification.hidePopup();
                                NetUtils.openBrowserLink("https://github.com/Husker-hub/WebLaF-Style-Editor/releases");
                            });
                        }});
                        add(new WebButton("Close"){{
                            setFontSize(13);
                            addActionListener(e -> notification.hidePopup());
                        }});
                    }});
                }});
                NotificationManager.showNotification(notification);
            }
        }).start();
    }

    public static String getLatestVersion(){
        try {
            return NetUtils.getContent("https://api.github.com/repos/husker-dev/weblaf-style-editor/releases/latest").split("tag_name\":\"")[1].split("\"")[0];
        }catch (Exception ex){
            return "-1";
        }
    }

    public static int getVersionSize(String version){
        if(version.equals("-1"))
            return Integer.MAX_VALUE;
        return Integer.parseInt(version.replace(".",""));
    }
}
