package com.husker.editor.app.project;

import com.husker.editor.app.components.Styled_Button;
import com.husker.editor.app.components.Styled_Label;
import com.husker.editor.app.parameters.*;
import com.husker.editor.app.project.listeners.component.ComponentEvent;
import com.husker.editor.app.xml.XMLHead;
import com.husker.editor.app.xml.XMLParameter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static com.husker.editor.app.project.listeners.component.ComponentEvent.Type.*;

public abstract class StyleComponent extends AbstractParameterReceiver implements Cloneable{

    // Static
    public static HashMap<String, Class<? extends StyleComponent>> components = new HashMap<String, Class<? extends StyleComponent>>(){{
        put("Button", Styled_Button.class);
        put("Label", Styled_Label.class);
    }};

    private static ImageIcon example_icon = new ImageIcon("bin/icon_example.png");

    public static class Parameters {
        public static final StaticVariable ID = new StaticVariable("id");
        public static final StaticVariable EXTENDS = new StaticVariable("extends");
        public static final StaticVariable DECORATIONS = new StaticVariable("decorations", "true");
        public static final StaticVariable OVERWRITE_DECORATIONS = new StaticVariable("overwrite_decorations", "false");
        public static final StaticVariable ROUND_TYPE = new StaticVariable("round_type", "Full");
        public static final StaticVariable ROUND_FULL = new StaticVariable("round_full", "0");
        public static final StaticVariable ROUND_LT = new StaticVariable("round_lt", "0");   // Left top
        public static final StaticVariable ROUND_RT = new StaticVariable("round_rt", "0");   // Right top
        public static final StaticVariable ROUND_LB = new StaticVariable("round_lb", "0");   // Left bottom
        public static final StaticVariable ROUND_RB = new StaticVariable("round_rb", "0");   // Right bottom
        public static final StaticVariable SHAPE_ENABLED = new StaticVariable("shape_enabled", "false");
        public static final StaticVariable INNER_SHADOW_WIDTH = new StaticVariable("inner_shadow_width", "0");
        public static final StaticVariable OUTER_SHADOW_WIDTH = new StaticVariable("outer_shadow_width", "0");
        public static final StaticVariable BORDER_COLOR = new StaticVariable("border_color", "0,0,0,0");
        public static final StaticVariable INNER_SHADOW_COLOR = new StaticVariable("inner_shadow_color", "0,0,0");
        public static final StaticVariable OUTER_SHADOW_COLOR = new StaticVariable("outer_shadow_color", "0,0,0");
        public static final StaticVariable BACKGROUND_TYPE = new StaticVariable("background_type", "Color");
        public static final StaticVariable GRADIENT_TYPE = new StaticVariable("gradient_type", "linear");
        public static final StaticVariable GRADIENT_FROM = new StaticVariable("gradient_from", "0.0,0.0");
        public static final StaticVariable GRADIENT_TO = new StaticVariable("gradient_to", "0.0,1.0");
        public static final StaticVariable BACKGROUND_COLOR = new StaticVariable("background_color", "255,255,255");

        public static final StaticVariable BUTTON_SHOW_ICON = new StaticVariable("button_show_icon", "false");
        public static final StaticVariable BUTTON_SHOW_TEXT = new StaticVariable("button_show_text", "false");

        private static final StaticVariable[] KIT_BASE = new StaticVariable[]{ID, EXTENDS, DECORATIONS, OVERWRITE_DECORATIONS};
        public static final StaticVariable[] KIT_SHAPE = new StaticVariable[]{ROUND_FULL, ROUND_TYPE, ROUND_LB, ROUND_LT, ROUND_RB, ROUND_RT, SHAPE_ENABLED};
        public static final StaticVariable[] KIT_INNER_SHADOW = new StaticVariable[]{INNER_SHADOW_COLOR, INNER_SHADOW_WIDTH};
        public static final StaticVariable[] KIT_OUTER_SHADOW = new StaticVariable[]{OUTER_SHADOW_COLOR, OUTER_SHADOW_WIDTH};
        public static final StaticVariable[] KIT_BORDER = new StaticVariable[]{BORDER_COLOR};
        public static final StaticVariable[] KIT_BACKGROUND = new StaticVariable[]{BACKGROUND_TYPE, GRADIENT_TYPE, GRADIENT_FROM, GRADIENT_TO, BACKGROUND_COLOR};
        public static final StaticVariable[] KIT_BUTTON_CONTENT = new StaticVariable[]{BUTTON_SHOW_ICON, BUTTON_SHOW_TEXT};

    }

    protected void initParameters() {
        addStaticParameter(Parameters.ID, new TextParameter("Id"));
        addStaticParameter(Parameters.EXTENDS, new TextParameter("Extends"));
        addStaticParameter(Parameters.DECORATIONS, new BooleanParameter("Decorations"));
        addStaticParameter(Parameters.OVERWRITE_DECORATIONS, new BooleanParameter("Overwrite base"));

        // Round
        addStaticParameter(Parameters.SHAPE_ENABLED, new BooleanParameter("Enable", "Shape"){{
            addActionListener(() -> {
                boolean visible = getValue().equals("true");
                getStaticParameter(Parameters.ROUND_TYPE).setVisible(visible);
                getStaticParameter(Parameters.ROUND_TYPE).action();
            });
        }});

        addStaticParameter(Parameters.ROUND_TYPE, new ComboParameter("Round type", "Shape", new String[]{"Full", "Custom"}){{
            addActionListener(() -> {
                boolean full = getValue().equals("Full");
                boolean custom = !full;
                if(!isVisible()){
                    full = false;
                    custom = false;
                }
                getStaticParameter(Parameters.ROUND_LT).setVisible(custom);
                getStaticParameter(Parameters.ROUND_LB).setVisible(custom);
                getStaticParameter(Parameters.ROUND_RB).setVisible(custom);
                getStaticParameter(Parameters.ROUND_RT).setVisible(custom);
                getStaticParameter(Parameters.ROUND_FULL).setVisible(full);
            });
        }});
        addStaticParameter(Parameters.ROUND_FULL, new IntegerParameter("Round", "Shape"));
        addStaticParameter(Parameters.ROUND_LB, new IntegerParameter(" Corner", "Shape"){{
            setIcon(new ImageIcon("bin/round_lb.png"));
        }});
        addStaticParameter(Parameters.ROUND_LT, new IntegerParameter(" Corner", "Shape"){{
            setIcon(new ImageIcon("bin/round_lt.png"));
        }});
        addStaticParameter(Parameters.ROUND_RB, new IntegerParameter(" Corner", "Shape"){{
            setIcon(new ImageIcon("bin/round_rb.png"));
        }});
        addStaticParameter(Parameters.ROUND_RT, new IntegerParameter(" Corner", "Shape"){{
            setIcon(new ImageIcon("bin/round_rt.png"));
        }});

        // Background
        addStaticParameter(Parameters.BACKGROUND_TYPE, new ComboParameter("Background", "Background", new String[]{"Gradient", "Color"}){{
            addActionListener(() -> {
                boolean visible = getValue().equals("Gradient");
                getStaticParameter(Parameters.GRADIENT_TYPE).setVisible(visible);
                getStaticParameter(Parameters.GRADIENT_TO).setVisible(visible);
                getStaticParameter(Parameters.GRADIENT_FROM).setVisible(visible);

                getStaticParameter(Parameters.BACKGROUND_COLOR).setVisible(!visible);
            });
        }});

        addStaticParameter(Parameters.GRADIENT_TYPE, new ComboParameter("Gradient type", "Background", new String[]{"linear", "radial"}));
        addStaticParameter(Parameters.GRADIENT_FROM, new Point2DParameter("Gradient from", "Background"));
        addStaticParameter(Parameters.GRADIENT_TO, new Point2DParameter("Gradient to", "Background"));
        addStaticParameter(Parameters.BACKGROUND_COLOR, new ColorParameter("Color", "Background"));

        // Border
        addStaticParameter(Parameters.BORDER_COLOR, new ColorParameter("Color", "Border"));

        // Shadow inner
        addStaticParameter(Parameters.INNER_SHADOW_WIDTH, new IntegerParameter("Width", "Inner shadow"));
        addStaticParameter(Parameters.INNER_SHADOW_COLOR, new ColorParameter("Color", "Inner shadow"));

        // Shadow outer
        addStaticParameter(Parameters.OUTER_SHADOW_WIDTH, new IntegerParameter("Width", "Outer shadow"));
        addStaticParameter(Parameters.OUTER_SHADOW_COLOR, new ColorParameter("Color", "Outer shadow"));


        // Button content
        addStaticParameter(Parameters.BUTTON_SHOW_TEXT, new BooleanParameter("Show text", "Content"));
        addStaticParameter(Parameters.BUTTON_SHOW_ICON, new BooleanParameter("Show icon", "Content"));
    }

    public static ImageIcon getExampleIcon(){
        return example_icon;
    }

    // Object

    private String type;
    private String title;
    private Project project;

    private ArrayList<StyleComponent> child_components = new ArrayList<>();

    public StyleComponent(String title, String type){
        super(StyleComponent.class);
        this.title = title;
        this.type = type;

        addImplementedParameters(Parameters.KIT_BASE);
    }

    public StyleComponent clone(Project project){
        try{
            StyleComponent component = (StyleComponent) super.clone();
            component.project = project;

            return component;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public String getTitle(){
        return title;
    }

    public Project getProject(){
        return project;
    }

    public XMLHead getXMLStyle(){
        return getXMLStyle(false);
    }
    public XMLHead getXMLStyle(boolean preview){
        XMLHead head = new XMLHead("style");

        if(preview)
            head.addParameter("id", "preview");
        else
            applyParameterOnCustom(head, "id", Parameters.ID);
        head.addParameter("type", type);
        applyParameterOnCustom(head, "extends", Parameters.EXTENDS);
        applyParameterOnCustom(head, "painter.decorations.decoration", "visible", Parameters.DECORATIONS);
        applyParameterOnCustom(head, "painter.decorations", "overwrite", Parameters.OVERWRITE_DECORATIONS);

        if(isImplemented(Parameters.SHAPE_ENABLED)) {
            if (getVariableValue(Parameters.SHAPE_ENABLED).equals("true")) {
                applyHeadOnCustom(head, "painter.decorations.decoration.WebShape", Parameters.SHAPE_ENABLED);

                if(getVariableValue(Parameters.ROUND_TYPE).equals("Full")){
                    applyParameterOnCustom(head, "painter.decorations.decoration.WebShape", "round", Parameters.ROUND_FULL);
                }else{
                    String round = "";
                    round += getVariableValue(Parameters.ROUND_LT) + ",";
                    round += getVariableValue(Parameters.ROUND_RT) + ",";
                    round += getVariableValue(Parameters.ROUND_RB) + ",";
                    round += getVariableValue(Parameters.ROUND_LB);
                    head.setParameterByPath("painter.decorations.decoration.WebShape", "round", round);
                }
            }
        }
        applyParameterOnCustom(head, "painter.decorations.decoration.LineBorder", "color", Parameters.BORDER_COLOR);

        if(!areVariablesDefault(Parameters.INNER_SHADOW_WIDTH, Parameters.INNER_SHADOW_COLOR)) {
            head.setHeadByPath("painter.decorations.decoration", new XMLHead("WebShadow") {{
                addParameter("type", "inner");

                if(!isVariableDefault(Parameters.INNER_SHADOW_WIDTH))
                    addParameter("width", getVariableValue(Parameters.INNER_SHADOW_WIDTH));

                if(!isVariableDefault(Parameters.INNER_SHADOW_COLOR))
                    addParameter("color", getVariableValue(Parameters.INNER_SHADOW_COLOR));
            }});
        }

        if(!areVariablesDefault(Parameters.OUTER_SHADOW_WIDTH, Parameters.OUTER_SHADOW_COLOR)) {
            head.setHeadByPath("painter.decorations.decoration", new XMLHead("WebShadow") {{
                addParameter("type", "outer");

                if(!isVariableDefault(Parameters.OUTER_SHADOW_WIDTH))
                    addParameter("width", getVariableValue(Parameters.OUTER_SHADOW_WIDTH));

                if(!isVariableDefault(Parameters.OUTER_SHADOW_COLOR))
                    addParameter("color", getVariableValue(Parameters.OUTER_SHADOW_COLOR));
            }});
        }

        if(isImplemented(Parameters.BACKGROUND_TYPE)) {
            if (getVariableValue(Parameters.BACKGROUND_TYPE).equals("Gradient")){
                applyParameterOnCustom(head, "painter.decorations.decoration.GradientBackground", "type", Parameters.GRADIENT_TYPE);
                applyParameterOnCustom(head, "painter.decorations.decoration.GradientBackground", "from", Parameters.GRADIENT_FROM);
                applyParameterOnCustom(head, "painter.decorations.decoration.GradientBackground", "to", Parameters.GRADIENT_TO);
            }else{
                head.setParameterByPath("painter.decorations.decoration.ColorBackground", "color", getVariableValue(Parameters.BACKGROUND_COLOR));
            }
        }

        if(!isVariableDefault(Parameters.BUTTON_SHOW_ICON) && getVariableValue(Parameters.BUTTON_SHOW_ICON).equals("true"))
            head.setParameterByPath("painter.decorations.decoration.ButtonLayout.ButtonIcon", "constraints", "icon");
        if(!isVariableDefault(Parameters.BUTTON_SHOW_TEXT) && getVariableValue(Parameters.BUTTON_SHOW_TEXT).equals("true"))
            head.setParameterByPath("painter.decorations.decoration.ButtonLayout.ButtonText", "constraints", "text");

        return head;
    }

    public abstract Component createPreviewComponent();

    public void doEvent(ComponentEvent.Type event, Object... objects){
        Components.doEvent(event, this, objects);
    }

    public void addChildComponent(StyleComponent component){
        child_components.add(component);
        doEvent(New_Child, component);
    }
    public ArrayList<StyleComponent> getChildComponents(){
        return child_components;
    }
    public void removeChild(StyleComponent component){
        removeChild(getChildComponents().indexOf(component));
    }
    public void removeChild(int index){
        child_components.remove(index);
        doEvent(Removed_Child);
    }
    public void moveChildComponent(int from, int to){
        StyleComponent component = child_components.get(from);
        child_components.remove(component);
        child_components.add(to, component);
    }

    public boolean areVariablesDefault(StaticVariable... variables){
        for(StaticVariable variable : variables)
            if (isImplemented(variable) && !isVariableDefault(variable))
                return false;
        return true;
    }

    public void applyParameterOnCustom(XMLHead head, String path, String parameter, StaticVariable variable){
        if(!isImplemented(variable))
            return;
        if(!isVariableDefault(variable))
            head.setParameterByPath(path, new XMLParameter(parameter, getVariableValue(variable)));
    }
    public void applyParameterOnCustom(XMLHead head, String parameter, StaticVariable variable){
        if(!isImplemented(variable))
            return;
        if(!isVariableDefault(variable))
            head.addParameter(parameter, getVariableValue(variable));
    }
    public void applyHeadOnCustom(XMLHead head, String path, StaticVariable variable){
        if(!isImplemented(variable))
            return;
        if(!isVariableDefault(variable))
            head.setHeadByPath(path);
    }

    public boolean isVariableDefault(StaticVariable variable){
        if(!isImplemented(variable))
            return true;
        if(getVariableValue(Parameters.OVERWRITE_DECORATIONS).equals("true"))
            return variable.getDefaultValue().equals(getVariable(variable).getDefaultValue());

        return getVariable(variable.getName()).isDefaultValue();
    }

    protected void onVariableChanged(String variable){
        Components.doEvent(new ComponentEvent(Style_Changed, this, variable));
    }


}
