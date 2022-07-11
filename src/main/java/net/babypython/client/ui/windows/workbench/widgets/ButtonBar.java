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
package net.babypython.client.ui.windows.workbench.widgets;

import net.babypython.client.constants.AppConstants;
import net.babypython.client.ui.constants.WindowButtonConstants;
import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.ui.gwt.window.widgets.GwtWindowButton;
import net.babypython.client.ui.gwt.window.widgets.GwtWindowButtonBar;
import net.babypython.client.vm.containers.lists.StringList;

public class ButtonBar extends GwtWindowButtonBar {

    public ButtonBar(GwtWindow window) {
        super(window);
    }

    void addActionButton() {
        GwtWindowButton actionButton = addButton(WindowButtonConstants.WorkbenchButtonAction);
        StringList menuButtons = new StringList();
        menuButtons.add(WindowButtonConstants.WorkbenchButtonActionClearTranscript);
        menuButtons.add(WindowButtonConstants.Separator);
        menuButtons.add(WindowButtonConstants.WorkbenchButtonActionClearLeftEditor);
        menuButtons.add(WindowButtonConstants.WorkbenchButtonActionClearRightEditor);
        menuButtons.add(WindowButtonConstants.Separator);
        menuButtons.add(WindowButtonConstants.WorkbenchButtonActionClearBoard);
        actionButton.setMenuButtons(menuButtons);
    }

    void addConfigButton() {
        GwtWindowButton configButton = addButton(WindowButtonConstants.WorkbenchButtonConfig);
        StringList menuButtons = new StringList();
        menuButtons.add(WindowButtonConstants.WorkbenchButtonRunContinuously);
        menuButtons.add(WindowButtonConstants.Separator);
//        menuButtons.add(WindowButtonConstants.WorkbenchButtonRunSingleStep);
        menuButtons.add(WindowButtonConstants.WorkbenchButtonRunWithPauses);
        menuButtons.add(WindowButtonConstants.Separator);
        menuButtons.add(WindowButtonConstants.WorkbenchButtonBoardSize3);
        menuButtons.add(WindowButtonConstants.WorkbenchButtonBoardSize5);
        menuButtons.add(WindowButtonConstants.WorkbenchButtonBoardSize7);
        menuButtons.add(WindowButtonConstants.WorkbenchButtonBoardSize9);
        configButton.setMenuButtons(menuButtons);
    }

    void addLayoutButton() {
        GwtWindowButton layoutButton = addButton(WindowButtonConstants.WorkbenchButtonLayout);
        StringList menuButtons = new StringList();
        menuButtons.add(WindowButtonConstants.WorkbenchButtonShowBoth);
        menuButtons.add(WindowButtonConstants.Separator);
        menuButtons.add(WindowButtonConstants.WorkbenchButtonShowLeft);
        menuButtons.add(WindowButtonConstants.WorkbenchButtonShowRight);
        menuButtons.add(WindowButtonConstants.Separator);
        menuButtons.add(WindowButtonConstants.WorkbenchButtonLeftBlocks);
        menuButtons.add(WindowButtonConstants.WorkbenchButtonLeftEditor);
        menuButtons.add(WindowButtonConstants.Separator);
        menuButtons.add(WindowButtonConstants.WorkbenchButtonRightBoard);
        menuButtons.add(WindowButtonConstants.WorkbenchButtonRightEditor);
        menuButtons.add(WindowButtonConstants.WorkbenchButtonRightTranscript);
        layoutButton.setMenuButtons(menuButtons);
    }

    @Override
    protected void addSpecialButtons() {
        addActionButton();
        addLayoutButton();
        addConfigButton();
    }

    @Override
    protected StringList defaultButtons() {
        StringList buttons = new StringList();
        buttons.add(WindowButtonConstants.WorkbenchButtonRun);
        if (AppConstants.SHOW_DEBUG)
            buttons.add(WindowButtonConstants.WorkbenchButtonDebug);
        return buttons;
    }
}
