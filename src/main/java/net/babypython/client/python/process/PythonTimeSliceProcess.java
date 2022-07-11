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
package net.babypython.client.python.process;

import net.babypython.client.python.api.PythonApi;
import net.babypython.client.ui.interfaces.IHandleLineChanged;
import net.babypython.client.vm.classes.special.objects.BlockObject;
import net.babypython.client.vm.constants.ProcessConstants;
import net.babypython.client.vm.constants.RunStyle;
import net.babypython.client.vm.process.VmTimeSliceProcess;
import net.babypython.client.vm.util.OutputSink;
import net.babypython.client.vm.vm.context.CompiledMethod;
import net.babypython.client.vm.vm.interfaces.IStdOut;
import net.babypython.client.vm.vm.runtime.VmProcess;

public class PythonTimeSliceProcess extends VmTimeSliceProcess {

    public static PythonTimeSliceProcess create(BlockObject blockObject, Object[] args) {
        Object receiver = blockObject.getReceiver();
        CompiledMethod compiledMethod = blockObject.getCompiledMethodForBlockOnly();
        PythonTimeSliceProcess process = new PythonTimeSliceProcess(compiledMethod, RunStyle.RUN_CONTINUOUSLY, OutputSink.asStdOut(), null, args);
        process.vmProcess.pushHomeContext(receiver, compiledMethod);
        return process;
    }

    public static PythonTimeSliceProcess run(CompiledMethod compiledMethod, RunStyle processRunType, IStdOut stdOut, IHandleLineChanged handleLineChanged) {
        return run(compiledMethod, processRunType, stdOut, handleLineChanged, null);
    }

    public static PythonTimeSliceProcess run(CompiledMethod compiledMethod, RunStyle processRunType, IStdOut stdOut, IHandleLineChanged handleLineChanged, Object[] args) {
        PythonTimeSliceProcess vmSliceProcess = new PythonTimeSliceProcess(compiledMethod, processRunType, stdOut, handleLineChanged, args);
        vmSliceProcess.schedule();
        return vmSliceProcess;
    }

    PythonTimeSliceProcess(CompiledMethod compiledMethod, RunStyle runStyle, IStdOut stdOut, IHandleLineChanged handleLineChanged, Object[] args) {
        this.compiledMethod = compiledMethod;
        this.runStyle = runStyle;
        this.stdOut = stdOut;
        this.handleLineChanged = handleLineChanged;
        this.args = args;
        vmProcess = new VmProcess(this);
        vmProcess.setRunStyle(runStyle);
        vmProcess.setIsForkedProcess();
        vmProcess.setArgs(args);
        pythonApi = PythonApi.getInstance();
    }

    @Override
    protected String getName() {
        return ProcessConstants.TimeSlice;
    }

    public void onLineChanged(int lineNumber) {
        if (handleLineChanged != null)
            handleLineChanged.onLineChanged(lineNumber);
    }

    @Override
    public void reset() {
        if (isEnded())
            return;
        if (vmProcess != null)
            vmProcess.resetBreakFlag();
    }

    @Override
    public void run() {
        setRunning();
        oldStdOut = pythonApi.setStdout(stdOut);
        if (errorCondition == null) {
            int startOpCodeCount = vmProcess.opCodeCount;
            if (vmProcess.canContinue()) {
                resumeProcess();
            } else {
                runFirstTime();
            }
            processedOpCodeCount = vmProcess.opCodeCount - startOpCodeCount;
            if (processedOpCodeCount > 0 && vmProcess.canContinue()) {
                setTimedOut();
            } else {
                setEnded();
            }
        } else {
            setEnded();
        }
        pythonApi.setStdout(oldStdOut);
    }

    void resumeProcess() {
        vmProcess.runTimeSlice(stdOut);
    }

    void runFirstTime() {
        vmProcess.run(compiledMethod, stdOut, args);
    }

    public void setDebugFlag() {
        debugFlag = true;
        vmProcess.setDebugFlag();
    }

//    @Override
//    public void setEnded() {
//        super.setEnded();
////        if (handleLineChanged != null)
////            handleLineChanged.processEnded(this);
//    }

    @Override
    public void sleep(int milliseconds) {
        setSleeping();
        vmProcess.setBreakFlag();
        setWakeup(milliseconds);
    }

    @Override
    public String toString() {
        return "TimeSliceProcess";
    }

    Object[] args;
    CompiledMethod compiledMethod;
    boolean debugFlag = false;
    static Object[] emptyArgs = new Object[0];
    IHandleLineChanged handleLineChanged;
    IStdOut oldStdOut;
    public int processedOpCodeCount;
    PythonApi pythonApi;
    RunStyle runStyle;
    IStdOut stdOut;
    public VmProcess vmProcess;
}
