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
package net.babypython.client.python.builtins.types.sprite.methods;

import net.babypython.client.python.builtins.objects.PythonSpriteObject;
import net.babypython.client.python.constants.PythonBuiltinMethodNames;
import net.babypython.client.python.core.PythonMethod;
import net.babypython.client.python.core.PythonObject;
import net.babypython.client.vm.util.ArgsUtil;
import net.babypython.client.vm.vm.context.Context;

public class SpriteShow extends PythonMethod {

    public SpriteShow() {
        super(PythonBuiltinMethodNames.BuiltinMethodShow);
    }

    @Override
    public boolean invoke(Context context, PythonObject self, Object... args) {
        if (!(self instanceof PythonSpriteObject))
            return false;
        PythonSpriteObject pythonSpriteObject = (PythonSpriteObject) self;
        pythonSpriteObject.showTile();
        return true;
    }

}
