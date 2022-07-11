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

import net.babypython.client.vm.vm.runtime.VmProcess;

public class BlockContext extends Context {

    public BlockContext(VmProcess process, HomeContext homeContext) {
        super(process);
        this.homeContext = homeContext;
    }

    @Override
    public String displayString() {
        return "BlockContext(" + getStack().peek() + ")";
    }

    @Override
    public HomeContext getHomeContext() {
        return homeContext;
    }

    @Override
    public boolean isBlockContext() {
        return true;
    }

    public void pushArgs(int nArgs) {
        if (nArgs > 0 && outerContext != null) {
            for (int i = 0; i < nArgs; i++)
                push(outerContext.pop());
        }
    }

    HomeContext homeContext;
}
