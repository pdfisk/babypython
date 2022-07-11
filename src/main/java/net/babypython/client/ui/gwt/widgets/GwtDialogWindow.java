/**
 * MIT License
 *
 * Copyright (c) 2022 Peter Fisk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.babypython.client.ui.gwt.widgets;

import com.google.gwt.user.client.ui.*;
import net.babypython.client.ui.constants.SpriteConstants;
import net.babypython.client.ui.constants.StyleConstants;
import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.ui.viewport.widgets.desktop.WindowManager;

import java.util.ArrayList;

public class GwtDialogWindow extends DialogBox {

    public GwtDialogWindow(String text, UIObject anchor, boolean isModal) {
        super(true, isModal);
        setAnimationEnabled(true);
        setGlassEnabled(isModal);
        HTML content = new HTML(text);
        content.addStyleName(SpriteConstants.BalloonStyle);
        setWidget(content);
        getElement().getStyle().setZIndex(StyleConstants.MaxZIndex);
        setActiveWindowsZIndexToZero ();
        show();
        int height = content.getElement().getClientHeight();
        if (anchor != null) {
            int left = anchor.getAbsoluteLeft();
            int top = anchor.getAbsoluteTop();
            setPopupPosition(left, top - height - SpriteConstants.BalloonOffset);
        } else
            center();
    }

    public void setActiveWindowsZIndexToZero () {
        hiddenWindows = WindowManager.getInstance().getActiveWindows();
        for (GwtWindow gwtWindow : hiddenWindows)
            gwtWindow.setZIndexStyle(0);
    }

    @Override
    public void hide() {
        super.hide();
        for (GwtWindow gwtWindow : hiddenWindows)
            gwtWindow.setZIndex();
    }

    ArrayList<GwtWindow> hiddenWindows = new ArrayList<>();
}
