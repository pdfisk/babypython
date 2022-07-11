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

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import net.babypython.client.ui.constants.ColorConstants;

public class GwtGridPanel extends SimplePanel {

    public GwtGridPanel() {
        super();
        grid = new Grid();
        grid.setWidth("100%");
        grid.setHeight("100%");
        grid.setCellSpacing(defaultSpacing());
        setWidget(grid);
        getElement().getStyle().setBackgroundColor(defaultBackgroundColor());
    }

    protected void addTiles() {
        for (int row = 0; row < grid.getRowCount(); row++)
            for (int col = 0; col < grid.getColumnCount(); col++)
                setTile(row, col, defaultTile());
    }

    protected String defaultBackgroundColor() {
        return ColorConstants.BoardBackground;
    }

    protected int defaultSpacing() {
        return 1;
    }

    protected Widget defaultTile() {
        return new SimplePanel();
    }

    protected String defaultTileColor() {
        return "white";
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public void resize(int size) {
        resize(size, size);
    }

    public void resize(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        grid.clear();
        grid.resize(rows, columns);
        addTiles();
    }

    protected void setTile(int row, int column, Widget tile) {
        tile.setWidth("100%");
        tile.setHeight("100%");
        tile.getElement().getStyle().setBackgroundColor(defaultTileColor());
        grid.setWidget(row, column, tile);
    }

    int columns;
    Grid grid;
    int rows;
}
