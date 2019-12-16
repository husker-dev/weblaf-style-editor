package com.husker.editor.app.window.panels.code;

import com.alee.extended.syntax.SyntaxPreset;
import com.alee.extended.syntax.WebSyntaxArea;
import com.alee.extended.syntax.WebSyntaxScrollPane;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Components;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.project.StyleComponent;
import com.husker.editor.app.project.listeners.project.ProjectEvent;
import org.fife.ui.autocomplete.*;

import java.awt.*;

import static com.husker.editor.app.project.listeners.component.ComponentEvent.Type.*;


public class CodePanel extends WebPanel {

    private WebSyntaxArea sourceViewer;
    private WebSyntaxScrollPane scroll;

    public CodePanel(){
        setPreferredHeight(200);

        sourceViewer = new WebSyntaxArea(SyntaxPreset.xml, SyntaxPreset.editable);
        sourceViewer.applyPresets(SyntaxPreset.base, SyntaxPreset.margin, SyntaxPreset.size, SyntaxPreset.historyLimit);
        scroll = sourceViewer.createScroll(StyleId.syntaxareaScrollUndecorated);
        add(scroll, BorderLayout.CENTER);

        Components.addListener(e -> {
            if(e.getType().oneOf(Selected_Changed, Style_Changed))
                updateText();
        });
        Project.addListener(e -> {
            if(e.getType().oneOf(ProjectEvent.Type.Changed))
                updateText();
        });

        new AutoCompletion(createCompletionProvider()).install(sourceViewer);
    }

    public void updateText(){
        StyleComponent component = Project.getCurrentProject().Components.getSelectedComponent();
        if(component != null)
            sourceViewer.setText("<!--test--> \n" + component.getXMLStyle().toString());
        else
            sourceViewer.setText("");
        scroll.setEnabled(!(Project.getCurrentProject() == null || Project.getCurrentProject().Components.getSelectedComponent() == null));
    }

    private CompletionProvider createCompletionProvider() {

        // A DefaultCompletionProvider is the simplest concrete implementation
        // of CompletionProvider. This provider has no understanding of
        // language semantics. It simply checks the text entered up to the
        // caret position for a match against known completions. This is all
        // that is needed in the majority of cases.
        DefaultCompletionProvider provider = new DefaultCompletionProvider();

        // Add completions for all Java keywords. A BasicCompletion is just
        // a straightforward word completion.
        provider.addCompletion(new BasicCompletion(provider, "abstract"));
        provider.addCompletion(new BasicCompletion(provider, "assert"));
        provider.addCompletion(new BasicCompletion(provider, "break"));
        provider.addCompletion(new BasicCompletion(provider, "case"));
        // ... etc ...
        provider.addCompletion(new BasicCompletion(provider, "transient"));
        provider.addCompletion(new BasicCompletion(provider, "try"));
        provider.addCompletion(new BasicCompletion(provider, "void"));
        provider.addCompletion(new BasicCompletion(provider, "volatile"));
        provider.addCompletion(new BasicCompletion(provider, "while"));

        // Add a couple of "shorthand" completions. These completions don't
        // require the input text to be the same thing as the replacement text.
        provider.addCompletion(new ShorthandCompletion(provider, "sysout",
                "System.out.println(", "System.out.println("));
        provider.addCompletion(new ShorthandCompletion(provider, "syserr",
                "System.err.println(", "System.err.println("));

        return provider;

    }
}
