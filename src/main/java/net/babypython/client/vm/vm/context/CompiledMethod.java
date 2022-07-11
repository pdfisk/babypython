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
package net.babypython.client.vm.vm.context;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import net.babypython.client.vm.containers.dictionaries.LocalDictionary;
import net.babypython.client.vm.containers.lists.IntegerList;
import net.babypython.client.vm.containers.lists.ObjectList;
import net.babypython.client.python.process.PythonTimeSliceProcess;
import net.babypython.client.vm.classes.special.classes.UndefinedClass;
import net.babypython.client.vm.constants.JsonVClassConstants;
import net.babypython.client.vm.constants.RunStyle;
import net.babypython.client.vm.core.VClass;
import net.babypython.client.vm.interfaces.IMethod;
import net.babypython.client.vm.util.OutputSink;
import net.babypython.client.vm.vm.interfaces.IStdOut;

public class CompiledMethod implements IMethod {

    public CompiledMethod() {
        this(UndefinedClass.getInstance());
    }

    public CompiledMethod(VClass vClass) {
        super();
        this.vClass = vClass;
        byteCodes = new IntegerList();
        literals = new ObjectList();
        localDictionary = null;
        isCompiled = false;
    }

    public JSONObject asJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JsonVClassConstants._className, new JSONString(getClassName()));
        jsonObject.put(JsonVClassConstants._selector, new JSONString(getSelector()));
        jsonObject.put(JsonVClassConstants._source, new JSONString(getSource()));
        return jsonObject;
    }

    public CompiledMethod clone() {
        CompiledMethod compiledMethod = new CompiledMethod(vClass);
        for (int bc : byteCodes)
            compiledMethod.getByteCodes().add(bc);
        for (Object literal : literals)
            compiledMethod.getLiterals().add(literal);
        compiledMethod.setIsCompiled();
        compiledMethod.setSelector(getSelector());
        return compiledMethod;
    }

    public void ensureCompiled() {
        if (isCompiled)
            return;
        isCompiled = true;
//        SmalltalkCompiler.getInstance().compileForMethod(this, vClass, source, false);
    }

    public void fromJsonObject(JSONObject jsonMethodObject) {
        setClassName(jsonMethodObject.get(JsonVClassConstants._className).isString().stringValue());
        setSelector(jsonMethodObject.get(JsonVClassConstants._selector).isString().stringValue());
        setSource(jsonMethodObject.get(JsonVClassConstants._source).isString().stringValue());
    }

    public IntegerList getByteCodes() {
        return byteCodes;
    }

    public String getClassName() {
        return className;
    }

    public ObjectList getLiterals() {
        return literals;
    }

    public LocalDictionary getLocalDictionary() {
        return localDictionary;
    }

    public void setLocalDictionary(LocalDictionary localDictionary) {
        this.localDictionary = localDictionary;
    }

    public String getSelector() {
        return selector;
    }

    public String getSource() {
        return source;
    }

    public VClass getVClass() {
        return vClass;
    }

    @Override
    public Object perform(Context context, Object receiverObject, String selector, Object[] args) {
        return null;
    }

    public void run(IStdOut stdOut) {
        PythonTimeSliceProcess.run(this, RunStyle.RUN_CONTINUOUSLY, stdOut, null, null);
    }

    public void runSilently() {
        run(OutputSink.getInstance());
    }

    public void setByteCodes(IntegerList byteCodes) {
        this.byteCodes = byteCodes;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setIsCompiled() {
        isCompiled = true;
    }

    public void setLiterals(ObjectList literals)
    {
        this.literals = literals;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public IntegerList byteCodes;
    public String className;
    public boolean isCompiled;
    public ObjectList literals;
    public LocalDictionary localDictionary;
    public String selector;
    public String source;
    public VClass vClass;
}
