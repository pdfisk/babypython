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
import net.babypython.client.ui.gwt.widgets.GwtTextPanel;
import net.babypython.client.vm.interfaces.IPerform;
import net.babypython.client.vm.core.VClass;
import net.babypython.client.vm.core.VObject;
import net.babypython.client.vm.vm.context.Context;
import net.babypython.client.vm.vm.runtime.operations.StringOps;

public class TextPanelObject extends VObject implements IPerform {

    public TextPanelObject(VClass vClass) {
        super(vClass);
        createTextPanel();
    }

    protected void createTextPanel() {
        gwtTextPanel = new GwtTextPanel();
    }

    String getText() {
        return gwtTextPanel.getValue();
    }

    @Override
    public Widget getWidget() {
        return gwtTextPanel;
    }

    @Override
    public boolean isWidget() {
        return true;
    }

    @Override
    public Object perform(Context frame, Object receiverObject, String selector, Object[] args) {
        switch (selector) {
            case PythonMethodConstants.MessageText:
                return getText();
            case PythonMethodConstants.MessageTextArg:
                setText(args);
                break;
        }
        return this;
    }

    void setText(Object[] args) {
        gwtTextPanel.setValue(StringOps.coerce(args[0]));
    }

    protected GwtTextPanel gwtTextPanel;
}
