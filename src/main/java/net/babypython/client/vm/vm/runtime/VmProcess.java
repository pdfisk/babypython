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
package net.babypython.client.vm.vm.runtime;

import net.babypython.client.vm.containers.dictionaries.LocalDictionary;
import net.babypython.client.vm.containers.lists.IntegerList;
import net.babypython.client.vm.containers.lists.ObjectList;
import net.babypython.client.vm.containers.stacks.ObjectStack;
import net.babypython.client.python.builtins.PythonBuiltinFunctions;
import net.babypython.client.python.builtins.PythonBuiltinTypes;
import net.babypython.client.python.core.PythonMethod;
import net.babypython.client.python.core.PythonObject;
import net.babypython.client.python.core.abstract_.PythonFunction;
import net.babypython.client.python.core.user.PythonUserFunction;
import net.babypython.client.python.process.PythonTimeSliceProcess;
import net.babypython.client.python.user.PythonUserFunctions;
import net.babypython.client.vm.classes.special.classes.ArrayClass;
import net.babypython.client.vm.classes.special.classes.AssociationClass;
import net.babypython.client.vm.classes.special.classes.DictionaryClass;
import net.babypython.client.vm.classes.special.objects.*;
import net.babypython.client.vm.compiler.ByteCodeUtil;
import net.babypython.client.vm.constants.ByteCodeConstants;
import net.babypython.client.vm.constants.ProcessConstants;
import net.babypython.client.vm.constants.PythonMethodConstants;
import net.babypython.client.vm.constants.RunStyle;
import net.babypython.client.vm.core.VClass;
import net.babypython.client.vm.core.VObject;
import net.babypython.client.vm.interfaces.IMethod;
import net.babypython.client.vm.interfaces.IPerform;
import net.babypython.client.vm.process.scheduler.VmScheduler;
import net.babypython.client.vm.util.ClassUtil;
import net.babypython.client.vm.util.ErrorUtil;
import net.babypython.client.ui.util.Logable;
import net.babypython.client.vm.vm.context.*;
import net.babypython.client.vm.vm.interfaces.IStdOut;
import net.babypython.client.vm.vm.runtime.operations.StringOps;
import net.babypython.client.vm.workspace.Workspace;

public class VmProcess extends Logable {

    public VmProcess(PythonTimeSliceProcess timeSliceProcess) {
        this.timeSliceProcess = timeSliceProcess;
        this.breakFlag = false;
        this.isHalted = false;
        this.objectSpace = Workspace.getInstance();
        this.processCompleteFlag = false;
        this.processId = processCount++;
        this.runStyle = RunStyle.RUN_CONTINUOUSLY;
    }

    public boolean canContinue() {
        return !breakFlag && !processCompleteFlag && currentContext != null;
    }

    public IntegerList getByteCodes() {
        return currentContext.getByteCodes();
    }

    public CompiledMethod getCompiledMethod() {
        return currentContext.getCompiledMethod();
    }

    public boolean getIsHalted() {
        return isHalted;
    }

    public Object getLiteral() {
        return getLiterals().get(nextByteCode());
    }

    public ObjectList getLiterals() {
        return currentContext.getLiterals();
    }

    public Object getLocalVariable() {
        String variableName = getStringLiteral();
        return getLocalVariable(variableName);
    }

    public Object getLocalVariable(String variableName) {
        return currentContext.getLocalValue(variableName);
    }

    public int getOpCodeCount() {
        return opCodeCount;
    }

    public PropertyBinding getProperty() {
        String propertyName = getStringLiteral();
        Object object = pop();
        if (object instanceof PythonObject)
            return ((PythonObject) object).findProperty(propertyName);
        return null;
    }

    public Object getReceiver() {
        return currentContext.getSelf();
    }

    public ObjectStack getStack() {
        return currentContext.getStack();
    }

    public String getStringLiteral() {
        return getLiteral().toString();
    }

    public void jumpTo(int pc) {
        setPc(pc);
    }

    public void onSingleSteppingLineChanged(int currentLine) {
//        timeSliceProcess.onSingleSteppingLineChanged(currentLine);
    }

    public void onSteppingLineChanged(int currentLine) {
//        timeSliceProcess.onSteppingLineChanged(currentLine);
    }

    public void push(Object anObject) {
        getStack().push(anObject);
    }

    public BlockContext pushBlockContext() {
        return new BlockContext(this, currentContext.getHomeContext());
    }

    public void pushHomeContext(Object receiver, CompiledMethod compiledMethod) {
        try {
            compiledMethod.ensureCompiled();
        } catch (Exception e) {
            info("compiledMethod.ensureCompiled error", e.getMessage());
            return;
        }
        HomeContext homeContext = null;
        try {
            homeContext = new HomeContext(this, receiver, compiledMethod);
        } catch (Exception e) {
            info("homeContext error", e.getMessage());
            return;
        }
        try {
            pushHomeContext(homeContext);
        } catch (Exception e) {
            info("pushHomeContext(homeContext) error", e.getMessage());
            return;
        }
    }

    void pushHomeContext(HomeContext homeContext) {
        homeContext.setOuterContext(currentContext);
        setCurrentContext(homeContext);
    }

    public void resetBreakFlag() {
        breakFlag = false;
    }

    public void run(CompiledMethod compiledMethod, IStdOut stdOut, Object[] args) {
        this.args = args;
        pushHomeContext(NilObject.getInstance(), compiledMethod);
        runTimeSlice(stdOut);
    }

    void performOpCode(int opCode) {
        opCodeCount++;
        try {
            switch (opCode) {
                case ByteCodeConstants.STORE_GLOBAL:
                    opAssignGlobalVariable();
                    break;
                case ByteCodeConstants.STORE_ATTR:
                    opAssignInstanceVariable();
                    break;
                case ByteCodeConstants.STORE_NAME:
                    opAssignLocalVariable();
                    break;
                case ByteCodeConstants.BINARY_TRUE_DIVIDE:
                    opBinaryDivide();
                    break;
                case ByteCodeConstants.BINARY_SUBTRACT:
                    opBinaryMinus();
                    break;
                case ByteCodeConstants.BINARY_MODULO:
                    opBinaryMod();
                    break;
                case ByteCodeConstants.BINARY_MULTIPLY:
                    opBinaryMultiply();
                    break;
                case ByteCodeConstants.BINARY_ADD:
                    opBinaryPlus();
                    break;
                case ByteCodeConstants.OpCompEq:
                    opCompEq();
                    break;
                case ByteCodeConstants.OpCompGe:
                    opCompGe();
                    break;
                case ByteCodeConstants.OpCompGt:
                    opCompGt();
                    break;
                case ByteCodeConstants.OpCompLe:
                    opCompLe();
                    break;
                case ByteCodeConstants.OpCompLt:
                    opCompLt();
                    break;
                case ByteCodeConstants.OpCompNeq:
                    opCompNeq();
                    break;
                case ByteCodeConstants.OpDefFunction:
                    opDefFunction();
                    break;
                case ByteCodeConstants.DUP_TOP:
                    opDup();
                    break;
                case ByteCodeConstants.OpFork:
                    opFork();
                    break;
                case ByteCodeConstants.OpForkWithArgs:
                    opForkWithArgs();
                    break;
                case ByteCodeConstants.OpJumpIfFalse:
                    opJumpIfFalse();
                    break;
                case ByteCodeConstants.JUMP_ABSOLUTE:
                    opJumpTo();
                    break;
                case ByteCodeConstants.OpLineChanged:
                    opLineChanged(nextByteCode());
                    break;
                case ByteCodeConstants.POP_TOP:
                    opPop();
                    break;
                case ByteCodeConstants.OpPushArray:
                    opPushArray();
                    break;
                case ByteCodeConstants.OpPushAssociation:
                    opPushAssociation();
                    break;
                case ByteCodeConstants.OpPushDictionary:
                    opPushDictionary();
                    break;
                case ByteCodeConstants.OpPushFalse:
                    opPushFalse();
                    break;
                case ByteCodeConstants.LOAD_GLOBAL:
                    opPushGlobalVariable();
                    break;
                case ByteCodeConstants.LOAD_ATTR:
                    opPushInstanceVariable();
                    break;
                case ByteCodeConstants.LOAD_CONST:
                    opPushLiteral();
                    break;
                case ByteCodeConstants.LOAD_NAME:
                    opPushLocalVariable();
                    break;
                case ByteCodeConstants.OpPushProperty:
                    opPushProperty();
                    break;
                case ByteCodeConstants.OpPushNone:
                    opPushNil();
                    break;
                case ByteCodeConstants.OpPushSelf:
                    opPushSelf();
                    break;
                case ByteCodeConstants.OpPushTrue:
                    opPushTrue();
                    break;
                case ByteCodeConstants.OpPythonCallFunction:
                    opPythonCallFunction();
                    break;
                case ByteCodeConstants.OpPythonCallNamedFunction:
                    opPythonCallNamedFunction();
                    break;
                case ByteCodeConstants.OpPythonInstantiateType:
                    opPythonCallInstantiateType();
                    break;
                case ByteCodeConstants.RETURN_VALUE:
                    opReturnFromMethod();
                    break;
                case ByteCodeConstants.OpSendMessage:
                    opSendMessage(false);
                    break;
                case ByteCodeConstants.OpSendMessageWithArgs:
                    opSendMessageWithArgs(false);
                    break;
                case ByteCodeConstants.OpSendSuperMessage:
                    opSendMessage(true);
                    break;
                case ByteCodeConstants.OpSendSuperMessageWithArgs:
                    opSendMessageWithArgs(true);
                    break;
                case ByteCodeConstants.OpTraceMessage:
                    opTraceMessage();
                    break;
                case ByteCodeConstants.OpTraceVariable:
                    opTraceVariable();
                    break;
                case ByteCodeConstants.OpValue:
                    opValue();
                    break;
                case ByteCodeConstants.OpValueWithArgs:
                    opValueWithArgs();
                    break;
                default:
                    info("byteCode", opCode, "not found");
                    info(getSelector(), getByteCodes());
                    setBreakFlag();
                    break;
            }
        } catch (Exception e) {
            info("performOpCode error", opCode, getSelector(), getCompilingClass());
            halt();
        }
    }

    void opAssignGlobalVariable() {
        Object value = pop();
        String variableName = getLiteral().toString();
        setGlobal(variableName, value);
        push(variableName);
    }

    void opAssignInstanceVariable() {
        Object value = pop();
        String variableName = getLiteral().toString();
        Object receiver = getReceiver();
        if (receiver instanceof VObject)
            ((VObject) receiver).setInstanceVariable(variableName, value);
        push(value);
    }

    void opAssignLocalVariable() {
        Object value = pop();
        String variableName = getLiteral().toString();
        currentContext.setLocalValue(variableName, value);
    }

    void opBinaryDivide() {
        Object value2 = pop();
        Object value1 = pop();
        push(MathExpr.div(value1, value2));
    }

    void opBinaryMinus() {
        Object value2 = pop();
        Object value1 = pop();
        push(MathExpr.minus(value1, value2));
    }

    void opBinaryMod() {
        Object value2 = pop();
        Object value1 = pop();
        push(MathExpr.mod(value1, value2));
    }

    void opBinaryMultiply() {
        Object value2 = pop();
        Object value1 = pop();
        push(MathExpr.mult(value1, value2));
    }

    void opBinaryPlus() {
        Object value2 = pop();
        Object value1 = pop();
        push(MathExpr.plus(value1, value2));
    }

    void opCompEq() {
        Object value2 = pop();
        Object value1 = pop();
        push(CompExpr.eq(value1, value2));
    }

    void opCompGe() {
        Object value2 = pop();
        Object value1 = pop();
        push(CompExpr.ge(value1, value2));
    }

    void opCompGt() {
        Object value2 = pop();
        Object value1 = pop();
        push(CompExpr.gt(value1, value2));
    }

    void opCompLe() {
        Object value2 = pop();
        Object value1 = pop();
        push(CompExpr.le(value1, value2));
    }

    void opCompLt() {
        Object value2 = pop();
        Object value1 = pop();
        push(CompExpr.lt(value1, value2));
    }

    void opCompNeq() {
        Object value2 = pop();
        Object value1 = pop();
        push(CompExpr.neq(value1, value2));
    }

    void opDefFunction() {
        Object value = getLiteral();
        if (value instanceof PythonUserFunction) {
            PythonUserFunction pythonUserFunction = (PythonUserFunction) value;
            currentContext.setLocalValue(pythonUserFunction.getName(), pythonUserFunction);
            PythonUserFunctions.define(pythonUserFunction);
        }
    }

    void opDup() {
        push(peek());
    }

    void opFork() {
        Object receiver = pop();
        if (receiver instanceof BlockObject) {
            BlockObject blockObject = (BlockObject) receiver;
            blockObject.fork();
            push(blockObject);
        } else
            sendMessageWithArgs(receiver, PythonMethodConstants.MessageFork, emptyArgs, false);
    }

    void opForkWithArgs() {
        Object receiver = pop();
        int nArgs = nextByteCode();
        if (receiver instanceof BlockObject)
            ((BlockObject) receiver).fork(nArgs);
        else {
            Object[] args = popArgs(nArgs);
            switch (nArgs) {
                case 1:
                    sendMessageWithArgs(receiver, PythonMethodConstants.MessageForkArg, args, false);
                    break;
                case 2:
                    sendMessageWithArgs(receiver, PythonMethodConstants.MessageForkArg2, args, false);
                    break;
                case 3:
                    sendMessageWithArgs(receiver, PythonMethodConstants.MessageForkArg3, args, false);
                    break;
                default:
                    info("opForkWithArgs error", nArgs);
                    break;
            }
        }
    }

    void opJumpIfFalse() {
        Object value = pop();
        int jmp = nextByteCode();
        if (value instanceof Boolean && !((Boolean) value).booleanValue())
            setPc(jmp);
    }

    void opJumpTo() {
        setPc(nextByteCode());
    }

    void opLineChanged(int lineNumber) {
        timeSliceProcess.onLineChanged(lineNumber);
        switch (runStyle) {
            case RUN_WITH_PAUSES:
                currentContext.sleep(ProcessConstants.SteppingDelay);
                break;
        }
    }

    void opPop() {
        pop();
    }

    void opPushArray() {
        int nItems = nextByteCode();
        ArrayObject arrayObject = ArrayClass.getInstance().createInstance();
        for (int i = 0; i < nItems; i++)
            arrayObject.add(pop());
        push(arrayObject);
    }

    void opPushAssociation() {
        String key = StringOps.coerce(pop());
        Object value = pop();
        push(AssociationClass.getInstance().createInstance(key, value));
    }

    void opPushDictionary() {
        int nItems = nextByteCode();
        DictionaryObject dictionaryObject = DictionaryClass.getInstance().createInstance();
        for (int i = 0; i < nItems; i++) {
            Object object = pop();
            if (object instanceof AssociationObject) {
                AssociationObject associationObject = (AssociationObject) object;
                dictionaryObject.put(associationObject.getKey(), associationObject.getValue());
            }
        }
        push(dictionaryObject);
    }

    void opPushFalse() {
        push(Boolean.FALSE);
    }

    void opPushGlobalVariable() {
        String variableName = getLiteral().toString();
        push(objectSpace.getGlobal(variableName));
    }

    void opPushInstanceVariable() {
        String variableName = getLiteral().toString();
        Object receiver = getReceiver();
        if (receiver instanceof VObject)
            push(((VObject) receiver).getInstanceVariable(variableName));
        else
            push(NilObject.getNilObject());
    }

    void opPushLiteral() {
        push(getLiteral());
    }

    void opPushLocalVariable() {
        push(getLocalVariable());
    }

    void opPushProperty() {
        push(getProperty());
    }

    void opPushNil() {
        push(NilObject.getInstance());
    }

    void opPushSelf() {
        push(currentContext.getSelf());
    }

    void opPushTrue() {
        push(Boolean.TRUE);
    }

    void opPythonCallFunction() {
        pythonCallFunction();
    }

    void opPythonCallNamedFunction() {
        pythonCallNamedFunction();
    }

    void opPythonCallInstantiateType() {
        pythonInstantiateType();
    }

    void opReturnFromMethod() {
        lastValue = peek();
        Context parentContext = currentContext.getHomeOuterContext();
        if (parentContext == null) {
            halt();
            if (stdOut != null && !isNullOrNone(lastValue))
                stdOut.prn(lastValue);
        } else {
            setCurrentContext(parentContext);
            push(lastValue);
        }
    }

    void opSendBasicNew(VClass receiver) {
        push(receiver.createInstance());
    }

    boolean opSendClass(Object receiver) {
        push(ClassUtil.getVClass(receiver));
        return true;
    }

    void opSendMessage(boolean isSuper) {
        Object receiver = pop();
        String selector = getLiteral().toString();
        sendMessageWithArgs(receiver, selector, emptyArgs, isSuper);
    }

    void opSendMessageWithArgs(boolean isSuper) {
        String selector = getLiteral().toString();
        int nArgs = nextByteCode();
        Object receiver = pop();
        Object[] args = popArgs(nArgs);
        sendMessageWithArgs(receiver, selector, args, isSuper);
    }

    void opTraceMessage() {
        info("message", getLiteral());
    }

    void opTraceVariable() {
        String variableName = getStringLiteral();
        info(variableName, getLocalVariable(variableName));
    }

    public void showCurrentMethod() {
        info("showCurrentMethod", getPc(), getOpCodeCount());
        ByteCodeUtil.setCompiledMethod(getCompiledMethod());
        ByteCodeUtil.display();
        showStack();
    }

    void showStack() {
        ObjectStack objectStack = getStack();
        for (int i = objectStack.size() - 1; i >= 0; i--)
            info(i, objectStack.get(i));
    }

    public Object peek() {
        return getStack().peek();
    }

    public Object pop() {
        if (args != null) {
            for (Object arg : args)
                push(arg);
            args = null;
        }
        return getStack().pop();
    }

    public Object[] popArgs(int nArgs) {
        ObjectList argList = new ObjectList();
        for (int i = 0; i < nArgs; i++)
            argList.add(0, pop());
        return argList.toArray();
    }

    void pythonCallFunction() {
        Object[] args = popArgs(nextByteCode());
        Object fn = pop();
        PythonObject self = null;
        if ((fn instanceof PropertyBinding)) {
            PropertyBinding propertyBinding = (PropertyBinding) fn;
            if (propertyBinding.isSelfPythonObject())
                self = (PythonObject) propertyBinding.getSelf();
            fn = propertyBinding.getValue();
        }
        if (fn instanceof PythonFunction)
            ((PythonFunction) fn).invoke(currentContext, args);
        else if (fn instanceof PythonMethod)
            ((PythonMethod) fn).invoke(currentContext, self, args);
        else
            info("pythonCallFunction no function found", fn == null);
    }

    void pythonCallNamedFunction() {
        String fnName = getStringLiteral();
        Object[] args = popArgs(nextByteCode());
        if (!PythonBuiltinFunctions.invoke(currentContext, fnName, args)) {
            if (!PythonUserFunctions.invoke(currentContext, fnName, args))
                info("pythonCallNamedFunction error", fnName, args.length);
        }
    }

    void pythonInstantiateType() {
        String typeName = getStringLiteral();
        Object[] args = popArgs(nextByteCode());
        LocalDictionary localDictionary = getLocals();
        push(PythonBuiltinTypes.instantiate(stdOut, typeName, localDictionary, args));
    }

    void sendMessageWithArgs(Object receiver, String selector, Object[] args, boolean isSuper) {
        if (receiver instanceof VClass) {
            if (sendMessageVClassWithArgs((VClass) receiver, selector, args, isSuper))
                return;
        }
        if (receiver instanceof VObject) {
            if (sendMessageVObjectWithArgs((VObject) receiver, selector, args, isSuper))
                return;
        }
        if (receiver instanceof IStdOut) {
            if (sendMessageStdOut((IStdOut) receiver, selector, args))
                return;
        }
        if (receiver instanceof IMethod) {
            if (sendMessageWithArgs((IMethod) receiver, selector, args))
                return;
        }
        if (receiver instanceof IPerform) {
            if (sendMessageIPerformWithArgs((IMethod) receiver, selector, args))
                return;
        }
        if (selector == PythonMethodConstants.MessageClass) {
            opSendClass(receiver);
            return;
        }
        if (!sendMessageObjectWithArgs(receiver, selector, args, isSuper))
            push(ErrorUtil.doesNotUnderstand("process", receiver, selector, currentContext));
    }

    boolean sendMessageWithArgs(IMethod receiver, String selector, Object[] args) {
        push(receiver.perform(currentContext, receiver, selector, args));
        return true;
    }

    boolean sendMessageIPerformWithArgs(IPerform receiver, String selector, Object[] args) {
        receiver.perform(currentContext, receiver, selector, args);
        return true;
    }

    boolean sendMessageVClassWithArgs(VClass receiver, String selector, Object[] args, boolean isSuper) {
        switch (selector) {
            case PythonMethodConstants.MessageSuperclass:
                push(receiver.getSuperClass());
                return true;
            case PythonMethodConstants.MessageBasicNew:
                opSendBasicNew(receiver);
                return true;
            default:
                return false;
        }
    }

    boolean sendMessageVObjectWithArgs(VObject receiver, String selector, Object[] args, boolean isSuper) {
        switch (selector) {
            case PythonMethodConstants.MessageClass:
                return opSendClass(receiver);
            default:
                VClass vClass = receiver.getVClass();
                IMethod iMethod = vClass.lookupMethod(selector, isSuper);
                if (iMethod != null) {
                    if (iMethod instanceof CompiledMethod)
                        return performCompiledMethod((CompiledMethod) iMethod, receiver, selector, args);
                    else
                        return performBuiltinMethod(iMethod, receiver, selector, args);
                }
                return false;
        }
    }

    boolean sendMessageObjectWithArgs(Object receiver, String selector, Object[] args, boolean isSuper) {
        VClass vClass = ClassUtil.getVClass(receiver);
        IMethod iMethod = vClass.lookupMethod(selector, isSuper);
        if (iMethod != null) {
            if (iMethod instanceof CompiledMethod)
                return performCompiledMethod((CompiledMethod) iMethod, receiver, selector, args);
            else
                return performBuiltinMethod(iMethod, receiver, selector, args);
        }
        return false;
    }

    public boolean performCompiledMethod(CompiledMethod compiledMethod, Object receiver, String selector, Object[] args) {
        try {
            pushHomeContext(receiver, compiledMethod);
            push(receiver);
            for (int i = args.length - 1; i >= 0; i--)
                push(args[i]);
            return true;
        } catch (Exception e) {
            info("performCompiledMethod", selector, e.getMessage());
            return false;
        }
    }

    boolean performBuiltinMethod(IMethod iMethod, Object receiver, String selector, Object[] args) {
        Object result = iMethod.perform(currentContext, receiver, selector, args);
        if (result != null) {
            push(result);
            return true;
        }
        return false;
    }

    boolean sendMessageStdOut(IStdOut receiver, String selector, Object[] args) {
        switch (selector) {
            case PythonMethodConstants.MessageClear:
                receiver.clear();
                break;
            case PythonMethodConstants.MessageNewline:
                receiver.newline();
                break;
            case PythonMethodConstants.MessagePrArg:
                receiver.pr(args);
                break;
            case PythonMethodConstants.MessagePrnArg:
                receiver.prn(args);
                break;
            default:
                return false;
        }
        push(receiver);
        return true;
    }

    void opValue() {
        Object receiver = pop();
        if (receiver instanceof BlockObject)
            ((BlockObject) receiver).value(currentContext, 0);
        else
            sendMessageWithArgs(receiver, PythonMethodConstants.MessageValue, emptyArgs, false);
    }

    void opValueWithArgs() {
        Object receiver = pop();
        int nArgs = nextByteCode();
        if (receiver instanceof BlockObject)
            ((BlockObject) receiver).value(currentContext, nArgs);
        else {
            Object[] args = popArgs(nArgs);
            switch (nArgs) {
                case 1:
                    sendMessageWithArgs(receiver, PythonMethodConstants.MessageValueArg, args, false);
                    break;
                case 2:
                    sendMessageWithArgs(receiver, PythonMethodConstants.MessageValueArg2, args, false);
                    break;
                case 3:
                    sendMessageWithArgs(receiver, PythonMethodConstants.MessageValueArg3, args, false);
                    break;
                default:
                    info("opValueWithArgs error", nArgs);
                    break;
            }
        }
    }

    boolean atEnd() {
        return getPc() >= getByteCodes().size();
    }

    VClass getCompilingClass() {
        return currentContext.getCompilingClass();
    }

    public Context getCurrentContext() {
        return currentContext;
    }

    public Object getGlobal(String variableName) {
        return objectSpace.getGlobal(variableName);
    }

    public LocalDictionary getLocals() {
        return getCompiledMethod().getLocalDictionary();
    }

    public int getPc() {
        return currentContext.getPc();
    }

    String getSelector() {
        return currentContext.getSelector();
    }

    public IStdOut getStdOut() {
        return stdOut;
    }

    public void halt() {
        setBreakFlag();
        setAtEnd();
        isHalted = true;
    }

    protected boolean isNullOrNone(Object value) {
        return value == null || value instanceof NilObject;
    }

    int nextByteCode() {
        return currentContext.nextByteCode();
    }

    public void runTimeSlice(IStdOut stdOut) {
        timeSliceCount++;
        this.stdOut = stdOut;
        startTimeSlice();
        if (timeSlice != null) {
            for (timeSlice.reset(); !timeSlice.atEnd(); timeSlice.inc()) {
                if (breakFlag || processCompleteFlag || atEnd())
                    break;
                if (debugFlag)
                    ByteCodeUtil.displayPc(getPc());
                int opCode = nextByteCode();
                performOpCode(opCode);
            }
        }
        stopTimeSlice();
        if (!breakFlag && !processCompleteFlag) {
            timeSliceProcess.schedule();
        }
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public void setAtEnd() {
        timeSlice.setAtEnd();
    }

    public void setBreakFlag() {
        breakFlag = true;
    }

    public void setCurrentContext(Context context) {
        currentContext = context;
    }

    public void setDebugFlag() {
        debugFlag = true;
    }

    public void setGlobal(String variableName, Object value) {
        objectSpace.putGlobal(variableName, value);
    }

    public void setIsForkedProcess() {
        isForkedProcess = true;
    }

    public void setPc(int pc) {
        currentContext.setPc(pc);
    }

    public void setRunStyle(RunStyle runStyle) {
        this.runStyle = runStyle;
    }

    protected void showDebug(Object... args) {
        if (debugFlag)
            info(args);
    }

    public void sleep(double seconds) {
        setAtEnd();
        timeSliceProcess.setWakeup((int) (seconds * 1000));
        timeSliceProcess.setSleeping();
        VmScheduler.schedule(timeSliceProcess);
    }

    public void startTimeSlice() {
        stopTimeSlice();
        resetBreakFlag();
        timeSlice = new TimeSlice(this);
    }

    public void stopTimeSlice() {
        if (timeSlice != null)
            timeSlice.setAtEnd();
        timeSlice = null;
    }

    public Object[] args;
    public boolean breakFlag;
    Context currentContext;
    boolean debugFlag = false;
    public Object[] emptyArgs = new Object[0];
    public boolean isForkedProcess;
    protected boolean isHalted;
    public Object lastValue;
    public Workspace objectSpace;
    public int opCodeCount;
    public boolean processCompleteFlag;
    public static int processCount = 0;
    public int processId;
    public RunStyle runStyle;
    public IStdOut stdOut;
    public TimeSlice timeSlice;
    public int timeSliceCount;
    public PythonTimeSliceProcess timeSliceProcess;
}
