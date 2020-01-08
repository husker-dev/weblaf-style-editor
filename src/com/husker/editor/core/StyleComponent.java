package com.husker.editor.core;

import com.husker.editor.content.components.Styled_Button;
import com.husker.editor.content.components.Styled_Label;
import com.husker.editor.content.folders.StyleComponentFolder;
import com.husker.editor.content.parameters.*;
import com.husker.editor.core.tools.Resources;
import com.husker.editor.core.xml.XMLHead;

import java.util.HashMap;
import java.util.function.Predicate;

public abstract class StyleComponent extends EditableObject implements Cloneable{

    // Static
    public static HashMap<String, Class<? extends StyleComponent>> components = new HashMap<String, Class<? extends StyleComponent>>(){{
        put("Button", Styled_Button.class);
        put("Label", Styled_Label.class);
    }};

    public static class Variables {
        public static final StaticVariable ID = new StaticVariable("id");
        public static final StaticVariable EXTENDS = new StaticVariable("extends");
        public static final StaticVariable DECORATIONS = new StaticVariable("decorations", "true");
        public static final StaticVariable OVERWRITE_DECORATIONS = new StaticVariable("overwrite_decorations", "false");

        // Round
        public static final StaticVariable ROUND_TYPE = new StaticVariable("round_type", "Full");
        public static final StaticVariable ROUND_FULL = new StaticVariable("round_full", "0");
        public static final StaticVariable ROUND_LT = new StaticVariable("round_lt", "0");   // Left top
        public static final StaticVariable ROUND_RT = new StaticVariable("round_rt", "0");   // Right top
        public static final StaticVariable ROUND_LB = new StaticVariable("round_lb", "0");   // Left bottom
        public static final StaticVariable ROUND_RB = new StaticVariable("round_rb", "0");   // Right bottom

        // Shape
        public static final StaticVariable SHAPE_ENABLED = new StaticVariable("shape_enabled", "false");

        // Inner shadow
        public static final StaticVariable INNER_SHADOW_WIDTH = new StaticVariable("inner_shadow_width", "0");
        public static final StaticVariable INNER_SHADOW_COLOR = new StaticVariable("inner_shadow_color", "0,0,0");

        // Outer shadow
        public static final StaticVariable OUTER_SHADOW_WIDTH = new StaticVariable("outer_shadow_width", "0");
        public static final StaticVariable OUTER_SHADOW_COLOR = new StaticVariable("outer_shadow_color", "0,0,0");

        // Border
        public static final StaticVariable BORDER_COLOR = new StaticVariable("border_color", "0,0,0,0");

        // Background
        public static final StaticVariable BACKGROUND_TYPE = new StaticVariable("background_type", "Color");
        public static final StaticVariable BACKGROUND_ENABLED = new StaticVariable("background_enabled", "false");
        public static final StaticVariable GRADIENT_TYPE = new StaticVariable("gradient_type", "linear");
        public static final StaticVariable GRADIENT_FROM = new StaticVariable("gradient_from", "0.0,0.0");
        public static final StaticVariable GRADIENT_TO = new StaticVariable("gradient_to", "0.0,1.0");
        public static final StaticVariable GRADIENT_COLOR_1 = new StaticVariable("gradient_color_1", "255,255,255");
        public static final StaticVariable GRADIENT_COLOR_2 = new StaticVariable("gradient_color_2", "223,223,223");
        public static final StaticVariable BACKGROUND_COLOR = new StaticVariable("background_color", "0,0,0,0");

        public static final StaticVariable BUTTON_SHOW_ICON = new StaticVariable("button_show_icon", "false");
        public static final StaticVariable BUTTON_SHOW_TEXT = new StaticVariable("button_show_text", "false");

        private static final StaticVariable[] KIT_BASE = new StaticVariable[]{ID, EXTENDS, DECORATIONS, OVERWRITE_DECORATIONS};
        public static final StaticVariable[] KIT_SHAPE = new StaticVariable[]{ROUND_FULL, ROUND_TYPE, ROUND_LB, ROUND_LT, ROUND_RB, ROUND_RT, SHAPE_ENABLED};
        public static final StaticVariable[] KIT_INNER_SHADOW = new StaticVariable[]{INNER_SHADOW_COLOR, INNER_SHADOW_WIDTH};
        public static final StaticVariable[] KIT_OUTER_SHADOW = new StaticVariable[]{OUTER_SHADOW_COLOR, OUTER_SHADOW_WIDTH};
        public static final StaticVariable[] KIT_BORDER = new StaticVariable[]{BORDER_COLOR};
        public static final StaticVariable[] KIT_BACKGROUND = new StaticVariable[]{BACKGROUND_ENABLED, BACKGROUND_TYPE, GRADIENT_TYPE, GRADIENT_FROM, GRADIENT_TO, GRADIENT_COLOR_1, GRADIENT_COLOR_2, BACKGROUND_COLOR};
        public static final StaticVariable[] KIT_BUTTON_CONTENT = new StaticVariable[]{BUTTON_SHOW_ICON, BUTTON_SHOW_TEXT};
    }

    protected void initParameters() {
        addStaticParameter(Variables.ID, new TextParameter("Id"));
        addStaticParameter(Variables.EXTENDS, new TextParameter("Extends"));
        addStaticParameter(Variables.DECORATIONS, new BooleanParameter("Decorations"));
        addStaticParameter(Variables.OVERWRITE_DECORATIONS, new BooleanParameter("Overwrite base"));

        // Round
        addStaticParameter(Variables.SHAPE_ENABLED, new BooleanParameter("Enable", "Shape"){{
            onValueChanged(value -> getStaticParameter(Variables.ROUND_TYPE).setVisible(value.equals("true")));
        }});

        addStaticParameter(Variables.ROUND_TYPE, new ComboParameter("Round type", "Shape", new String[]{"Full", "Custom"}){
            {
                onValueChanged(value -> updateVisible());
                onVisibleChanged(visible -> updateVisible());
                onApplying(object -> updateVisible());
            }
            void updateVisible(){
                boolean full = isVisible() && getValue().equals("Full");
                boolean custom = isVisible() && !full;

                getStaticParameter(Variables.ROUND_LT).setVisible(custom);
                getStaticParameter(Variables.ROUND_LB).setVisible(custom);
                getStaticParameter(Variables.ROUND_RB).setVisible(custom);
                getStaticParameter(Variables.ROUND_RT).setVisible(custom);
                getStaticParameter(Variables.ROUND_FULL).setVisible(full);
            }
        });
        addStaticParameter(Variables.ROUND_FULL, new IntegerParameter("Round", "Shape"));
        addStaticParameter(Variables.ROUND_LB, new IntegerParameter(" Corner", "Shape"){{
            setIcon(Resources.getImageIcon("round_lb.png"));
        }});
        addStaticParameter(Variables.ROUND_LT, new IntegerParameter(" Corner", "Shape"){{
            setIcon(Resources.getImageIcon("round_lt.png"));
        }});
        addStaticParameter(Variables.ROUND_RB, new IntegerParameter(" Corner", "Shape"){{
            setIcon(Resources.getImageIcon("round_rb.png"));
        }});
        addStaticParameter(Variables.ROUND_RT, new IntegerParameter(" Corner", "Shape"){{
            setIcon(Resources.getImageIcon("round_rt.png"));
        }});

        // Background
        addStaticParameter(Variables.BACKGROUND_ENABLED, new BooleanParameter("Enabled", "Background"){{
            addValueChangedListener(value -> getStaticParameter(Variables.BACKGROUND_TYPE).setVisible(value.equals("true")));
        }});
        addStaticParameter(Variables.BACKGROUND_TYPE, new ComboParameter("Background", "Background", new String[]{"Gradient", "Color"}){
            {
                onValueChanged(value -> updateVisible());
                onVisibleChanged(visible -> updateVisible());
                onApplying(object -> updateVisible());
            }
            void updateVisible(){
                boolean gradient_visible = isVisible() && getValue().equals("Gradient");
                boolean color_visible = isVisible() && !gradient_visible;

                getStaticParameter(Variables.GRADIENT_TYPE).setVisible(gradient_visible);
                getStaticParameter(Variables.GRADIENT_TO).setVisible(gradient_visible);
                getStaticParameter(Variables.GRADIENT_FROM).setVisible(gradient_visible);
                getStaticParameter(Variables.GRADIENT_COLOR_1).setVisible(gradient_visible);
                getStaticParameter(Variables.GRADIENT_COLOR_2).setVisible(gradient_visible);
                getStaticParameter(Variables.BACKGROUND_COLOR).setVisible(color_visible);
            }
        });

        addStaticParameter(Variables.GRADIENT_TYPE, new ComboParameter("Gradient type", "Background", new String[]{"linear", "radial"}));
        addStaticParameter(Variables.GRADIENT_FROM, new Point2DParameter("Gradient from", "Background"));
        addStaticParameter(Variables.GRADIENT_TO, new Point2DParameter("Gradient to", "Background"));
        addStaticParameter(Variables.GRADIENT_COLOR_1, new ColorParameter("Color 1", "Background"));
        addStaticParameter(Variables.GRADIENT_COLOR_2, new ColorParameter("Color 2", "Background"));
        addStaticParameter(Variables.BACKGROUND_COLOR, new ColorParameter("Color", "Background"));

        // Border
        addStaticParameter(Variables.BORDER_COLOR, new ColorParameter("Color", "Border"));

        // Shadow inner
        addStaticParameter(Variables.INNER_SHADOW_WIDTH, new IntegerParameter("Width", "Inner shadow"));
        addStaticParameter(Variables.INNER_SHADOW_COLOR, new ColorParameter("Color", "Inner shadow"));

        // Shadow outer
        addStaticParameter(Variables.OUTER_SHADOW_WIDTH, new IntegerParameter("Width", "Outer shadow"));
        addStaticParameter(Variables.OUTER_SHADOW_COLOR, new ColorParameter("Color", "Outer shadow"));

        // Button content
        addStaticParameter(Variables.BUTTON_SHOW_TEXT, new BooleanParameter("Show text", "Button content"));
        addStaticParameter(Variables.BUTTON_SHOW_ICON, new BooleanParameter("Show icon", "Button content"));
    }

    // Object

    private String type;

    protected StyleComponent(Project project, FolderElement folderElement, String title, String type){
        super(project, folderElement, StyleComponent.class, title);
        this.type = type;

        addImplementedParameters(Variables.KIT_BASE);
    }

    public void applyXML(XMLHead head){
        //System.out.println(head.toString());
        //if(head != null)
        //    System.out.println(head.toString());
    }

    public XMLHead getXMLStyle(){
        return getXMLStyle(false);
    }
    public XMLHead getXMLStyle(boolean preview){
        XMLHead head = new XMLHead("style");

        if(preview)
            head.addParameter("id", "::preview::");
        else
            applyParameterOnCustom(head, "id", Variables.ID);
        head.addParameter("type", type);
        applyParameterOnCustom(head, "extends", Variables.EXTENDS);
        applyParameterOnCustom(head, "painter.decorations.decoration", "visible", Variables.DECORATIONS);
        if(super.isVariableCustom(Variables.OVERWRITE_DECORATIONS))
            head.setParameterByPath("painter.decorations", "overwrite", getVariableValue(Variables.OVERWRITE_DECORATIONS));

        if(isImplemented(Variables.SHAPE_ENABLED) && getVariableValue(Variables.SHAPE_ENABLED).equals("true")) {
            createHeadOnCustom(head, "painter.decorations.decoration.WebShape", Variables.SHAPE_ENABLED);

            if(getVariableValue(Variables.ROUND_TYPE).equals("Full")){
                applyParameterOnCustom(head, "painter.decorations.decoration.WebShape", "round", Variables.ROUND_FULL);
            }else{
                String round = "";
                round += getVariableValue(Variables.ROUND_LT) + ",";
                round += getVariableValue(Variables.ROUND_RT) + ",";
                round += getVariableValue(Variables.ROUND_RB) + ",";
                round += getVariableValue(Variables.ROUND_LB);
                if(areVariablesCustom(Variables.ROUND_LT, Variables.ROUND_RT, Variables.ROUND_RB, Variables.ROUND_LB))
                    head.setParameterByPath("painter.decorations.decoration.WebShape", "round", round);
            }
        }
        applyParameterOnCustom(head, "painter.decorations.decoration.LineBorder", "color", Variables.BORDER_COLOR);

        if(areVariablesCustom(Variables.INNER_SHADOW_WIDTH, Variables.INNER_SHADOW_COLOR)) {
            Predicate<XMLHead> predicate = h -> h.getParameter("type") != null && h.getParameterValue("type").equals("inner");

            head.createHeadByPath("painter.decorations.decoration", new XMLHead("WebShadow", new String[]{"type", "inner"}));
            applyParameterOnCustom(head, "painter.decorations.decoration.WebShadow", "width", Variables.INNER_SHADOW_WIDTH, predicate);
            applyParameterOnCustom(head, "painter.decorations.decoration.WebShadow", "color", Variables.INNER_SHADOW_COLOR, predicate);
        }

        if(areVariablesCustom(Variables.OUTER_SHADOW_WIDTH, Variables.OUTER_SHADOW_COLOR)) {
            Predicate<XMLHead> predicate = h -> h.getParameter("type") != null && h.getParameterValue("type").equals("outer");

            head.createHeadByPath("painter.decorations.decoration", new XMLHead("WebShadow", new String[]{"type", "outer"}));
            applyParameterOnCustom(head, "painter.decorations.decoration.WebShadow", "width", Variables.OUTER_SHADOW_WIDTH, predicate);
            applyParameterOnCustom(head, "painter.decorations.decoration.WebShadow", "color", Variables.OUTER_SHADOW_COLOR, predicate);
        }

        if(isImplemented(Variables.BACKGROUND_ENABLED) && getVariableValue(Variables.BACKGROUND_ENABLED).equals("true")) {
            if (getVariableValue(Variables.BACKGROUND_TYPE).equals("Gradient")){
                createHeadOnCustom(head, "painter.decorations.decoration.GradientBackground", Variables.BACKGROUND_TYPE);
                applyParameterOnCustom(head, "painter.decorations.decoration.GradientBackground", "type", Variables.GRADIENT_TYPE);
                applyParameterOnCustom(head, "painter.decorations.decoration.GradientBackground", "from", Variables.GRADIENT_FROM);
                applyParameterOnCustom(head, "painter.decorations.decoration.GradientBackground", "to", Variables.GRADIENT_TO);
                if(areVariablesCustom(Variables.GRADIENT_COLOR_1, Variables.GRADIENT_COLOR_2)) {
                    head.createHeadByPath("painter.decorations.decoration.GradientBackground", new XMLHead("color", getVariableValue(Variables.GRADIENT_COLOR_1)));
                    head.createHeadByPath("painter.decorations.decoration.GradientBackground", new XMLHead("color", getVariableValue(Variables.GRADIENT_COLOR_2)));
                }
            }else {
                createHeadOnCustom(head, "painter.decorations.decoration.ColorBackground", Variables.BACKGROUND_TYPE);
                applyParameterOnCustom(head, "painter.decorations.decoration.ColorBackground", "color", Variables.BACKGROUND_COLOR);
            }
        }
        if(isImplemented(Variables.BUTTON_SHOW_ICON) && isVariableCustom(Variables.BUTTON_SHOW_ICON))
            head.setParameterByPath("painter.decorations.decoration.ButtonLayout.ButtonIcon", "constraints", getVariableValue(Variables.BUTTON_SHOW_ICON).equals("true") ? "icon" : "");
        if(isImplemented(Variables.BUTTON_SHOW_TEXT) && isVariableCustom(Variables.BUTTON_SHOW_TEXT))
            head.setParameterByPath("painter.decorations.decoration.ButtonLayout.ButtonText", "constraints", getVariableValue(Variables.BUTTON_SHOW_TEXT).equals("true") ? "text" : "");

        return head;
    }

    public boolean isVariableCustom(StaticVariable variable){
        if(!isImplemented(variable))
            return false;
        if(getVariableValue(Variables.OVERWRITE_DECORATIONS).equals("true"))
            return !variable.getDefaultValue().equals(getVariable(variable).getConstantValue());

        return !getVariable(variable.getName()).isDefaultValue();
    }

    public FolderElement createFolder() {
        return new StyleComponentFolder(getProject(), this);
    }
}
