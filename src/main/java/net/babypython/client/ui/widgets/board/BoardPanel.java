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
package net.babypython.client.ui.widgets.board;

import com.google.gwt.user.client.ui.Widget;
import net.babypython.client.ui.constants.BoardPanelConstants;
import net.babypython.client.ui.gwt.widgets.GwtGridPanel;
import net.babypython.client.ui.interfaces.IAfterResize;
import net.babypython.client.vm.classes.special.objects.BlockObject;
import net.babypython.client.vm.util.StringUtil;
import net.babypython.client.vm.vm.context.Context;
import net.babypython.client.vm.vm.interfaces.ICallMethod;
import net.babypython.client.vm.vm.runtime.operations.IntOps;

import java.util.ArrayList;

public class BoardPanel extends GwtGridPanel implements IAfterResize, ICallMethod {

    @Override
    public void afterResize() {
        for (BoardTile boardTile : tileDictionary.values())
            boardTile.afterResize();
    }

    @Override
    public Object callMethod(Context context, String methodName, ArrayList<Object> args) {
//        switch (methodName) {
//            case BoardPanelConstants.CLEAR:
//                clear();
//                break;
//            case BoardPanelConstants.CLEAR_TILE:
//                clearTile(args);
//                break;
//            case BoardPanelConstants.SET_CLICK_HANDLER:
//                setClickHandler(args);
//                break;
//            case BoardPanelConstants.SET_TILE_BACKGROUND:
//                setTileColor(args);
//                break;
//            case BoardPanelConstants.SET_TILE_IMAGE:
//                setTileImage(args);
//                break;
//            case BoardPanelConstants.SET_TILE_TEXT:
//                setTileText(args);
//                break;
//        }
        return null;
    }

    @Override
    public void clear() {
        for (BoardTile tile : tileDictionary.values())
            tile.clear();
    }

    void clearTile(ArrayList<Object> args) {
        if (args.size() < 2)
            return;
        int row = IntOps.coerce(args.get(0));
        int column = IntOps.coerce(args.get(1));
        clearTile(row, column);
    }

    public void clearTile(int row, int column) {
        BoardTile tile = getTile(row, column);
        if (tile == null)
            return;
        tile.clearImage();
    }

    protected Widget defaultTile() {
        return new BoardTile(this);
    }

    public BlockObject getClickHandler() {
        return clickHandlerBlock;
    }

    public BoardTile getTile(int row, int column) {
        return tileDictionary.get(locationTag(row, column));
    }

    protected String locationTag(int row, int column) {
        return "row:" + row + "-column:" + column;
    }

    @Override
    public void onAttach() {
        super.onAttach();
        resize(BoardPanelConstants.INITIAL_SIZE);
    }

    public void onTileClicked(int row, int column) {
        ArrayList<Object> args = new ArrayList<>();
        args.add(column);
        args.add(row);
        if (clickHandlerBlock != null)
            clickHandlerBlock.fork(args.toArray());
    }

    public void setClickHandler(BlockObject blockContext) {
        clickHandlerBlock = blockContext;
    }

    void setTileColor(ArrayList<Object> args) {
        if (args.size() < 3)
            return;
        int row = IntOps.coerce(args.get(0));
        int column = IntOps.coerce(args.get(1));
        String color = StringUtil.__str__(args.get(2));
        setTileColor(row, column, color);
    }

    public void setTileColor(int row, int column, String color) {
        BoardTile tile = getTile(row, column);
        if (tile == null)
            return;
        tile.setBackground(color);
    }

    void setTileImage(ArrayList<Object> args) {
        if (args.size() < 3)
            return;
        int row = IntOps.coerce(args.get(0));
        int column = IntOps.coerce(args.get(1));
        String path = StringUtil.__str__(args.get(2));
        setTileImage(row, column, path);
    }

    public void setTileImage(int row, int column, String path) {
        BoardTile tile = getTile(row, column);
        if (tile == null)
            return;
        tile.setImage(path);
    }

    void setTileText(ArrayList<Object> args) {
        if (args.size() < 3)
            return;
        int row = IntOps.coerce(args.get(0));
        int column = IntOps.coerce(args.get(1));
        String text = StringUtil.__str__(args.get(2));
        setTileText(row, column, text);
    }

    public void setTileText(int row, int column, String text) {
        BoardTile tile = getTile(row, column);
        if (tile == null)
            return;
        tile.setText(text);
    }

    protected void setTile(int row, int column, Widget tile) {
        super.setTile(row, column, tile);
        if (!(tile instanceof BoardTile))
            return;
        BoardTile boardTile = (BoardTile) tile;
        boardTile.setRow(row);
        boardTile.setColumn(column);
        tileDictionary.put(locationTag(row, column), boardTile);
    }

    @Override
    public String toString() {
        return "BoardPanel(" + getColumns() + ")";
    }

    BlockObject clickHandlerBlock;
    TileDictionary tileDictionary = new TileDictionary();
}
