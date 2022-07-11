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
package net.babypython.client.ui.gwt.window.widgets;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.MenuBar;
import net.babypython.client.ui.constants.StyleConstants;
import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.vm.classes.special.objects.BlockObject;
import net.babypython.client.vm.classes.special.objects.WindowButtonObject;
import net.babypython.client.vm.containers.lists.StringList;

public class GwtWindowButton extends Button {

    public GwtWindowButton(String label, GwtWindow window) {
        super(label);
        this.label = label;
        this.window = window;
        actionTag = label.toLowerCase().replace(' ', '_').replace('-', '_');
        getElement().addClassName("windowBarButton");
        sinkEvents(Event.ONCLICK);
    }

    void buildMenuDialog() {
        MenuBar items = new MenuBar(true);
        for (String menuLabel : menuButtons) {
            if (menuLabel.startsWith("-")) {
                items.addSeparator();
                continue;
            }
            String menuAction = menuLabel.toLowerCase().replace(' ', '_');
            items.addItem(menuLabel, new Scheduler.ScheduledCommand() {
                @Override
                public void execute() {
                    window.onButtonClicked(menuAction);
                }
            });
        }
        menuDialog = new DialogBox(true);
        menuDialog.add(items);
    }

    public BlockObject getClickHandler() {
        return clickHandlerBlock;
    }

    @Override
    public void onBrowserEvent(Event event) {
        event.preventDefault();
        event.stopPropagation();
        switch (DOM.eventGetType(event)) {
            case Event.ONCLICK:
                onClicked();
                break;
        }
    }

    void onClicked() {
        if (menuButtons != null)
            showMenu();
        else if (windowButtonObject == null)
            window.onButtonClicked(actionTag);
        else
            windowButtonObject.onClicked();
    }

    public void setClickHandler(BlockObject blockContext) {
        clickHandlerBlock = blockContext;
    }

    public void setMenuButtons(StringList menuButtons) {
        this.menuButtons = menuButtons;
    }

    public void setWindowButtonObject(WindowButtonObject windowButtonObject) {
        this.windowButtonObject = windowButtonObject;
    }

    void showMenu() {
        if (menuDialog == null)
            buildMenuDialog();
        menuDialog.show();
        menuDialog.setPopupPosition(getAbsoluteLeft(), getAbsoluteTop() - menuDialog.getElement().getClientHeight());
        menuDialog.getElement().getStyle().setZIndex(StyleConstants.MaxZIndex);
    }

    String actionTag;
    BlockObject clickHandlerBlock;
    String label;
    StringList menuButtons;
    DialogBox menuDialog;
    GwtWindow window;
    WindowButtonObject windowButtonObject;
}
