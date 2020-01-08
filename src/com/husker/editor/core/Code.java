package com.husker.editor.core;

import com.husker.editor.core.events.CodeChangedEvent;
import com.husker.editor.core.listeners.code.CodeListener;

import java.util.ArrayList;

public class Code {
    private static ArrayList<CodeListener> listeners = new ArrayList<>();

    public static void addCodeListener(CodeListener listener){
        listeners.add(listener);
    }

    private Project project;
    private String text;
    private EditableObject object;

    public Code(Project project, EditableObject object){
        this.project = project;
        this.object = object;
    }

    public void setText(String text){
        this.text = text;
        Main.event(Code.class, listeners, listener -> listener.changed(new CodeChangedEvent(project, this)));
    }

    public String getText(){
        return text;
    }

    public EditableObject getObject(){
        return object;
    }
}
