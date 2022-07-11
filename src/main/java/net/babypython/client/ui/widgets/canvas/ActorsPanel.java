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
package net.babypython.client.ui.widgets.canvas;

import net.babypython.client.ui.constants.SpriteConstants;
import net.babypython.client.ui.gwt.widgets.GwtCanvas;
import net.babypython.client.ui.widgets.sprites.ImageSprite;
import net.babypython.client.ui.widgets.sprites.Rectangle;
import net.babypython.client.ui.widgets.sprites.Sprite;

import java.util.ArrayList;

public class ActorsPanel extends GwtCanvas {

    public ActorsPanel() {
        super();
        sprites = new ArrayList<>();
        addSprites();
    }

    protected void addImage(String tag, int x, int y, double scale) {
        sprites.add(new ImageSprite(tag, x, y, scale));
        maxX = Math.max(x, maxX);
        maxY = Math.max(y, maxY);
    }

    protected void addRectangle(String color, int x, int y, int w, int h) {
        sprites.add(new Rectangle(color, x, y, w, h));
    }

    protected void addSprites() {
        int x = -95, y = 5;
        addImage(SpriteConstants.CatLeft, x += 125, y, 0.5);
        addImage(SpriteConstants.CatRight, x += 125, y, 0.5);
        addImage(SpriteConstants.ChickLeft, x += 125, y, 0.08);
        addImage(SpriteConstants.ChickRight, x + 125, y, 0.08);
        addImage(SpriteConstants.ChickenAndChick, x = 5, y += 125, 0.4);
        addImage(SpriteConstants.DogAtHomeLeft, x += 125, y, 0.6);
        addImage(SpriteConstants.DogHouse, x += 145, y, 0.6);
        addImage(SpriteConstants.DuckFront, x + 145, y, 0.5);
        addImage(SpriteConstants.DuckLeft, x = 5, y += 125, 0.5);
        addImage(SpriteConstants.DuckRight, x += 125, y, 0.5);
        addImage(SpriteConstants.DucklingInHouse, x += 125, y, 0.5);
        addImage(SpriteConstants.Fence, x + 125, y, 0.5);
        addImage(SpriteConstants.OrangeDucklingLeft, x = 5, y += 125, 0.5);
        addImage(SpriteConstants.OrangeDucklingRight, x += 125, y, 0.5);
        addImage(SpriteConstants.PaleChickenLeft, x += 125, y, 0.5);
        addImage(SpriteConstants.PaleChickenRight, x + 125, y, 0.5);
        addImage(SpriteConstants.Pump, x = 5, y += 145, 0.25);
        addImage(SpriteConstants.PumpWithDog, x += 145, y, 0.3);
        addImage(SpriteConstants.PumpWithDogAndDuckling, x + 165, y, 0.3);
    }

    protected void drawSprites() {
        context.beginPath();
        for (int i = 0; i < sprites.size(); i++)
            sprites.get(i).draw(context);
        context.closePath();
    }

    protected int getMinCanvasHeight() {
        int height = getElement().getClientHeight();
        return Math.max(height, maxY + 250);
    }

    protected int getMinCanvasWidth() {
        int width = getElement().getClientWidth();
        return Math.max(width, maxX + 250);
    }

    public void redraw() {
        super.redraw();
        drawSprites();
    }

    int maxX = 0;
    int maxY = 0;

    ArrayList<Sprite> sprites;
}
