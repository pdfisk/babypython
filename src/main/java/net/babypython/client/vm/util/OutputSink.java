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
package net.babypython.client.vm.util;

import net.babypython.client.vm.constants.OutputFormatting;
import net.babypython.client.vm.vm.context.Context;
import net.babypython.client.vm.vm.interfaces.IStdOut;

import java.util.ArrayList;

public class OutputSink implements IStdOut {

    OutputSink() {
    }

    public static IStdOut asStdOut() {
        return getInstance();
    }

    @Override
    public Object callMethod(Context context, String methodName, ArrayList<Object> args) {
        return null;
    }

    @Override
    public void clear() {
    }

    @Override
    public OutputFormatting getFormatting() {
        return null;
    }

    @Override
    public void newline() {
    }

    @Override
    public void pr(Object... args) {
    }

    @Override
    public void prn(Object... args) {
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

    public static OutputSink getInstance() {
        if (instance == null)
            instance = new OutputSink();
        return instance;
    }

    static OutputSink instance;
}
