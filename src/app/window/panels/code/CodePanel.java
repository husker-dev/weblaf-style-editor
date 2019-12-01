package app.window.panels.code;

import app.Components;
import app.Project;
import app.StyleComponent;
import com.alee.extended.syntax.SyntaxPreset;
import com.alee.extended.syntax.WebSyntaxArea;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.style.StyleId;

import java.awt.*;

import static app.Components.ComponentEvent.Selected_Component_Changed;
import static app.Components.ComponentEvent.Style_Parameters_Changed;

public class CodePanel extends WebPanel {

    public CodePanel(){
        setPreferredHeight(200);

        final String emptyText = "<code_panel />";
        final WebSyntaxArea sourceViewer = new WebSyntaxArea ( emptyText, SyntaxPreset.xml, SyntaxPreset.viewable );
        sourceViewer.applyPresets ( SyntaxPreset.base, SyntaxPreset.margin, SyntaxPreset.size, SyntaxPreset.historyLimit );
        add ( sourceViewer.createScroll ( StyleId.syntaxareaScrollUndecorated ), BorderLayout.CENTER );

        Components.addListener((event, objects) -> {
            if(event.oneOf(Selected_Component_Changed, Style_Parameters_Changed)){
                StyleComponent component = Project.getCurrentProject().Components.getSelectedComponent();
                if(component != null)
                    sourceViewer.setText(component.getCode(false).toString());
                else
                    sourceViewer.setText("<code_panel />");
            }

        });
    }
}
