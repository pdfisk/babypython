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
package net.babypython.client.python.streams;

import net.babypython.client.vm.constants.OutputFormatting;
import net.babypython.client.ui.util.Logable;
import net.babypython.client.vm.util.ReprUtil;
import net.babypython.client.vm.util.StringUtil;
import net.babypython.client.vm.vm.context.Context;
import net.babypython.client.vm.vm.interfaces.IStdOut;

import java.util.ArrayList;

public class PythonOutputStream implements IStdOut {

    public PythonOutputStream(IStdOut rawStream) {
        this.rawStream = rawStream;
        setRepr();
        setShowNone(false);
    }

    String asFormattedString(Object value) {
        if (formatting == OutputFormatting.REPR)
            return asReprString(value);
        return asStrString(value);
    }

    String asReprString(Object value) {
        return ReprUtil.repr(value);
    }

    String asStrString(Object value) {
        return StringUtil.__str__(value);
    }

    @Override
    public Object callMethod(Context context, String methodName, ArrayList<Object> args) {
        Logable.info("PythonOutputStream callMethod", methodName);
        return null;
    }

    void comma() {
        rawStream.pr(",");
    }

    @Override
    public void clear() {
        rawStream.clear();
    }

    public OutputFormatting getFormatting() {
        return formatting;
    }

    @Override
    public void newline() {
        rawStream.newline();
    }

    @Override
    public void pr(Object... args) {
        for (int i = 0; i < args.length; i++) {
            prFormatted(args[i]);
            if (i < args.length - 1)
                space();
        }
    }

    void prFormatted(Object arg) {
        rawStream.pr(asFormattedString(arg));
    }

    @Override
    public void prn(Object... args) {
        pr(args);
        newline();
    }

    public void setFormatting(OutputFormatting formatting) {
        this.formatting = formatting;
    }

    public void setRepr() {
        setFormatting(OutputFormatting.REPR);
    }

    public void setShowNone(boolean value) {
        showNone = value;
    }

    public void setStr() {
        setFormatting(OutputFormatting.STR);
    }

    void space() {
        rawStream.pr(" ");
    }

    OutputFormatting formatting;
    IStdOut rawStream;
    boolean showNone;
}
