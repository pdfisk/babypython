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
import net.babypython.client.vm.containers.lists.StringList;
import net.babypython.client.ui.gwt.widgets.GwtListPanel;
import net.babypython.client.ui.interfaces.IListSelectionHandler;
import net.babypython.client.vm.interfaces.IPerform;
import net.babypython.client.vm.core.VClass;
import net.babypython.client.vm.core.VObject;
import net.babypython.client.vm.util.ArgsUtil;
import net.babypython.client.vm.vm.context.Context;

public class ListViewObject extends VObject implements IPerform {

    public ListViewObject(VClass vClass) {
        super(vClass);
        selectedIndex = -1;
        items = new StringList();
        createListPanel();
    }

    void addArg(Object[] args) {
        items.add(ArgsUtil.getStringArg(args));
        gwtListPanel.setItems(items);
    }

    protected void createListPanel() {
        gwtListPanel = new GwtListPanel(null);
        gwtListPanel.setSelectionHandler(new IListSelectionHandler() {
            @Override
            public void onListSelection(int index) {
                onSelection(index);
            }
        });
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    @Override
    public Widget getWidget() {
        return gwtListPanel;
    }

    @Override
    public boolean isWidget() {
        return true;
    }

    protected void onSelection(int index) {
        selectedIndex = index;
    }

    @Override
    public Object perform(Context frame, Object receiverObject, String selector, Object[] args) {
        switch (selector) {
            case PythonMethodConstants.MessageAddArg:
                addArg(args);
                break;
            case PythonMethodConstants.MessageSelectedIndex:
                return getSelectedIndex();
            case PythonMethodConstants.MessageSelectionHandlerArg:
                setSelectionHandler(args);
                break;
        }
        return this;
    }

    void setSelectionHandler(Object[] args) {
        setSelectionHandler(ArgsUtil.getBlockArg(args));
    }

    void setSelectionHandler(BlockObject blockObject) {
        if (blockObject == null || gwtListPanel == null)
            return;
        selectionHandlerBlock = blockObject;
    }

    protected StringList items;
    protected GwtListPanel gwtListPanel;
    protected int selectedIndex;
    protected BlockObject selectionHandlerBlock;
}
