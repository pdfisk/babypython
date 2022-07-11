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
package net.babypython.client.ui.windows.workbench;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import net.babypython.client.python.api.PythonApi;
import net.babypython.client.ui.constants.CommonWindowConstants;
import net.babypython.client.ui.constants.WindowButtonConstants;
import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.ui.gwt.window.widgets.GwtWindowButtonBar;
import net.babypython.client.ui.widgets.monaco.PythonEditor;
import net.babypython.client.ui.windows.workbench.widgets.ButtonBar;
import net.babypython.client.ui.windows.workbench.widgets.WorkbenchPanel;

public class WorkbenchWindow extends GwtWindow {

    @Override
    protected GwtWindowButtonBar defaultButtonBar() {
        return new ButtonBar(this);
    }

    @Override
    protected Widget defaultContent() {
        return workbenchPanel = new WorkbenchPanel(this);
    }

    @Override
    protected int defaultHeight() {
        return CommonWindowConstants.WorkbenchWindowHeight;
    }

    @Override
    protected String defaultTitle() {
        return CommonWindowConstants.WorkbenchWindowTitle;
    }

    @Override
    protected int defaultWidth() {
        return CommonWindowConstants.WorkbenchWindowWidth;
    }

    public PythonEditor getPythonEditor() {
        return new PythonEditor(this);
    }

    public PythonApi getPythonApi() {
        return PythonApi.getInstance();
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        if (deferredCode != null) {
            setCodeAfterAttach(deferredCode);
            deferredCode = null;
        }
    }

    @Override
    public void onButtonClicked(String actionTag) {
        switch (actionTag) {
            case WindowButtonConstants.WorkbenchActionBoardSize3:
                onBoardSize(3);
                break;
            case WindowButtonConstants.WorkbenchActionBoardSize5:
                onBoardSize(5);
                break;
            case WindowButtonConstants.WorkbenchActionBoardSize7:
                onBoardSize(7);
                break;
            case WindowButtonConstants.WorkbenchActionBoardSize9:
                onBoardSize(9);
                break;
            case WindowButtonConstants.WorkbenchActionClearBoard:
                onClearBoard();
                break;
            case WindowButtonConstants.WorkbenchActionClearLeftEditor:
                onClearLeftEditor();
                break;
            case WindowButtonConstants.WorkbenchActionClearRightEditor:
                onClearRightEditor();
                break;
            case WindowButtonConstants.WorkbenchActionClearTranscript:
                onClearTranscript();
                break;
            case WindowButtonConstants.WorkbenchActionDebug:
                onDebug();
                break;
            case WindowButtonConstants.WorkbenchActionLeftBlocks:
                onLeftBlocks();
                break;
            case WindowButtonConstants.workbenchActionLeftEditor:
                onLeftEditor();
                break;
            case WindowButtonConstants.WorkbenchActionRightBoard:
                onRightBoard();
                break;
            case WindowButtonConstants.WorkbenchActionRightEditor:
                onRightEditor();
                break;
            case WindowButtonConstants.WorkbenchActionRightTranscript:
                onRightTranscript();
                break;
            case WindowButtonConstants.WorkbenchActionRun:
                onRun();
                break;
            case WindowButtonConstants.WorkbenchActionRunContinuously:
                onRunContinuously();
                break;
            case WindowButtonConstants.WorkbenchActionRunSingleStep:
                onRunSingleStep();
                break;
            case WindowButtonConstants.WorkbenchActionRunWithPauses:
                onRunWithPauses();
                break;
            case WindowButtonConstants.WorkbenchActionShowBoth:
                onShowBoth();
                break;
            case WindowButtonConstants.WorkbenchActionShowLeft:
                onShowLeft();
                break;
            case WindowButtonConstants.WorkbenchActionShowRight:
                onShowRight();
                break;
            default:
                super.onButtonClicked(actionTag);
                break;
        }
    }

    void onBoardSize(int size) {
        workbenchPanel.setBoardSize(size);
    }

    void onClearBoard() {
        workbenchPanel.clearBoard();
    }

    void onClearLeftEditor() {
        workbenchPanel.clearLeftEditor();
    }

    void onClearRightEditor() {
        workbenchPanel.clearRightEditor();
    }

    void onClearTranscript() {
        workbenchPanel.clearTranscript();
    }

    void onDebug() {
        workbenchPanel.debug();
    }

    void onLeftBlocks() {
//        workbenchPanel.showLhsBlocks();
    }

    void onLeftEditor() {
        workbenchPanel.showLhsEditor();
    }

    void onRightBoard() {
        workbenchPanel.showRhsBoard();
    }

    void onRightEditor() {
        workbenchPanel.showRhsEditor();
    }

    void onRightTranscript() {
        workbenchPanel.showRhsTranscript();
    }

    void onRun() {
        workbenchPanel.run();
    }

    void onRunContinuously() {
        workbenchPanel.setRunContinuously();
    }

    void onRunSingleStep() {
        workbenchPanel.setRunSingleStep();
    }

    void onRunWithPauses() {
        workbenchPanel.setRunWithPauses();
    }

    void onShowBoth() {
        workbenchPanel.showBothSides();
    }

    void onShowLeft() {
        workbenchPanel.showLhsOnly();
    }

    void onShowRight() {
        workbenchPanel.showRhsOnly();
    }

    public void setCode(String code) {
        if (isAttached())
            setCodeAfterAttach(code);
        else
            deferredCode = code;
    }

    void setCodeAfterAttach(String code) {
        Timer t = new Timer() {
            @Override
            public void run() {
                onLeftEditor();
                onShowBoth();
                workbenchPanel.setLhsCode(code);
                moveToFront();
            }
        };
        t.schedule(2000);
    }

    String deferredCode;
    WorkbenchPanel workbenchPanel;
}
