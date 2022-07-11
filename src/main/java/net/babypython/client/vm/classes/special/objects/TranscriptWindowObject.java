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
package net.babypython.client.vm.classes.special.objects;

import net.babypython.client.vm.constants.OutputFormatting;
import net.babypython.client.vm.constants.PythonMethodConstants;
import net.babypython.client.vm.constants.PythonVarNameConstants;
import net.babypython.client.vm.classes.special.classes.TranscriptWindowClass;
import net.babypython.client.vm.interfaces.IPerform;
import net.babypython.client.ui.windows.transcript.TranscriptWindow;
import net.babypython.client.ui.util.Logable;
import net.babypython.client.vm.vm.context.Context;
import net.babypython.client.vm.vm.interfaces.IStdOut;

import java.util.ArrayList;

public class TranscriptWindowObject extends WindowObject implements IPerform, IStdOut {

    TranscriptWindowObject() {
        super(TranscriptWindowClass.getInstance());
    }

    @Override
    public Object callMethod(Context frame, String selector, ArrayList<Object> args) {
        Logable.info("TranscriptWindowObject", selector);
        return NilObject.getInstance();
    }

    @Override
    protected void createWindow() {
        gwtWindow = transcriptWindow = TranscriptWindow.getInstance();
    }

    @Override
    public void clear() {
        TranscriptWindow.clr();
    }

    @Override
    public OutputFormatting getFormatting() {
        return null;
    }

    @Override
    public void newline() {
        TranscriptWindow.getInstance().newline();
    }

    @Override
    public Object perform(Context context, Object receiverObject, String selector, Object[] args) {
        switch (selector) {
            case PythonMethodConstants.MessageClear:
                clear();
                break;
            case PythonMethodConstants.MessageNewline:
                newline();
                break;
            case PythonMethodConstants.MessagePrArg:
                pr(args);
                break;
            case PythonMethodConstants.MessagePrnArg:
            case PythonMethodConstants.MessagePrnArg2:
            case PythonMethodConstants.MessagePrnArg3:
            case PythonMethodConstants.MessagePrnArg4:
            case PythonMethodConstants.MessagePrnArg5:
                prn(args);
                break;
            default:
                return null;
        }
        return this;
    }

    @Override
    public void pr(Object... args) {
        TranscriptWindow.getInstance().pr(args);
    }

    @Override
    public void prn(Object... args) {
        TranscriptWindow.getInstance().prn(args);
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

    public String toString() {
        return PythonVarNameConstants.GlobalNameTranscript;
    }

    public static TranscriptWindowObject getInstance() {
        if (instance == null)
            instance = new TranscriptWindowObject();
        return instance;
    }

    TranscriptWindow transcriptWindow;
    static TranscriptWindowObject instance;
}
