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
package net.babypython.client.python.builtins.types.sprite.sprites;

import net.babypython.client.vm.containers.dictionaries.LocalDictionary;
import net.babypython.client.python.builtins.objects.PythonSpriteObject;
import net.babypython.client.python.builtins.types.sprite.SpriteType;
import net.babypython.client.python.constants.PythonBuiltinSpriteImagePaths;
import net.babypython.client.python.constants.PythonBuiltinTypeNames;
import net.babypython.client.python.core.PythonObject;
import net.babypython.client.vm.vm.interfaces.IStdOut;

public class SpriteLightHenLeftType extends SpriteType {

    public SpriteLightHenLeftType() {
        super(PythonBuiltinTypeNames.BuiltinTypeLightHenLeft);
    }

    @Override
    protected String defaultImagePath() {
        return PythonBuiltinSpriteImagePaths.LightHenLeft;
    }

    @Override
    public PythonObject instantiate(IStdOut stdOut, LocalDictionary localDictionary, Object... args) {
        return new PythonSpriteObject(this, stdOut, localDictionary, args);
    }
}
