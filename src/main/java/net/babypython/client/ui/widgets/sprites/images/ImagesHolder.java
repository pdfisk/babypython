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
package net.babypython.client.ui.widgets.sprites.images;

import net.babypython.client.ui.constants.SpriteConstants;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ImagesHolder extends VerticalPanel {

    ImagesHolder() {
        super();
        images = new HashMap<>();
        initialize();
    }

    void addAnimalImage(String fileName) {
        String url = "images/animals/" + fileName;
        addImage(fileName, url);
    }

    void addImage(String tag, String url) {
        Image image = new Image(url);
        images.put(tag, new ImageHolder(image));
        add(image);
    }

    void addImages() {
        for (String spriteFileName : SpriteConstants.getAllSprites())
            addAnimalImage(spriteFileName);
    }

    public ImageElement getImageElement(String tag) {
        if (!images.containsKey(tag))
            return null;
        return images.get(tag).asImageElement();
    }

    public ArrayList<String> getAllSpriteTags() {
        ArrayList<String> tags = new ArrayList<>();
        for (String key : images.keySet())
            tags.add(key);
        Collections.sort(tags);
        return tags;
    }

    void initialize() {
        getElement().getStyle().setDisplay(Style.Display.NONE);
        setHeight("100%");
        addImages();
    }

    public static ImageElement getImageElementFor(String tag) {
        return getInstance().getImageElement(tag);
    }

    public static ImagesHolder getInstance() {
        if (instance == null)
            instance = new ImagesHolder();
        return instance;
    }

    static ImagesHolder instance;
    HashMap<String, ImageHolder> images;
}
