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
package net.babypython.client.python.builtins.objects;

import net.babypython.client.python.builtins.types.sprite.SpriteType;
import net.babypython.client.python.core.PythonObject;
import net.babypython.client.python.core.abstract_.PythonType;
import net.babypython.client.ui.constants.WorkbenchConstants;
import net.babypython.client.ui.gwt.widgets.GwtBalloonPanel;
import net.babypython.client.ui.widgets.board.BoardPanel;
import net.babypython.client.ui.widgets.board.BoardTile;
import net.babypython.client.vm.containers.dictionaries.LocalDictionary;
import net.babypython.client.vm.util.ArgsUtil;
import net.babypython.client.vm.util.StringUtil;
import net.babypython.client.vm.vm.interfaces.IStdOut;

public class PythonSpriteObject extends PythonObject {

    public PythonSpriteObject(PythonType type, IStdOut stdOut, LocalDictionary localDictionary, Object... args) {
        super(type, stdOut);
        this.localDictionary = localDictionary;
        setCol(0);
        setRow(0);
        initializeImageUrl();
        if (args.length > 0)
            spriteName = ArgsUtil.getStringArg(args);
    }

    public void clearTile() {
        BoardPanel boardPanel = getBoardPanel();
        if (boardPanel == null)
            return;
        boardPanel.clearTile(getRow(), getCol());
    }

    public void closeBalloon() {
        if (gwtBalloonPanel != null) {
            gwtBalloonPanel.hide();
            gwtBalloonPanel = null;
        }
    }

    public BoardPanel getBoardPanel() {
        if (boardPanel == null) {
            Object boardPanelObject = getLocalValue(WorkbenchConstants.LocalVariableBoard);
            if (boardPanelObject instanceof BoardPanel)
                boardPanel = (BoardPanel) boardPanelObject;
        }
        return boardPanel;
    }

    public Object getLocalValue(String key) {
        if (localDictionary == null)
            return null;
        return localDictionary.get(key);
    }

    public int getCol() {
        return col;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getRow() {
        return row;
    }

    protected void initializeImageUrl() {
        if (getType() instanceof SpriteType) {
            SpriteType pythonAbstractSpriteType = (SpriteType) getType();
            imageUrl = pythonAbstractSpriteType.getImagePath();
        }
    }

    public void moveBy(int row, int col) {
        moveTo(getRow() + row, getCol() + col);
    }

    public void moveTo(int row, int col) {
        closeBalloon();
        clearTile();
        setRow(row);
        setCol(col);
        showTile();
    }

    public void say(String text) {
        BoardPanel boardPanel = getBoardPanel();
        if (boardPanel == null)
            return;
        closeBalloon();
        BoardTile tile = boardPanel.getTile(getRow(), getCol());
        gwtBalloonPanel = new GwtBalloonPanel(text, tile);
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void showTile() {
        BoardPanel boardPanel = getBoardPanel();
        if (boardPanel == null)
            return;
        boardPanel.setTileImage(getRow(), getCol(), getImageUrl());
    }

    @Override
    public String toString() {
        return StringUtil.format("{0}({1})", getType().getName(), spriteName);
    }

    protected BoardPanel boardPanel;
    protected int col;
    protected GwtBalloonPanel gwtBalloonPanel;
    protected String imageUrl = "";
    protected LocalDictionary localDictionary;
    protected int row;
    protected String spriteName = "<none>";
}
