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

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.*;
import net.babypython.client.ui.gwt.widgets.GwtCustomButton;
import net.babypython.client.ui.interfaces.IAfterResize;
import net.babypython.client.ui.util.Logable;

public class BoardTile extends DockLayoutPanel implements IAfterResize {

    public BoardTile(BoardPanel boardPanel) {
        super(Style.Unit.PCT);
        this.boardPanel = boardPanel;
        addInnerPanels();
        sinkEvents(Event.ONCLICK);
    }

    void addInnerPanels() {
        gwtCustomButton = new GwtCustomButton();
        gwtCustomButton.setWidth("100%");
        gwtCustomButton.setHeight("100%");
        add(gwtCustomButton);
    }

    @Override
    public void afterResize() {
        if (gwtCustomButton != null)
            gwtCustomButton.onResize();
    }

    public void clear() {
        clearImage();
        setText(" ");
    }

    public void clearImage() {
        gwtCustomButton.clearImage();
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public void onBrowserEvent(Event event) {
        switch (DOM.eventGetType(event)) {
            case Event.ONCLICK:
                boardPanel.onTileClicked(row, column);
                break;
        }
    }

    public void setBackground(String color) {
        Logable.info("setBackground", color);
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setImage(String imageName) {
        gwtCustomButton.setButtonImage(imageName);
    }

    public void setText(String text) {
        gwtCustomButton.setText(text);
    }

    BoardPanel boardPanel;
    int column;
    GwtCustomButton gwtCustomButton;
    int row;
}
