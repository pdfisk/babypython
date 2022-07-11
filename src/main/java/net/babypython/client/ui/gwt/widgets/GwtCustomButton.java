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

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.CustomButton;
import com.google.gwt.user.client.ui.Image;

public class GwtCustomButton extends CustomButton {

    public void clearImage() {
        buttonImage = new Image();
        Face face = getUpFace();
        if (face != null)
            face.setImage(buttonImage);
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        onResize();
    }

    public void onResize() {
        Element element = getElement();
        buttonHeight = element.getClientHeight();
        buttonWidth = element.getClientWidth();
        resizeImage();
        resizeText();
    }

    void resizeImage() {
        buttonImage.setWidth(buttonWidth + "px");
        buttonImage.setHeight(buttonHeight + "px");
    }

    void resizeText() {
        if (buttonText == null)
            return;
        Face face = getUpFace();
        if (face != null) {
            int height = getElement().getClientHeight();
            int fontSize = height / 2;
            String style = "";
            style += "line-height:" + height + "px;";
            style += "width:100%;";
            style += "height:100%;";
            style += "font-size:" + fontSize + "px;";
            style += "font-weight:bold;";
            style += "text-align:center;";
            style += "vertical-align:middle;";
            String html = "<div style=\"" + style + "\">" + buttonText + "</div>";
            face.setHTML(html);
        }
    }

    public void setButtonImage(String imageName) {
        String path = "images/animals/" + imageName;
        buttonImage = new Image();
        buttonImage.setUrl(path);
        onResize();
        Face face = getUpFace();
        face.setText(null);
        face.setImage(buttonImage);
    }

    @Override
    public void setText(String text) {
        buttonText = text;
        clearImage();
        Timer t = new Timer() {
            @Override
            public void run() {
                resizeText();
            }
        };
        t.schedule(100);
    }

    int buttonHeight;
    Image buttonImage = new Image();
    String buttonText;
    int buttonWidth;
}
