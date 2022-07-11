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
package net.babypython.client.vm.classes.special.classes;

import net.babypython.client.vm.constants.PythonTypeConstants;
import net.babypython.client.vm.core.VClass;
import net.babypython.client.vm.workspace.Workspace;

public class FloatClass extends VClass {

	 FloatClass() {
		super(PythonTypeConstants.ClassFloat, Workspace.getInstance().getObjectClass(), "");
	}

	public Object createInstance() {
		return Workspace.getInstance().getNoneObject();
	}

	public VClass addSubclass(VClass aClass) {
		return aClass;
	}

	public static FloatClass getInstance() {
		if (instance == null)
			instance = new FloatClass();
		return instance;
	}

	static FloatClass instance;

}
