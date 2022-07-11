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
import net.babypython.client.vm.interfaces.IPerform;
import net.babypython.client.vm.core.VClass;
import net.babypython.client.vm.core.VObject;
import net.babypython.client.ui.widgets.board.BoardPanel;
import net.babypython.client.vm.vm.context.Context;
import net.babypython.client.vm.vm.runtime.operations.IntOps;
import net.babypython.client.vm.vm.runtime.operations.StringOps;

public class BoardPanelObject extends VObject implements IPerform {

    public BoardPanelObject(VClass vClass) {
        super(vClass);
        createBoardPanel();
    }

    protected void createBoardPanel() {
        boardPanel = new BoardPanel();
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    @Override
    public Widget getWidget() {
        return boardPanel;
    }

    @Override
    public boolean isWidget() {
        return true;
    }

    @Override
    public Object perform(Context frame, Object receiverObject, String selector, Object[] args) {
        switch (selector) {
            case PythonMethodConstants.MessageClear:
                return onClear();
            case PythonMethodConstants.MessageClearRowColumnArgs:
                return onClearRowColumn(args);
            case PythonMethodConstants.MessageClickHandler:
                return onClickHandler();
            case PythonMethodConstants.MessageClickHandlerArg:
                return onClickHandler(args);
            case PythonMethodConstants.MessageColorRowColumnArg:
                return onColorRowColumn(args);
            case PythonMethodConstants.MessageImageRowColumnArg:
                return onImageRowColumn(args);
            case PythonMethodConstants.MessageResizeArg:
                return onResize(args);
            case PythonMethodConstants.MessageTextRowColumnArg:
                return onTextRowColumn(args);
            default:
                return this;
        }
    }

    Object onClear() {
        boardPanel.clear();
        return this;
    }

    Object onClearRowColumn(Object[] args) {
        int row = utilGetIntArg(0, args);
        int column = utilGetIntArg(1, args);
        boardPanel.clearTile(row, column);
        return this;
    }

    Object onClickHandler() {
        return boardPanel.getClickHandler();
    }

    Object onClickHandler(Object[] args) {
        if (args.length > 0 && args[0] instanceof BlockObject)
            boardPanel.setClickHandler((BlockObject) args[0]);
        return this;
    }

    Object onColorRowColumn(Object[] args) {
        String color = utilGetStringArg(0, args);
        int row = utilGetIntArg(1, args);
        int column = utilGetIntArg(2, args);
        boardPanel.setTileColor(row, column, color);
        return this;
    }

    Object onImageRowColumn(Object[] args) {
        String path = utilGetStringArg(0, args);
        int row = utilGetIntArg(1, args);
        int column = utilGetIntArg(2, args);
        boardPanel.setTileImage(row, column, path);
        return this;
    }

    Object onResize(Object[] args) {
        int size = utilGetIntArg(0, args);
        boardPanel.resize(size);
        return this;
    }

    Object onTextRowColumn(Object[] args) {
        String text = utilGetStringArg(0, args);
        int row = utilGetIntArg(1, args);
        int column = utilGetIntArg(2, args);
        boardPanel.setTileText(row, column, text);
        return this;
    }

    int utilGetIntArg(int index, Object[] args) {
        if (args.length < index)
            return -1;
        return IntOps.coerce(args[index]);
    }

    String utilGetStringArg(int index, Object[] args) {
        if (args.length < index)
            return "";
        return StringOps.coerce(args[index]);
    }

    protected BoardPanel boardPanel;
}
