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

import net.babypython.client.ui.constants.CommonWindowConstants;
import net.babypython.client.ui.windows.transcript.TranscriptWindow;
import net.babypython.client.vm.constants.OutputFormatting;
import net.babypython.client.vm.util.StringUtil;
import net.babypython.client.vm.vm.context.Context;
import net.babypython.client.vm.vm.interfaces.IStdOut;

import java.util.ArrayList;

public class GwtTranscriptPanel extends GwtTextPanel implements IStdOut {

    public GwtTranscriptPanel() {
        this(null);
    }

    public GwtTranscriptPanel(TranscriptWindow transcriptWindow) {
        super();
        this.transcriptWindow = transcriptWindow;
    }

    public Object callMethod(Context context, String methodName, ArrayList<Object> args) {
        switch (methodName) {
            case "clear":
                clear();
                break;
            case "hide":
                if (transcriptWindow != null)
                    transcriptWindow.hide();
                break;
            case "print":
                prnArgs(args.toArray());
                break;
            case "show":
                if (transcriptWindow != null)
                    transcriptWindow.show();
                break;
            case "wide":
                if (transcriptWindow != null)
                    transcriptWindow.setWidth(CommonWindowConstants.TranscriptWindowWidthWide);
                break;
            case "widest":
                if (transcriptWindow != null)
                    transcriptWindow.setWidth(CommonWindowConstants.TranscriptWindowWidthVeryWide);
                break;
        }
        return null;
    }

    @Override
    public OutputFormatting getFormatting() {
        return null;
    }

    @Override
    public void newline() {
        pr("\n");
        scrollToEnd();
    }

    @Override
    public void pr(Object... args) {
        prArgs(args);
    }

    public void prArgs(Object[] args) {
        setValue(getValue() + StringUtil.argsToString(args));
    }

    @Override
    public void prn(Object... args) {
        prnArgs(args);
    }

    @Override
    public void setFormatting(OutputFormatting formatting) {

    }

    @Override
    public void setRepr() {

    }

    @Override
    public void setStr() {

    }

    public void prnArgs(Object[] args) {
        pr(args);
        newline();
    }

    public String toString() {
        return "a Transcript panel";
    }

    TranscriptWindow transcriptWindow;
}
