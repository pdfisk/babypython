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
package net.babypython.client.vm.classes.special.objects;

import com.google.gwt.user.client.ui.Widget;
import net.babypython.client.vm.constants.PythonMethodConstants;
import net.babypython.client.ui.gwt.window.widgets.GwtWindowButton;
import net.babypython.client.vm.interfaces.IPerform;
import net.babypython.client.vm.core.VClass;
import net.babypython.client.vm.core.VObject;
import net.babypython.client.vm.util.ArgsUtil;
import net.babypython.client.vm.vm.context.CompiledMethod;
import net.babypython.client.vm.vm.context.Context;

public class WindowButtonObject extends VObject implements IPerform {

    public WindowButtonObject(VClass windowButtonClass) {
        super(windowButtonClass);
    }

    BlockObject getClickHandler() {
        if (gwtWindowButton == null)
            return null;
        return gwtWindowButton.getClickHandler();
    }

    @Override
    public Widget getWidget() {
        return gwtWindowButton;
    }

    @Override
    public boolean isWidget() {
        return true;
    }

    public void onClicked() {
        BlockObject clickHandler = getClickHandler();
        if (clickHandler != null)
            clickHandler.fork();
    }

    BlockObject onGetClickHandler() {
        return getClickHandler();
    }

    public String onGetText() {
        if (gwtWindowButton == null)
            return "";
        return gwtWindowButton.getText();
    }

    void onSetClickHandler(Object[] args) {
        BlockObject clickHandler = ArgsUtil.getBlockArg(args);
        setClickHandler(clickHandler);
    }

    void onSetText(Object[] args) {
        setText(ArgsUtil.getStringArg(args));
    }

    @Override
    public Object perform(Context context, Object receiverObject, String selector, Object[] args) {
        switch (selector) {
            case PythonMethodConstants.MessageClickHandler:
                return onGetClickHandler();
            case PythonMethodConstants.MessageClickHandlerArg:
                onSetClickHandler(args);
                break;
            case PythonMethodConstants.MessageText:
                return onGetText();
            case PythonMethodConstants.MessageTextArg:
                onSetText(args);
                break;
        }
        return this;
    }

    void setClickHandler(BlockObject clickHandler) {
        if (clickHandler == null || gwtWindowButton == null)
            return;
        gwtWindowButton.setClickHandler(clickHandler);
    }

    public void setGwtWindowButton(GwtWindowButton gwtWindowButton) {
        this.gwtWindowButton = gwtWindowButton;
        this.gwtWindowButton.setWindowButtonObject(this);
    }

    public void setText(String text) {
        if (gwtWindowButton != null)
            gwtWindowButton.setText(text);
    }

    public String toString() {
        return "Button(" + onGetText() + ")";
    }

    protected GwtWindowButton gwtWindowButton;
    protected CompiledMethod sendSelectorMethod;
}
