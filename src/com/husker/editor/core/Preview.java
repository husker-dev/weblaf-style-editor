package com.husker.editor.core;

public abstract class Preview {

    private EditableObject object;
    private PreviewUI ui;

    public Preview(EditableObject object){
        this.object = object;
    }

    public PreviewUI getUI(){
        return getUI(false);
    }
    public PreviewUI getUI(boolean create){
        if(create){
            if(ui != null)
                return ui;
            else
                return ui = createUI();
        }else
            return ui;
    }
    public void removeUI(){
        ui = null;
    }

    protected abstract PreviewUI createUI();

    public Project getProject(){
        return object.getProject();
    }
    public EditableObject getObject(){
        return object;
    }

    public void showed(){ }
    public void hidden(){ }
}
