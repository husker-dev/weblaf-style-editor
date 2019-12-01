package app.window.components;

import com.alee.extended.panel.WebComponentPane;
import com.alee.extended.panel.WebSelectablePanel;
import com.alee.managers.focus.FocusManager;
import com.alee.managers.style.StyleId;

import java.awt.*;

public class MovableComponentList extends WebComponentPane {

    public MovableComponentList(){
        setStyleId(StyleId.componentpane);
        setReorderingAllowed(true);
        //setShowReorderGrippers(false);
    }

    public WebSelectablePanel addElement(Component component){
        WebSelectablePanel selectablePanel = super.addElement(component);
        FocusManager.removeFocusTrackers(selectablePanel);
        return selectablePanel;
    }
}
