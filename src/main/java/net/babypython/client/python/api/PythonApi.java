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
package net.babypython.client.python.api;

import net.babypython.client.vm.containers.dictionaries.LocalDictionary;
import net.babypython.client.vm.containers.lists.PythonTokenList;
import net.babypython.client.python.compiler.PythonCompiler;
import net.babypython.client.python.parser.Parser;
import net.babypython.client.python.process.PythonTimeSliceProcess;
import net.babypython.client.python.streams.PythonOutputStream;
import net.babypython.client.ui.interfaces.IHandleLineChanged;
import net.babypython.client.ui.windows.transcript.TranscriptWindow;
import net.babypython.client.vm.constants.RunStyle;
import net.babypython.client.ui.util.Logable;
import net.babypython.client.vm.vm.context.CompiledMethod;
import net.babypython.client.vm.vm.interfaces.IStdOut;

public class PythonApi extends Logable {

    public PythonTokenList getAllTokens(String src) {
        return Parser.parseTokens(src);
    }

    public IStdOut getStdout() {
        return null;
    }

    public void debugScript(String src, IStdOut stdOut) {
        stdOut.prn(compileToTreeString(src));
    }

    public CompiledMethod compile(String src) {
        return PythonCompiler.compile(src);
    }

    public String compileToTreeString(String src) {
        return PythonCompiler.compileToTreeString(src);
    }

    public void runScript(String src) {
        runScript(src, TranscriptWindow.asStdOut());
    }

    public void runScript(String src, IStdOut stdOut) {
        stdOut.prn(nativeRunScript(src));
    }

    native String nativeRunScript(String src) /*-{
        $wnd.console.log('nativeRunScript', src);
        return $wnd.pyodide.runPython(src);
    }-*/;

//    runScript(src, stdOut, RunStyle.RUN_CONTINUOUSLY ,null, emptyLocalDictionary);
    public void runScript(String src, IStdOut stdOut, RunStyle runStyle, IHandleLineChanged handleLineChanged, LocalDictionary localDictionary) {
        CompiledMethod compiledMethod = compile(src);
        PythonOutputStream pyStdOut = new PythonOutputStream(stdOut);
        if (compiledMethod == null) {
            pyStdOut.prn("compile error for:\n" + src);
            return;
        }
        try {
            compiledMethod.setLocalDictionary(localDictionary);
            PythonTimeSliceProcess.run(compiledMethod, runStyle, pyStdOut, handleLineChanged);
        } catch (Exception e) {
            pyStdOut.prn("run error", e.getMessage());
        }
    }

    public IStdOut setStdout(IStdOut stdOut) {
        return null;
    }

    public static PythonApi getInstance() {
        if (instance == null)
            instance = new PythonApi();
        return instance;
    }

    static LocalDictionary emptyLocalDictionary = new LocalDictionary();
    static PythonApi instance;
}
