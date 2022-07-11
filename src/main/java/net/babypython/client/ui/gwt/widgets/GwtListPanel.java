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

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;
import net.babypython.client.vm.containers.lists.IntegerList;
import net.babypython.client.vm.containers.lists.StringList;
import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.ui.interfaces.IListSelectionHandler;

public class GwtListPanel extends ListBox {

    public GwtListPanel(GwtWindow window) {
        super();
        this.window = window;
        getElement().getStyle().setOverflow(Style.Overflow.AUTO);
        setHeight("100%");
        setWidth("100%");
        getElement().addClassName("listPanel");
        setVisibleItemCount(defaultVisibleItemCount());
        addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                if (selectionHandler != null)
                    selectionHandler.onListSelection(getSelectedIndex());
            }
        });
    }

    protected int defaultVisibleItemCount() {
        return 100;
    }

    public int[] getSelectedIndices() {
        IntegerList selections = new IntegerList();
        int i;
        for (i = 0; i < getItemCount(); i++) {
            if (isItemSelected(i))
                selections.add(i);
        }
        int[] array = new int[selections.size()];
        for (i = 0; i < selections.size(); i++) {
            array[i] = selections.get(i);
        }
        return array;
    }

    public void setItems(StringList items) {
        clear();
        int visibleItemCount = items.size() > 1 ? items.size() : defaultVisibleItemCount();
        setVisibleItemCount(visibleItemCount);
        for (int i = 0; i < items.size(); i++) {
            addItem(items.get(i));
        }
    }

    public void setSelectionHandler(IListSelectionHandler selectionHandler) {
        this.selectionHandler = selectionHandler;
    }

    IListSelectionHandler selectionHandler;
    GwtWindow window;
}
