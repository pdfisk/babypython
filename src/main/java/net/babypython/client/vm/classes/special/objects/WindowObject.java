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
import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.vm.interfaces.IPerform;
import net.babypython.client.vm.core.VClass;
import net.babypython.client.vm.core.VObject;
import net.babypython.client.vm.util.ArgsUtil;
import net.babypython.client.vm.vm.context.Context;
import net.babypython.client.vm.vm.runtime.operations.StringOps;

public class WindowObject extends VObject implements IPerform {

    public WindowObject(VClass vClass) {
        super(vClass);
        createWindow();
        gwtWindow.windowObject = this;
    }

    protected void createWindow() {
        gwtWindow = new GwtWindow();
        gwtWindow.show();
    }

    @Override
    public Object perform(Context context, Object receiverObject, String selector, Object[] args) {
        switch (selector) {
            case PythonMethodConstants.MessageAddButtonArg:
                return addButton(args);
            case PythonMethodConstants.MessageContent:
                return getContentObject();
            case PythonMethodConstants.MessageContentArg:
                setContentObject(args);
                break;
            case PythonMethodConstants.MessageHeightArg:
                height(args);
                break;
            case PythonMethodConstants.MessageHide:
                hide();
                break;
            case PythonMethodConstants.MessageShow:
                show();
                break;
            case PythonMethodConstants.MessageTitleArg:
                title(args);
                break;
            case PythonMethodConstants.MessageWidthArg:
                width(args);
                break;
        }
        return this;
    }

    WindowButtonObject addButton(Object[] args) {
        String label = StringOps.coerce(args[0]);
        return gwtWindow.addButton(label);
    }

    VObject getContentObject() {
        return contentObject;
    }

    void height(Object[] args) {
        gwtWindow.setHeight(ArgsUtil.getIntArg(args));
    }

    void hide() {
        gwtWindow.hide();
    }

    void setContentObject(Object[] args) {
        if (args.length < 1)
            return;
        Object contentArg = args[0];
        if (!(contentArg instanceof VObject))
            return;
         contentObject = (VObject) contentArg;
        if (!contentObject.isWidget())
            return;
        Widget widget = contentObject.getWidget();
        gwtWindow.setContent(widget);
    }

    void show() {
        gwtWindow.show();
    }

    void title(Object[] args) {
        gwtWindow.setTitle(ArgsUtil.getStringArg(args));
    }

    void width(Object[] args) {
        gwtWindow.setWidth(ArgsUtil.getIntArg(args));
    }

    VObject contentObject;
    protected GwtWindow gwtWindow;
}
