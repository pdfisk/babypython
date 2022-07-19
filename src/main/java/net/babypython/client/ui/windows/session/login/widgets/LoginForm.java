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
package net.babypython.client.ui.windows.session.login.widgets;

import net.babypython.client.ui.constants.CommonWindowConstants;
import net.babypython.client.ui.gwt.widgets.GwtForm;
import net.babypython.client.ui.gwt.widgets.GwtTextBox;

public class LoginForm extends GwtForm {

    protected void addFields() {
        super.addFields();
        nameField = addTextBox("Name");
        nameField.setIsLowercase();
        nameField.setIsNoSpaces();
        passwordField = addTextBox("Password");
    }

    @Override
    protected int defaultLabelWidth() {
        return CommonWindowConstants.LoginWindowLabelWidth;
    }

    @Override
    protected int defaultWidgetAdjust() {
        return CommonWindowConstants.LoginWindowWidgetAdjust;
    }

    public String getName() {
        return nameField.getValue();
    }

    public String getPassword() {
        return passwordField.getValue();
    }

    public void reset() {
        nameField.setValue("");
        passwordField.setValue("");
    }

    GwtTextBox nameField;
    GwtTextBox passwordField;
}
