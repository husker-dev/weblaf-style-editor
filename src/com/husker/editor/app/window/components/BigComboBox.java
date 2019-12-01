package com.husker.editor.app.window.components;

import com.alee.laf.combobox.WebComboBox;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class BigComboBox extends WebComboBox {


    // --------------------------------------------------------------
    // ---
    // --- Hack to get control of combobox popup size
    // ---
    // --------------------------------------------------------------
    /**
     * Gets the width the combo's popup should use.
     *
     * Can be overwritten to return any width desired.
     */
    public int getPopupWidth() {
        final Dimension preferred = getPreferredSize();
        return Math.max(getWidth(), preferred.width);
    }

    @SuppressWarnings("deprecation")
    @Override
    public Dimension size() {
        return getSize((Dimension) null);
    }

    @Override
    public Dimension getSize() {
        return getSize((Dimension) null);
    }

    @Override
    public Dimension getSize(final Dimension dimension) {
        // If the method was called from the ComboPopup,
        // simply lie about the current size of the combo box.
        final int width = isCalledFromComboPopup() ? getPopupWidth() : getWidth();
        if (dimension == null) {
            return new Dimension(width, getHeight());
        }
        dimension.width = width;
        dimension.height = getHeight();
        return dimension;
    }

    /**
     * Hack method to determine if called from within the combo popup UI.
     */
    public boolean isCalledFromComboPopup() {
        try {
            final Throwable t = new Throwable();
            t.fillInStackTrace();
            StackTraceElement[] st = t.getStackTrace();
            // look only at top 5 elements of call stack
            int max = Math.min(st.length, 5);
            for (int i=0; i<max; ++i) {
                final String name = st[i].getClassName();
                if (name != null && name.contains("ComboPopup")) {
                    return true;
                }
            }
        } catch (final Exception e) {
            // if there was a problem, assume not called from combo popup
        }
        return false;
    }


}
