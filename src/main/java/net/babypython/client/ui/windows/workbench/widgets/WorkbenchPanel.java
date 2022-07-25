/**
 * MIT License
 * <p>
 * Copyright (c) 2022 Peter Fisk
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.babypython.client.ui.windows.workbench.widgets;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import net.babypython.client.python.api.PythonApi;
import net.babypython.client.ui.constants.WorkbenchConstants;
import net.babypython.client.ui.gwt.widgets.GwtSplitLayoutPanel;
import net.babypython.client.ui.gwt.widgets.GwtTranscriptPanel;
import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.ui.interfaces.IAfterResize;
import net.babypython.client.ui.interfaces.IHandleLineChanged;
import net.babypython.client.ui.util.Logable;
import net.babypython.client.ui.widgets.blocks.BlocksPanel;
import net.babypython.client.ui.widgets.board.BoardPanel;
import net.babypython.client.ui.widgets.monaco.PythonEditor;
import net.babypython.client.ui.windows.workbench.WorkbenchWindow;
import net.babypython.client.vm.constants.RunStyle;
import net.babypython.client.vm.containers.dictionaries.LocalDictionary;
import net.babypython.client.vm.vm.interfaces.IStdOut;
import org.apache.juli.logging.Log;

public class WorkbenchPanel extends GwtSplitLayoutPanel implements IAfterResize {

    public WorkbenchPanel(WorkbenchWindow workbenchWindow) {
        super();
        this.parentWindow = workbenchWindow;
        localDictionary = new LocalDictionary();
        runStyle = RunStyle.RUN_WITH_PAUSES;
        lhsPythonEditor = workbenchWindow.getPythonEditor();
        rhsPythonEditor = workbenchWindow.getPythonEditor();
        pythonApi = workbenchWindow.getPythonApi();
        buildLhsStack();
        buildRhsStack();
        addWest(lhsStackWrapper, this.parentWindow.defaultContentWidth() * 0.45);
        add(rhsStackWrapper);
        showLhsEditor();
        showRhsBoard();
        initializeLocalDictionary();
    }

    @Override
    public void afterResize() {
        if (lhsBlocksPanel != null)
            lhsBlocksPanel.onResize();
        if (lhsBlocksPanel != null)
            lhsBlocksPanel.onResize();
        if (lhsBlocksPanel != null)
            lhsBlocksPanel.onResize();
    }

    public IStdOut asStdOut() {
        return rhsTranscriptPanel;
    }

    public IHandleLineChanged asHandleLineChanged() {
        return lhsPythonEditor;
    }

    void initializeLocalDictionary() {
        localDictionarySet(WorkbenchConstants.LocalVariableBoard, rhsBoardPanel);
    }

    void localDictionarySet(String key, Object value) {
        getLocalDictionary().put(key, value);
    }

    void buildLhsStack() {
        lhsBlocksPanel = new BlocksPanel("Demo");
        lhsStackWrapper = new LhsStackWrapper(this);
        lhsDeckLayoutPanel = new DeckLayoutPanel();
        lhsDeckLayoutPanel.add(lhsBlocksPanel);
        lhsDeckLayoutPanel.add(lhsPythonEditor);
        lhsStackWrapper.setWidget(lhsDeckLayoutPanel);
        setWidgetMinSize(lhsStackWrapper, 0);
        showLhsEditor();
    }

    void buildRhsStack() {
        rhsBoardPanel = new BoardPanel();
        rhsTranscriptPanel = new GwtTranscriptPanel();
        rhsStackWrapper = new RhsStackWrapper(this);
        rhsDeckLayoutPanel = new DeckLayoutPanel();
        rhsDeckLayoutPanel.add(rhsBoardPanel);
        rhsDeckLayoutPanel.add(rhsPythonEditor);
        rhsDeckLayoutPanel.add(rhsTranscriptPanel);
        rhsStackWrapper.setWidget(rhsDeckLayoutPanel);
        setWidgetMinSize(rhsStackWrapper, 0);
        showRhsBoard();
    }

    public void clearBoard() {
        rhsBoardPanel.clear();
    }

    public void clearLeftEditor() {
        lhsPythonEditor.clear();
    }

    public void clearRightEditor() {
        rhsPythonEditor.clear();
    }

    public void clearTranscript() {
        rhsTranscriptPanel.clear();
    }

    public void debug() {
        pythonApi.debugScript(getCode(), asStdOut());
    }

    protected int getClientWidth() {
        return getElement().getClientWidth();
    }

    String getCode() {
        return lhsPythonEditor.getValue();
    }

    protected int getLhsDynamicSize() {
        return (int) (0.45 * getClientWidth());
    }

    LocalDictionary getLocalDictionary() {
        return localDictionary;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        Timer t = new Timer() {
            @Override
            public void run() {
                showBothSides();
            }
        };
        t.schedule(0);
    }

    public void onResizeLeft() {
        lhsBlocksPanel.onResize();
    }

    public void onResizeRight() {
//        rhsBoardPanel.afterResize();
    }

    public void run() {
        pythonApi.runScript(getCode(), asStdOut());
//        pythonApi.runScript(getCode(), asStdOut(), runStyle, asHandleLineChanged(), getLocalDictionary());
    }

    public void setBoardSize(int size) {
        rhsBoardPanel.resize(size);
    }

    public void setLhsCode(String code) {
        lhsPythonEditor.setValue(code);
    }

    public void setRunContinuously() {
        runStyle = RunStyle.RUN_CONTINUOUSLY;
    }

    public void setRunSingleStep() {
        runStyle = RunStyle.RUN_SINGLE_STEP;
    }

    public void setRunWithPauses() {
        runStyle = RunStyle.RUN_WITH_PAUSES;
    }

    public void showBothSides() {
        setWidgetSize(lhsStackWrapper, getLhsDynamicSize());
    }

    public void showLhsBlocks() {
        lhsDeckLayoutPanel.showWidget(lhsBlocksPanel);
    }

    public void showLhsEditor() {
        lhsDeckLayoutPanel.showWidget(lhsPythonEditor);
    }

    public void showLhsOnly() {
        setWidgetSize(lhsStackWrapper, getClientWidth());
    }

    public void showRhsBoard() {
        rhsDeckLayoutPanel.showWidget(rhsBoardPanel);
    }

    public void showRhsEditor() {
        rhsDeckLayoutPanel.showWidget(rhsPythonEditor);
    }

    public void showRhsOnly() {
        setWidgetSize(lhsStackWrapper, 0);
    }

    public void showRhsTranscript() {
        rhsDeckLayoutPanel.showWidget(rhsTranscriptPanel);
    }

    BlocksPanel lhsBlocksPanel;
    DeckLayoutPanel lhsDeckLayoutPanel;
    PythonEditor lhsPythonEditor;
    ResizeLayoutPanel lhsStackWrapper;
    LocalDictionary localDictionary;
    GwtWindow parentWindow;
    BoardPanel rhsBoardPanel;
    DeckLayoutPanel rhsDeckLayoutPanel;
    PythonEditor rhsPythonEditor;
    ResizeLayoutPanel rhsStackWrapper;
    GwtTranscriptPanel rhsTranscriptPanel;
    RunStyle runStyle;
    PythonApi pythonApi;
}
