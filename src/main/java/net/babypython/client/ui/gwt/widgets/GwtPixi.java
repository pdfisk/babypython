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

import net.babypython.client.ui.widgets.sprites.images.ImagesHolder;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.ImageElement;

import java.util.HashMap;

public class GwtPixi extends GwtCanvas {

    public GwtPixi() {
        super();
        spritesMap = new HashMap<>();
        buildPixiApp();
    }

    public void addSprite(String name, String imageName, int x, int y, double scale) {
        ImageElement imageElement = ImagesHolder.getInstance().getImageElement(imageName);
        JavaScriptObject sprite = nativeCreatePixiSprite(imageElement);
        spritesMap.put(name, sprite);
        nativeSetSpriteAnchorPoint(sprite, 0.5);
        nativeSetSpritePosition(sprite, x, y);
        nativeAddSpriteToStage(pixiStage, sprite);
        nativeSetSpriteScale(sprite, scale);
    }

    void buildPixiApp() {
        pixiView = canvas.getCanvasElement();
        pixiApp = nativeCreatePixiApp(pixiView);
        pixiStage = nativeGetStage(pixiApp);
    }

    public void clear() {
        for (String name : spritesMap.keySet())
            removeSprite(name);
    }

    public void removeSprite(String name) {
        if (!spritesMap.containsKey(name))
            return;
        JavaScriptObject sprite = spritesMap.get(name);
        spritesMap.remove(name);
        nativeRemoveSpriteFromStage(pixiStage, sprite);
    }

    public void resize() {
        resizeCanvas(getElement().getClientWidth(), getElement().getClientHeight());
    }

    public void setSpritePosition(String name, int x, int y) {
        if (!spritesMap.containsKey(name))
            return;
        JavaScriptObject sprite = spritesMap.get(name);
        nativeSetSpritePosition(sprite, x, y);
    }

    native void nativeAddSpriteToStage(JavaScriptObject stage, JavaScriptObject sprite) /*-{
        stage.addChild(sprite);
    }-*/;

    native JavaScriptObject nativeCreatePixiApp(JavaScriptObject canvasElement) /*-{
        return new $wnd.PIXI.Application({
            backgroundColor: 0x1099bb,
            view: canvasElement
        });
    }-*/;

    native JavaScriptObject nativeCreatePixiSprite(ImageElement imageElement) /*-{
        return $wnd.PIXI.Sprite.from(imageElement);
    }-*/;

    native JavaScriptObject nativeGetStage(JavaScriptObject sprite) /*-{
        return sprite.stage;
    }-*/;

    native void nativeRemoveSpriteFromStage(JavaScriptObject stage, JavaScriptObject sprite) /*-{
        stage.removeChild(sprite);
    }-*/;

    native void nativeSetSpriteAnchorPoint(JavaScriptObject sprite, double anchorPoint) /*-{
        sprite.anchor.set(anchorPoint);
    }-*/;

    native void nativeSetSpritePosition(JavaScriptObject sprite, int x, int y) /*-{
        sprite.x = x;
        sprite.y = y;
    }-*/;

    native void nativeSetSpriteScale(JavaScriptObject sprite, double scale) /*-{
        var oldWidth = sprite.width;
        var oldHeight = sprite.height;
        sprite.width = oldWidth * scale;
        sprite.height = oldHeight * scale;
    }-*/;

    JavaScriptObject pixiApp;
    JavaScriptObject pixiStage;
    JavaScriptObject pixiView;
    HashMap<String, JavaScriptObject> spritesMap;
}
