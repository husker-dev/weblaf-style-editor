package com.husker.editor.content;

import com.alee.managers.style.Styleable;
import com.husker.editor.content.components.Styled_Button;
import com.husker.editor.content.components.Styled_Label;
import com.husker.editor.content.folders.StyleComponentFolder;
import com.husker.editor.content.parameters.*;
import com.husker.editor.core.*;
import com.husker.editor.core.tools.Resources;
import com.husker.editor.core.xml.XMLHead;

import javax.swing.*;
import java.awt.*;
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

        // Margin
        public static final StaticVariable MARGIN_TYPE = new StaticVariable("margin_type", "Full");
        public static final StaticVariable MARGIN_FULL = new StaticVariable("margin_full", "0");
        public static final StaticVariable MARGIN_LEFT = new StaticVariable("margin_left", "0");
        public static final StaticVariable MARGIN_RIGHT = new StaticVariable("margin_right", "0");
        public static final StaticVariable MARGIN_TOP = new StaticVariable("margin_top", "0");
        public static final StaticVariable MARGIN_BOTTOM = new StaticVariable("margin_bottom", "0");

        // Padding
        public static final StaticVariable PADDING_TYPE = new StaticVariable("padding_type", "Full");
        public static final StaticVariable PADDING_FULL = new StaticVariable("padding_full", "0");
        public static final StaticVariable PADDING_LEFT = new StaticVariable("padding_left", "0");
        public static final StaticVariable PADDING_RIGHT = new StaticVariable("padding_right", "0");
        public static final StaticVariable PADDING_TOP = new StaticVariable("padding_top", "0");
        public static final StaticVariable PADDING_BOTTOM = new StaticVariable("padding_bottom", "0");

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
        public static final StaticVariable GRADIENT_COLOR_1 = new StaticVariable("gradient_color_1", "0,0,0,0");
        public static final StaticVariable GRADIENT_COLOR_2 = new StaticVariable("gradient_color_2", "0,0,0,0");
        public static final StaticVariable BACKGROUND_COLOR = new StaticVariable("background_color", "0,0,0,0");

        public static final StaticVariable BUTTON_SHOW_ICON = new StaticVariable("button_show_icon", "false");
        public static final StaticVariable BUTTON_SHOW_TEXT = new StaticVariable("button_show_text", "false");

        private static final StaticVariable[] KIT_BASE = new StaticVariable[]{ID, EXTENDS, DECORATIONS, OVERWRITE_DECORATIONS};
        public static final StaticVariable[] KIT_SHAPE = new StaticVariable[]{ROUND_FULL, ROUND_TYPE, ROUND_LB, ROUND_LT, ROUND_RB, ROUND_RT, SHAPE_ENABLED};
        public static final StaticVariable[] KIT_MARGIN = new StaticVariable[]{MARGIN_TYPE, MARGIN_FULL, MARGIN_LEFT, MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM};
        public static final StaticVariable[] KIT_PADDING = new StaticVariable[]{PADDING_TYPE, PADDING_FULL, PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM};
        public static final StaticVariable[] KIT_INNER_SHADOW = new StaticVariable[]{INNER_SHADOW_COLOR, INNER_SHADOW_WIDTH};
        public static final StaticVariable[] KIT_OUTER_SHADOW = new StaticVariable[]{OUTER_SHADOW_COLOR, OUTER_SHADOW_WIDTH};
        public static final StaticVariable[] KIT_BORDER = new StaticVariable[]{BORDER_COLOR};
        public static final StaticVariable[] KIT_BACKGROUND = new StaticVariable[]{BACKGROUND_ENABLED, BACKGROUND_TYPE, GRADIENT_TYPE, GRADIENT_FROM, GRADIENT_TO, GRADIENT_COLOR_1, GRADIENT_COLOR_2, BACKGROUND_COLOR};
        public static final StaticVariable[] KIT_BUTTON_CONTENT = new StaticVariable[]{BUTTON_SHOW_ICON, BUTTON_SHOW_TEXT};
    }

    // Object

    private String type;

    protected StyleComponent(Project project, FolderElement folderElement, String title, String type){
        super(StyleComponent.class, project, title, folderElement);
        setPreview(new StylePreview(this));
        this.type = type;

        addImplementedParameters(Variables.KIT_BASE);
    }

    protected void initStaticParameters() {
        addStaticParameter(Variables.ID, new TextParameter("Id"));
        addStaticParameter(Variables.EXTENDS, new TextParameter("Extends"));
        addStaticParameter(Variables.DECORATIONS, new BooleanParameter("Decorations"));
        addStaticParameter(Variables.OVERWRITE_DECORATIONS, new BooleanParameter("Overwrite base"));

        // Margin
        addStaticParameter(Variables.MARGIN_TYPE, new ComboParameter("Type", "Margin", new String[]{"Full", "Custom"}){
            {
                onValueChanged(value -> updateVisible());
                onVisibleChanged(visible -> updateVisible());
                onApplying(object -> updateVisible());
            }
            void updateVisible(){
                boolean full = isVisible() && getValue().equals("Full");
                boolean custom = isVisible() && !full;

                getStaticParameter(Variables.MARGIN_LEFT).setVisible(custom);
                getStaticParameter(Variables.MARGIN_TOP).setVisible(custom);
                getStaticParameter(Variables.MARGIN_RIGHT).setVisible(custom);
                getStaticParameter(Variables.MARGIN_BOTTOM).setVisible(custom);
                getStaticParameter(Variables.MARGIN_FULL).setVisible(full);
            }
        });
        addStaticParameter(Variables.MARGIN_FULL, new IntegerParameter("Pixels", "Margin"));
        addStaticParameter(Variables.MARGIN_LEFT, new IntegerParameter("Left", "Margin"));
        addStaticParameter(Variables.MARGIN_TOP, new IntegerParameter("Top", "Margin"));
        addStaticParameter(Variables.MARGIN_RIGHT, new IntegerParameter("Right", "Margin"));
        addStaticParameter(Variables.MARGIN_BOTTOM, new IntegerParameter("Bottom", "Margin"));

        // Padding
        addStaticParameter(Variables.PADDING_TYPE, new ComboParameter("Type", "Padding", new String[]{"Full", "Custom"}){
            {
                onValueChanged(value -> updateVisible());
                onVisibleChanged(visible -> updateVisible());
                onApplying(object -> updateVisible());
            }
            void updateVisible(){
                boolean full = isVisible() && getValue().equals("Full");
                boolean custom = isVisible() && !full;

                getStaticParameter(Variables.PADDING_LEFT).setVisible(custom);
                getStaticParameter(Variables.PADDING_TOP).setVisible(custom);
                getStaticParameter(Variables.PADDING_RIGHT).setVisible(custom);
                getStaticParameter(Variables.PADDING_BOTTOM).setVisible(custom);
                getStaticParameter(Variables.PADDING_FULL).setVisible(full);
            }
        });
        addStaticParameter(Variables.PADDING_FULL, new IntegerParameter("Pixels", "Padding"));
        addStaticParameter(Variables.PADDING_LEFT, new IntegerParameter("Left", "Padding"));
        addStaticParameter(Variables.PADDING_TOP, new IntegerParameter("Top", "Padding"));
        addStaticParameter(Variables.PADDING_RIGHT, new IntegerParameter("Right", "Padding"));
        addStaticParameter(Variables.PADDING_BOTTOM, new IntegerParameter("Bottom", "Padding"));

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

    public void applyCode(String text) throws ErrorException {
        XMLHead head = null;
        try {
            head = XMLHead.fromString(text);
        }catch (Exception ex){}

        if (head == null)
            throw new ErrorException("XML reading error", "If you are sure that everything is correct, please contact the developer.");
    }

    public String getCode(boolean preview){
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

        // Margin
        if(getVariableValue(Variables.MARGIN_TYPE).equals("Full")){
            if(isVariableCustom(Variables.MARGIN_FULL))
                head.addParameter("margin", getVariableValue(Variables.MARGIN_FULL));
        }else{
            String value = "";
            value += getVariableValue(Variables.MARGIN_LEFT) + ",";
            value += getVariableValue(Variables.MARGIN_TOP) + ",";
            value += getVariableValue(Variables.MARGIN_RIGHT) + ",";
            value += getVariableValue(Variables.MARGIN_BOTTOM);
            if(areVariablesCustom(Variables.MARGIN_LEFT, Variables.MARGIN_TOP, Variables.MARGIN_RIGHT, Variables.MARGIN_BOTTOM))
                head.addParameter("margin", value);
        }

        // Padding
        if(getVariableValue(Variables.PADDING_TYPE).equals("Full")){
            if(isVariableCustom(Variables.PADDING_FULL))
                head.addParameter("padding", getVariableValue(Variables.PADDING_FULL));
        }else{
            String value = "";
            value += getVariableValue(Variables.PADDING_LEFT) + ",";
            value += getVariableValue(Variables.PADDING_TOP) + ",";
            value += getVariableValue(Variables.PADDING_RIGHT) + ",";
            value += getVariableValue(Variables.PADDING_BOTTOM);
            if(areVariablesCustom(Variables.PADDING_LEFT, Variables.PADDING_TOP, Variables.PADDING_RIGHT, Variables.PADDING_BOTTOM))
                head.addParameter("padding", value);
        }

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

        return head.toString();
    }

    public abstract JComponent createPreviewComponent();

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
