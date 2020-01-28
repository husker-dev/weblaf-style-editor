package com.husker.editor.content.style;

import com.husker.editor.content.style.components.StyledButton;
import com.husker.editor.content.style.components.StyledLabel;
import com.husker.editor.content.style.folders.StyleComponentFolder;
import com.husker.editor.content.style.parameters.DecorationsEnabledParameter;
import com.husker.editor.content.style.parameters.ExtendsParameter;
import com.husker.editor.content.style.parameters.IdParameter;
import com.husker.editor.content.style.parameters.OverwriteDecorationsParameter;
import com.husker.editor.content.style.parameters.button.ShowButtonIconParameter;
import com.husker.editor.content.style.parameters.button.ShowButtonTextParameter;
import com.husker.editor.content.style.parameters.margin.*;
import com.husker.editor.content.style.parameters.padding.*;
import com.husker.editor.content.style.parameters.shape.*;
import com.husker.editor.content.style.parameters.shape.background.BackgroundEnabledParameter;
import com.husker.editor.content.style.parameters.shape.background.BackgroundTypeParameter;
import com.husker.editor.content.style.parameters.shape.background.color.BackgroundColorParameter;
import com.husker.editor.content.style.parameters.shape.background.gradient.GradientColor1Parameter;
import com.husker.editor.content.style.parameters.shape.background.gradient.GradientColor2Parameter;
import com.husker.editor.content.style.parameters.shape.background.gradient.GradientFromParameter;
import com.husker.editor.content.style.parameters.shape.background.gradient.GradientTypeParameter;
import com.husker.editor.content.style.parameters.shape.border.BorderColorParameter;
import com.husker.editor.content.style.parameters.shape.round.*;
import com.husker.editor.content.style.parameters.shape.shadow.inner.InnerShadowColor;
import com.husker.editor.content.style.parameters.shape.shadow.inner.InnerShadowEnabled;
import com.husker.editor.content.style.parameters.shape.shadow.inner.InnerShadowWidth;
import com.husker.editor.content.style.parameters.shape.shadow.outer.OuterShadowColor;
import com.husker.editor.content.style.parameters.shape.shadow.outer.OuterShadowWidth;
import com.husker.editor.core.*;
import com.husker.editor.core.xml.XMLHead;

import javax.swing.*;
import java.util.HashMap;

public abstract class StyleComponent extends EditableObject implements Cloneable{

    // Static
    public static HashMap<String, Class<? extends StyleComponent>> components = new HashMap<String, Class<? extends StyleComponent>>(){{
        put("Button", StyledButton.class);
        put("Label", StyledLabel.class);
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
        public static final StaticVariable INNER_SHADOW_ENABLED = new StaticVariable("inner_shadow_enabled", "false");
        public static final StaticVariable INNER_SHADOW_WIDTH = new StaticVariable("inner_shadow_width", "0");
        public static final StaticVariable INNER_SHADOW_COLOR = new StaticVariable("inner_shadow_color", "0,0,0");

        // Outer shadow
        public static final StaticVariable OUTER_SHADOW_ENABLED = new StaticVariable("outer_shadow_enabled", "false");
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

        setCode(new XMLHead("style", new String[]{"type", type}).toString());

        this.addVariableChangedListener(variable -> {
            XMLHead head = StyleParameterUtils.generateHead(getCode());
            head.setParameter("type", type);
        });
    }

    protected void initStaticParameters() {
        addStaticParameter(Variables.ID, new IdParameter());
        addStaticParameter(Variables.EXTENDS, new ExtendsParameter());
        addStaticParameter(Variables.DECORATIONS, new DecorationsEnabledParameter());
        addStaticParameter(Variables.OVERWRITE_DECORATIONS, new OverwriteDecorationsParameter());

        // Margin
        String margin_group = "Margin";
        addStaticParameter(Variables.MARGIN_TYPE, new MarginTypeParameter(margin_group));
        addStaticParameter(Variables.MARGIN_FULL, new FullMarginParameter(margin_group));
        addStaticParameter(Variables.MARGIN_LEFT, new LeftMarginParameter(margin_group));
        addStaticParameter(Variables.MARGIN_TOP, new TopMarginParameter(margin_group));
        addStaticParameter(Variables.MARGIN_RIGHT, new RightMarginParameter(margin_group));
        addStaticParameter(Variables.MARGIN_BOTTOM, new BottomMarginParameter(margin_group));

        // Padding
        String padding_group = "Padding";
        addStaticParameter(Variables.PADDING_TYPE, new PaddingTypeParameter(padding_group));
        addStaticParameter(Variables.PADDING_FULL, new FullPaddingParameter(padding_group));
        addStaticParameter(Variables.PADDING_LEFT, new LeftPaddingParameter(padding_group));
        addStaticParameter(Variables.PADDING_TOP, new TopPaddingParameter(padding_group));
        addStaticParameter(Variables.PADDING_RIGHT, new RightPaddingParameter(padding_group));
        addStaticParameter(Variables.PADDING_BOTTOM, new BottomPaddingParameter(padding_group));

        // Round
        String shape_group = "Shape";
        addStaticParameter(Variables.SHAPE_ENABLED, new ShapeEnabledParameter(shape_group));
        addStaticParameter(Variables.ROUND_TYPE, new RoundTypeParameter(shape_group));
        addStaticParameter(Variables.ROUND_FULL, new FullRoundParameter(shape_group));
        addStaticParameter(Variables.ROUND_LB, new LBRoundParameter(shape_group));
        addStaticParameter(Variables.ROUND_LT, new LTRoundParameter(shape_group));
        addStaticParameter(Variables.ROUND_RB, new RBRoundParameter(shape_group));
        addStaticParameter(Variables.ROUND_RT, new RTRoundParameter(shape_group));

        // Background
        String background_group = "Background";
        addStaticParameter(Variables.BACKGROUND_ENABLED, new BackgroundEnabledParameter(background_group));
        addStaticParameter(Variables.BACKGROUND_TYPE, new BackgroundTypeParameter(background_group));
        addStaticParameter(Variables.GRADIENT_TYPE, new GradientTypeParameter(background_group));
        addStaticParameter(Variables.GRADIENT_FROM, new GradientFromParameter(background_group));
        addStaticParameter(Variables.GRADIENT_TO, new GradientTypeParameter(background_group));
        addStaticParameter(Variables.GRADIENT_COLOR_1, new GradientColor1Parameter(background_group));
        addStaticParameter(Variables.GRADIENT_COLOR_2, new GradientColor2Parameter(background_group));
        addStaticParameter(Variables.BACKGROUND_COLOR, new BackgroundColorParameter(background_group));

        // Border
        String border_group = "Border";
        addStaticParameter(Variables.BORDER_COLOR, new BorderColorParameter(border_group));

        // Shadow inner
        String inner_shadow_group = "Inner shadow";
        addStaticParameter(Variables.INNER_SHADOW_ENABLED, new InnerShadowEnabled(inner_shadow_group));
        addStaticParameter(Variables.INNER_SHADOW_WIDTH, new InnerShadowWidth(inner_shadow_group));
        addStaticParameter(Variables.INNER_SHADOW_COLOR, new InnerShadowColor(inner_shadow_group));

        // Shadow outer
        String outer_shadow_group = "Outer shadow";
        addStaticParameter(Variables.OUTER_SHADOW_ENABLED, new OuterShadowColor(outer_shadow_group));
        addStaticParameter(Variables.OUTER_SHADOW_WIDTH, new OuterShadowWidth(outer_shadow_group));
        addStaticParameter(Variables.OUTER_SHADOW_COLOR, new OuterShadowColor(outer_shadow_group));

        // Button content
        String button_content_group = "Button content";
        addStaticParameter(Variables.BUTTON_SHOW_TEXT, new ShowButtonTextParameter(button_content_group));
        addStaticParameter(Variables.BUTTON_SHOW_ICON, new ShowButtonIconParameter(button_content_group));
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
